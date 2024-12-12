package com.example.serhii_kozlov_pzpi_23_8;




import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextFile;
    private TextView textViewData;
    private SharedPreferences sharedPreferences;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextFile = findViewById(R.id.editTextFile);
        textViewData = findViewById(R.id.textViewData);

        Button buttonSavePrefs = findViewById(R.id.buttonSavePrefs);
        Button buttonLoadPrefs = findViewById(R.id.buttonLoadPrefs);
        Button buttonSaveDB = findViewById(R.id.buttonSaveDB);
        Button buttonLoadDB = findViewById(R.id.buttonLoadDB);
        Button buttonSaveFile = findViewById(R.id.buttonSaveFile);
        Button buttonLoadFile = findViewById(R.id.buttonLoadFile);

        // SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // SQLite
        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        buttonSavePrefs.setOnClickListener(view -> saveToSharedPreferences());
        buttonLoadPrefs.setOnClickListener(view -> loadFromSharedPreferences());
        buttonSaveDB.setOnClickListener(view -> saveToDatabase());
        buttonLoadDB.setOnClickListener(view -> loadFromDatabase());
        buttonSaveFile.setOnClickListener(view -> saveToFile());
        buttonLoadFile.setOnClickListener(view -> loadFromFile());
    }

    private void saveToSharedPreferences() {
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("age", age);
        editor.apply();

        Toast.makeText(this, "Saved to SharedPreferences", Toast.LENGTH_SHORT).show();
    }

    private void loadFromSharedPreferences() {
        String name = sharedPreferences.getString("name", "No Name");
        String age = sharedPreferences.getString("age", "No Age");

        textViewData.setText("Name: " + name + "\nAge: " + age);
    }

    private void saveToDatabase() {
        String name = editTextName.getText().toString();
        String ageStr = editTextAge.getText().toString();

        if (name.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Please enter both name and age", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);

        long rowId = database.insert("users", null, values);

        if (rowId != -1) {
            Toast.makeText(this, "Saved to SQLite", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving to SQLite", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromDatabase() {
        Cursor cursor = database.query("users", null, null, null, null, null, null);

        StringBuilder data = new StringBuilder();
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            if (nameIndex != -1 && ageIndex != -1) {
                String name = cursor.getString(nameIndex);
                int age = cursor.getInt(ageIndex);
                data.append("Name: ").append(name).append(", Age: ").append(age).append("\n");
            }
        }
        cursor.close();

        if (data.length() > 0) {
            textViewData.setText(data.toString());
        } else {
            textViewData.setText("No data found in SQLite");
        }
    }

    private void saveToFile() {
        String data = editTextFile.getText().toString();
        try {
            FileOutputStream fos = openFileOutput("user_data.txt", Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
            Toast.makeText(this, "Saved to File", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving to File", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput("user_data.txt");
            int c;
            StringBuilder temp = new StringBuilder();
            while ((c = fis.read()) != -1) {
                temp.append((char) c);
            }
            fis.close();

            textViewData.setText(temp.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Error loading from File", Toast.LENGTH_SHORT).show();
        }
    }

    static class DBHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "MyDB";
        private static final int DB_VERSION = 1;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    }
}
