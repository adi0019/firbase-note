package com.tanxe.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tanxe.project5.MainActivity;
import com.example.project5.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class createnote extends AppCompatActivity {

    EditText mcreatetitleofnote,mcreatecontentofnote;
    FloatingActionButton msavenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    ProgressBar mprogressbarofcreatenote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);



        msavenote=findViewById(R.id.savenote);
        mcreatecontentofnote=findViewById(R.id.createcontentofnote);
        mcreatetitleofnote=findViewById(R.id.createtitleofnote);

        mprogressbarofcreatenote=findViewById(R.id.progressbarofcreatenote);
        Toolbar toolbar=findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



        msavenote.setOnClickListener(v -> {
            String title=mcreatetitleofnote.getText().toString().trim();
            String content=mcreatecontentofnote.getText().toString().trim();
            if(title.isEmpty() || content.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Both field are Require",Toast.LENGTH_SHORT).show();
            }
            else
            {

                mprogressbarofcreatenote.setVisibility(View.VISIBLE);

                DocumentReference documentReference=firebaseFirestore.collection("project5").document(firebaseUser.getUid()).collection("myNotes").document();
                Map<String ,Object> note= new HashMap<>();
                note.put("title",title);
                note.put("content",content);

                documentReference.set(note).addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(),"Note Created Succesffuly",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(createnote.this,notesactivity.class));
                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(),"Failed To Create Note",Toast.LENGTH_SHORT).show();
                    mprogressbarofcreatenote.setVisibility(View.INVISIBLE);
                });
            }
        });






    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(createnote.this, MainActivity.class);
        startActivity(intent);
    }
}