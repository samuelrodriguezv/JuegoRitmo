package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class opciones extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner numjug;
    private Button jugar, up, down, info;
    private TextView numRondas;

    private int rondas;
    private String text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);

        //boton jugar
        jugar = findViewById(R.id.jugar);
        info = findViewById(R.id.informacion);

        //botones para subir e baixar rondas
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);

        //textview de rondas
        numRondas = findViewById(R.id.text);
        rondas = Integer.parseInt(numRondas.getText().toString());

        //funcionalidade do boton "up": aumenta en 1 o numero de rondas (maximo 10)
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rondas < 15){
                    rondas = rondas + 1;
                }
                numRondas.setText(String.valueOf(rondas));
            }
        });

        //funcionalidade do boton "down": reduce en 1 o numero de rondas (minimo 3)
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rondas > 1) {
                    rondas = rondas - 1;
                }
                numRondas.setText(String.valueOf(rondas));
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(opciones.this, Informacion.class));
            }
        });

        //spinner
        numjug = findViewById(R.id.jugadores);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Jugadores, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numjug.setAdapter(adapter);

        numjug.setOnItemSelectedListener(this);

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.equals("1 jugador")) {
                    Intent i = new Intent(opciones.this, tempo_1jugador.class);
                    //i.putExtra(EXTRA_NUMBER, numero);
                    i.putExtra("rondas", rondas);
                    startActivity(i);
                } else if (text.equals("2 jugadores")) {
                    Intent i = new Intent(opciones.this, tempo_2jugadores.class);
                    i.putExtra("rondas", rondas);
                    startActivity(i);
                } else if (text.equals("3 jugadores")) {
                    Intent i = new Intent(opciones.this, tempo_3jugadores.class);
                    i.putExtra("rondas", rondas);
                    startActivity(i);
                } else if (text.equals("4 jugadores")) {
                    Intent i = new Intent(opciones.this, tempo_4jugadores.class);
                    i.putExtra("rondas", rondas);
                    startActivity(i);
                }
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //recollo en String o texto de spinner
        text = parent.getItemAtPosition(pos).toString();

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}
