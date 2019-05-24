package com.studycseathome.todoappwithfirebase;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private Button updateButton,deleteButton;
    private EditText editTextTitle,editTextDescription;
    private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent i=getIntent();
        note= (Note) getIntent().getSerializableExtra("note");
        final String id=note.getId();
        db=FirebaseDatabase.getInstance();
        myRef=db.getReference("Notes");
        updateButton=findViewById(R.id.update_btn_id);
        deleteButton=findViewById(R.id.delete_btn_id);
        editTextTitle=findViewById(R.id.title_id);
        editTextDescription=findViewById(R.id.description_id);

        editTextTitle.setText(note.getTitle());
        editTextDescription.setText(note.getDescription());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote(id);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(id);
            }
        });
    }

    private void updateNote(String id) {
        String title=editTextTitle.getText().toString();
        String description=editTextDescription.getText().toString();
        //String id = myRef.push().getKey();
        Note n=new Note(id,title,description);
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            return;
        }
        myRef.child(id).setValue(n).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UpdateActivity.this,"Note Updated.",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void deleteNote(String id) {
        myRef.child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateActivity.this,"Note Updated",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
