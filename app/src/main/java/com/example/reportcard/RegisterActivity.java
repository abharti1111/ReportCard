package com.example.reportcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText textName, textEmail, textPassword, textPassword2;
    private RadioGroup radio;
    private RadioButton radioButton;
    private Button btnRegister;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
//    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textPassword2 = findViewById(R.id.textPassword2);
        btnRegister = findViewById(R.id.btnRegister);
        radio = findViewById(R.id.radio);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        mAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String name = textName.getText().toString().trim();
                final String email = textEmail.getText().toString().trim();
                final String password1 = textPassword.getText().toString().trim();
                final String password2 = textPassword2.getText().toString().trim();
                int selectedId = radio.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                final boolean isAdmin = radioButton.getText().toString().trim().equals("Admin");
                if(name.isEmpty()){
                    textName.setError("This field Cant be Empty");
                    textName.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    textEmail.setError("This field Cant be Empty");
                    textEmail.requestFocus();
                    return;
                }
                if(!email.matches(emailPattern)){
                    textEmail.setError("Incorrect email address");
                    textEmail.requestFocus();
                    return;
                }
                if(!password1.equals(password2)){
                    textPassword2.setError("Please Recheck password");
                    textPassword2.requestFocus();

                }else{
                    registerUser(name,email,isAdmin,password1);
                }
            }
            private void registerUser(final String name,final String email, final boolean isAdmin, final String password1){
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User(
                                            name,
                                            email,
                                            isAdmin
                                    );
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"Registration Success",Toast.LENGTH_LONG).show();
                                                Intent reg = new Intent(RegisterActivity.this,ActivityLogin.class);
                                                startActivity(reg);
                                            }else{
                                                Toast.makeText(RegisterActivity.this,"Oops!! Registration Failed",Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }
}
