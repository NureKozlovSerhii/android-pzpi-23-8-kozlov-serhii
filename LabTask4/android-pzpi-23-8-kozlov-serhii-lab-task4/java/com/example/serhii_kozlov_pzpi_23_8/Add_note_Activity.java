package com.example.serhii_kozlov_pzpi_23_8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Add_note_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText titleEditText, descriptionEditText;
    private Spinner importanceSpinner;
    private Button saveButton, selectImageButton;
    private ImageView noteImageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        importanceSpinner = findViewById(R.id.importanceSpinner);
        saveButton = findViewById(R.id.saveButton);
        selectImageButton = findViewById(R.id.selectImageButton);
        noteImageView = findViewById(R.id.noteImageView);
    }

    private void setupListeners() {
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isImageSelected(requestCode, resultCode, data)) {
            imageUri = data.getData();
            noteImageView.setImageURI(imageUri);
        }
    }

    private boolean isImageSelected(int requestCode, int resultCode, @Nullable Intent data) {
        return requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null;
    }

    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        int importance = importanceSpinner.getSelectedItemPosition();
        String dateTime = getCurrentDateTime();
        String imageUriString = (imageUri != null) ? imageUri.toString() : "";

        if (areFieldsEmpty(title, description)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            saveNoteToDatabase(title, description, importance, dateTime, imageUriString);
        }
    }

    private boolean areFieldsEmpty(String title, String description) {
        return title.isEmpty() || description.isEmpty();
    }

    private void saveNoteToDatabase(String title, String description, int importance, String dateTime, String imageUriString) {
        Data_Help dbHelper = new Data_Help(this);
        long noteId = dbHelper.addNote(title, description, importance, dateTime, imageUriString);

        if (noteId != -1) {
            Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding note", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
    }
}
