package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class NoteDetailActivity extends AppCompatActivity {

    //TODO: prompt message box before deleting

    private EditText titleEditText, descEditText;
    private Button deleteButton;

    private Note selectedNote;

    AlertDialog.Builder builder;
    SQLiteManager sqLiteManager = new SQLiteManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        checkForEditNote();

    }




    private void initWidgets() {
        titleEditText = findViewById(R.id.edtTxtTitleNTA);
        descEditText = findViewById(R.id.edtTxtNoteNTA);
        deleteButton = findViewById(R.id.deleteNoteButton);
        builder = new AlertDialog.Builder(this);

    }

    private void checkForEditNote() {
        Intent previousIntent = getIntent();
        int passedNoteID = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteForID(passedNoteID);

        if (selectedNote != null){
            titleEditText.setText(selectedNote.getTitle());
            descEditText.setText(selectedNote.getDescription());
        }
        else{
            deleteButton.setVisibility(View.INVISIBLE);
        }

    }

    public void saveNote(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());

        if(selectedNote == null ){
            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, title, desc);
            Note.noteArrayList.add(newNote);
            sqLiteManager.addNoteToDatabase(newNote);
        }else{
            selectedNote.setTitle(title);
            selectedNote.setDescription(desc);
            sqLiteManager.updateNoteInDatabase(selectedNote);
        }


        finish();
    }

    public void deleteNote(View view) {
        builder.setTitle("Delete Note")
                .setMessage("Delete note: "+ selectedNote.getTitle() + "?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedNote.setDeleted(new Date());
                        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(NoteDetailActivity.this);
                        sqLiteManager.updateNoteInDatabase(selectedNote);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

}