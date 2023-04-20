package com.example.universidad_jueves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EstudianteActivity extends AppCompatActivity {

    EditText jetcarnet, jetnombre, jetcarrera, jetsemestre;
    CheckBox jcbactivo;
    String carnet, nombre, carrera, semestre, id_documento;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        //Ocultar la barra de titulo por defecto y asociar objetos Java y Xml
        getSupportActionBar().hide();
        jetcarnet = findViewById(R.id.etcarnet);
        jetnombre = findViewById(R.id.etnombre);
        jetcarrera = findViewById(R.id.etcarrera);
        jetsemestre = findViewById(R.id.etsemestre);
        jcbactivo = findViewById(R.id.cbactivo);
    }

    public void Adicionar(View view) {
        //Pasar informacion de objetos a variables
        carnet = jetcarnet.getText().toString();
        nombre = jetnombre.getText().toString();
        carrera = jetcarrera.getText().toString();
        semestre = jetsemestre.getText().toString();
        //Validar que la informacion de entrada se digito
        if (carnet.isEmpty() || nombre.isEmpty() || carrera.isEmpty() || semestre.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetcarnet.requestFocus();
        } else {
            // Create a new student with a first and last name
            Map<String, Object> estudiante = new HashMap<>();
            estudiante.put("Carnet", carnet);
            estudiante.put("Nombre", nombre);
            estudiante.put("Carrera", carrera);
            estudiante.put("Semestre", semestre);
            estudiante.put("Activo", "Si");

            // Add a new document with a generated ID
            db.collection("Estudiantes")
                    .add(estudiante)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //  Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(EstudianteActivity.this, "Documento guardado", Toast.LENGTH_SHORT).show();
                            Limpiar_campos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w(TAG, "Error adding document", e);
                            Toast.makeText(EstudianteActivity.this, "Error guardando documento", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }//Fin adiccionar

    public void Consultar(View view) {
        carnet = jetcarnet.getText().toString();
        if (carnet.isEmpty()) {
            Toast.makeText(this, "Carnet requerido para buscar", Toast.LENGTH_SHORT).show();
            jetcarnet.requestFocus();
        } else {
            db.collection("Estudiantes")
                    .whereEqualTo("Carnet", carnet)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    id_documento = document.getId();
                                    jetnombre.setText(document.getString("Nombre"));
                                    jetcarrera.setText(document.getString("Carrera"));
                                    jetsemestre.setText(document.getString("Semestre"));
                                    if (document.getString("Activo").equals("Si"))
                                        jcbactivo.setChecked(true);
                                    else
                                        jcbactivo.setChecked(false);
                                }
                            } else {
                                // Log.w(TAG, "Error getting documents.", task.getException());

                            }
                        }
                    });
        }
    }//fin consultar

    private void Limpiar_campos() {
        jetcarnet.setText("");
        jetsemestre.setText("");
        jetcarrera.setText("");
        jetnombre.setText("");
        jcbactivo.setChecked(false);
        jetcarnet.requestFocus();
    }
}





