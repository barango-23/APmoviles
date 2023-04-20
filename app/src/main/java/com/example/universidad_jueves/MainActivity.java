package com.example.universidad_jueves;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ocultar la barra de titulo por defecto
        getSupportActionBar().hide();
    }

    public void Estudiantes(View view){
        Intent intestudiante=new Intent(this,EstudianteActivity.class);
        startActivity(intestudiante);
    }

    public void Materias(View view){
        Intent intmateria=new Intent(this,MateriaActivity.class);
        startActivity(intmateria);
    }

    public void Matriculas(View view){
        Intent intmatricula=new Intent(this,MatriculaActivity.class);
        startActivity(intmatricula);
    }
}