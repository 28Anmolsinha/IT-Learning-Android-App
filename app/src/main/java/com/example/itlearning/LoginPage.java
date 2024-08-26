package com.example.itlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    EditText email, password;
    Button loginButton, signupButton;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //pass intent for user are already register
            Intent intent=new Intent(LoginPage.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.login_button);
        signupButton=findViewById(R.id.sign_up_button);
        mAuth=FirebaseAuth.getInstance();

        //Opening up the homepage after clicking the login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = email.getText().toString();
                String userPass = password.getText().toString();

                if (userEmail.isEmpty() || userPass.isEmpty())
                {
                    Toast.makeText(LoginPage.this, "Please enter details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(userEmail, userPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        //intent
                                        Intent intent=new Intent(LoginPage.this,HomePage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        Toast.makeText(LoginPage.this, "Email or Password is wrong",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}