package com.example.prueba;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Clasificacion extends AppCompatActivity {

    private TextView best1, best2, best3;
    private int p1, p2, p3;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int puntos;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasificacion);

        mAuth = FirebaseAuth.getInstance();
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(id);

        best1 = findViewById(R.id.best1);
        best2 = findViewById(R.id.best2);
        best3 = findViewById(R.id.best3);

        getPuntos();

    }

    /*
    mostro as mellores puntuacións do usuario
     */
    private void getPuntos(){
        mDatabase.child("Puntos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    for(DataSnapshot ds: snapshot.getChildren()){

                        puntos = ds.getValue(Integer.class);

                        if(puntos > p1){

                            p3 = p2;
                            p2 = p1;
                            p1 = puntos;

                            best1.setText("Primer puesto: " + p1);
                            best2.setText("Segundo puesto: " + p2);
                            best3.setText("Tercer puesto: " + p3);

                        } else if(puntos > p2) {

                            p3 = p2;
                            p2 = puntos;

                            best1.setText("Primer puesto: " + p1);
                            best2.setText("Segundo puesto: " + p2);
                            best3.setText("Tercer puesto: " + p3);

                        } else if(puntos > p3){

                            p3 = puntos;

                            best1.setText("Primer puesto: " + p1);
                            best2.setText("Segundo puesto: " + p2);
                            best3.setText("Tercer puesto: " + p3);

                        }

                    }

                } else {

                    //se aínda non xogou ningunha partida mostro puntuacions co valor a 0
                    best1.setText("Primer puesto: 0");
                    best2.setText("Segundo puesto: 0");
                    best3.setText("Tercer puesto: 0");

                    Toast toast = Toast.makeText(Clasificacion.this, "Este usuario no tiene puntos todavía", Toast.LENGTH_SHORT);
                    toast.setGravity(0,0,0);
                    toast.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
