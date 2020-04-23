package com.example.proiect.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiect.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private String name = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth .getCurrentUser() != null){
            Intent intent = new Intent(this, ActivityYourTasks.class);
            startActivity(intent);  // Mergem in activity 2 daca e contul creat + logat
        }

    }

    public void onLoginButtonClick(View view){

        name = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(name + "@yahoo.com", password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ActivityYourTasks.class);
                startActivity(intent);  // Mergem in activity 2 daca m-am logat
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                if(!e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) //Daca nu avem user creat cu credentialele alea atunci nu mai afisam nimic si il creem direct
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                mAuth.createUserWithEmailAndPassword(name + "@yahoo.com", password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, "Created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityYourTasks.class);
                        startActivity(intent);  // Mergem in activity 2 daca contul s-a creat
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(MainActivity.this, "FAiled to create!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
