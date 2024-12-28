package com.example.serhii_kozlov_pzpi_23_8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Note_edit_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText titleEditText, descriptionEditText;
    private Spinner importanceSpinner;
    private Button saveButton, selectImageButton, deleteButton;
    private ImageView noteImageView;
    private Uri imageUri;
    private Data_Help dbHelper;
    private long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        dbHelper = new Data_Help(this);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        importanceSpinner = findViewById(R.id.importanceSpinner);
        saveButton = findViewById(R.id.saveButton);
        selectImageButton = findViewById(R.id.selectImageButton);
        deleteButton = findViewById(R.id.deleteButton);
        noteImageView = findViewById(R.id.noteImageView);

        Intent intent = getIntent();
        noteId = intent.getLongExtra("note_id", -1);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int importance = intent.getIntExtra("importance", 0);
        String imageUriString = intent.getStringExtra("image_uri");




        titleEditText.setText(title);
        descriptionEditText.setText(description);
        importanceSpinner.setSelection(importance);

        if (imageUriString != null && !imageUriString.isEmpty()) {
            imageUri = Uri.parse(imageUriString);
            noteImageView.setImageURI(imageUri);
        }

        selectImageButton.setOnClickListener(v -> openGallery());
        saveButton.setOnClickListener(v -> updateNote());
        deleteButton.setOnClickListener(v -> deleteNote());
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }




    private void updateNote() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        int importance = importanceSpinner.getSelectedItemPosition();
        String dateTime = getCurrentDateTime(); // Получение текущей даты
        String imageUriString = (imageUri != null) ? imageUri.toString() : "";

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int rowsAffected = dbHelper.updateNote(noteId, title, description, importance, dateTime, imageUriString);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Error updating note", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote() {
        dbHelper.deleteNote(noteId);
        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private String getCurrentDateTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new java.util.Date());
    }
}