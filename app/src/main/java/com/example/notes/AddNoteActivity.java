package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextDescription;
    private Spinner spinnerDayOfWeak;
    private RadioGroup radioGroupPriority;
    private NotesDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        database = NotesDatabase.getInstance(this);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextTextMultiLine);
        spinnerDayOfWeak = findViewById(R.id.spinnerDaysOfWeak);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

    }

    public void onClickSaveNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dayOfWeak = spinnerDayOfWeak.getSelectedItem().toString();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        if(isFilled(title, description)) {
            Note note = new Note(title, description, dayOfWeak, priority);
            database.notesDao().insertNote(note);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.all_columns_must_be_filled_in, Toast.LENGTH_SHORT).show();
        }
    }
    
    private  boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
    
}