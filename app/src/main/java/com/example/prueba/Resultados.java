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

public class Resultados extends AppCompatActivity {

    private TextView actual, best1, best2, best3;
    private Button bVolver, bRepetir;
    private int p1, p2, p3;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int puntos;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);

        mAuth = FirebaseAuth.getInstance();
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        //situome no usuario da base de datos para recorrer as súas puntuacións
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(id);

        actual = findViewById(R.id.actual);
        best1 = findViewById(R.id.best1);
        best2 = findViewById(R.id.best2);
        best3 = findViewById(R.id.best3);
        bVolver = findViewById(R.id.volver);
        bRepetir = findViewById(R.id.repetir);

        getPuntos();

        //volver a la pantalla opciones
        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        //repetir partida con los mismos datos
        bRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Resultados.this, tempo_HighScore.class));
            }
        });

    }

    private void getPuntos(){
        mDatabase.child("Puntos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    //recorro os puntos do usuario
                    for(DataSnapshot ds: snapshot.getChildren()){

                        puntos = ds.getValue(Integer.class);

                        //escribo os puntos no seu correspondente posto canto maior sexan
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

                        //escribo a puntuacion da ultima partida
                        actual.setText("Puntuación actual: " + puntos);

                    }

                } else {

                    Toast toast = Toast.makeText(Resultados.this, "Este usuario no tiene puntos todavía", Toast.LENGTH_SHORT);
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
