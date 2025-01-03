package com.example.serhii_kozlov_pzpi_23_8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Note_Adapter.OnNoteClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int ADD_NOTE_REQUEST_CODE = 1;
    private static final int EDIT_NOTE_REQUEST_CODE = 2;

    private RecyclerView recyclerView;
    private Note_Adapter adapter;
    private ArrayList<Note> notesList = new ArrayList<>();
    private ArrayList<Note> originalNotesList = new ArrayList<>();
    private Data_Help databaseHelper;
    private Uri imageUri;
    private EditText searchEditText;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    updateNotesList();
                } else {
                    Toast.makeText(this, "Permission denied. Cannot load images.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();

        recyclerView = findViewById(R.id.recyclerView);
        databaseHelper = new Data_Help(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Note_Adapter(this, notesList, this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.addNoteButton).setOnClickListener(v -> openAddNoteActivity());

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_MEDIA_IMAGES) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void filterNotes(String searchText) {
        if (originalNotesList.isEmpty()) {
            originalNotesList.addAll(notesList);
        }

        notesList.clear();

        if (searchText.isEmpty()) {
            notesList.addAll(originalNotesList);
        } else {
            for (Note note : originalNotesList) {
                if (note.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                    notesList.add(note);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void updateNotesList() {
        notesList.clear();
        originalNotesList.clear();
        notesList.addAll(databaseHelper.getAllNotes());
        originalNotesList.addAll(notesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNotesList();
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(this, Add_note_Activity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST_CODE);
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(this, Note_edit_Activity.class);
        intent.putExtra("note_id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("description", note.getDescription());
        intent.putExtra("importance", note.getImportance());
        intent.putExtra("image_uri", note.getImageUri() != null ? note.getImageUri().toString() : "");

        startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }

        if ((requestCode == ADD_NOTE_REQUEST_CODE || requestCode == EDIT_NOTE_REQUEST_CODE)
                && resultCode == RESULT_OK) {
            updateNotesList();
        }
    }
}
