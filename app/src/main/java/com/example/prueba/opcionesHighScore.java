package com.example.prueba;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class opcionesHighScore extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button registrar, acceder, clasificacion;

    private String email, password;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_highscore);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        registrar = findViewById(R.id.btn_registrar);
        acceder = findViewById(R.id.btn_acceder);
        clasificacion = findViewById(R.id.clasificacion);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){

                    if(password.length() >= 6){

                        RegisterUser();

                    } else {
                        etPassword.getText().clear();
                        Toast toast = Toast.makeText(opcionesHighScore.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT);
                        toast.setGravity(0,0,0);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(opcionesHighScore.this, "Los campos están vacíos", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();
                }

            }
        });

        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){

                    LogInUser();

                } else {
                    Toast toast = Toast.makeText(opcionesHighScore.this, "Los campos están vacíos", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();
                }

            }
        });

        clasificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){

                    UserScore();

                } else {
                    Toast toast = Toast.makeText(opcionesHighScore.this, "Los campos están vacíos", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();
                }
            }
        });


    }

    /*
    rexistro un novo usuario
     */
    private void RegisterUser(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast toast = Toast.makeText(opcionesHighScore.this, "Usuario creado con éxito", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();

                    //borro os EditText
                    etEmail.getText().clear();
                    etPassword.getText().clear();

                    String id = mAuth.getCurrentUser().getUid();

                    Map<String, Object> updates = new HashMap<String,Object>();
                    updates.put("Email", email);

                    //engado os datos á base de datos
                    mDatabase.child("Usuarios").child(id).setValue(updates);

                } else {
                    Toast toast = Toast.makeText(opcionesHighScore.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();
                }
            }
        });
    }

    /*
    accedo ao usuario para poder gardar os seus datos
     */
    private void LogInUser(){
        //comprobo o email e a contraseña
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    etEmail.getText().clear();
                    etPassword.getText().clear();

                    startActivity(new Intent(opcionesHighScore.this, tempo_HighScore.class));

                } else {
                    Toast toast = Toast.makeText(opcionesHighScore.this, "El usuario no existe", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();
                }

            }
        });

    }

    /*
    accedo á pantalla de clasificación
     */
    public void UserScore(){

        //comprobo o email e a contraseña
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    etEmail.getText().clear();
                    etPassword.getText().clear();

                    startActivity(new Intent(opcionesHighScore.this, Clasificacion.class));

                } else {
                    Toast toast = Toast.makeText(opcionesHighScore.this, "El usuario no existe", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();
                }

            }
        });

    }

}
