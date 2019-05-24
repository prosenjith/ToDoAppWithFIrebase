package com.studycseathome.todoappwithfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private Button addButton;
    private EditText editTextTitle,editTextDescription;
    private DatabaseReference myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        db=FirebaseDatabase.getInstance();
        myRef = db.getReference("Notes");
        addButton=findViewById(R.id.add_btn_id);
        editTextTitle=findViewById(R.id.title_id);
        editTextDescription=findViewById(R.id.description_id);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirebaseDatabase();
            }
        });
    }

    private void addToFirebaseDatabase() {
        String title=editTextTitle.getText().toString();
        String description=editTextDescription.getText().toString();
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            return;
        }
        String id = myRef.push().getKey();
        Note note=new Note(id,title,description);
        myRef.child(id).setValue(note);
        Toast.makeText(this,"Note Added.",Toast.LENGTH_SHORT).show();
    }
}
