package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Resultados1 extends AppCompatActivity {

    private TextView p1, rondas, puntMax;
    private Button bVolver, bRepetir;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados1);

        p1 = findViewById(R.id.puntos);
        bVolver = findViewById(R.id.volver);
        bRepetir = findViewById(R.id.repetir);
        rondas = findViewById(R.id.textView9);
        puntMax = findViewById(R.id.textView8);

        Intent i = getIntent();
        int puntos = i.getIntExtra("puntos1", 0);
        final int nRondas = i.getIntExtra("rondas", 0);

        rondas.setText("Numero de rondas: " + nRondas);
        puntMax.setText("Maxima puntuación posible: " + nRondas*10);

        p1.setText(String.valueOf(puntos));

        //volver a aplicacion anterior
        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        bRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Resultados1.this, tempo_1jugador.class);
                i.putExtra("rondas", nRondas);
                startActivity(i);
                finish();
            }
        });

    }
}
