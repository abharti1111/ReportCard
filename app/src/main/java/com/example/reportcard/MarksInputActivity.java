package com.example.reportcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MarksInputActivity extends AppCompatActivity {
    private TextView textName,textEmail;
    private EditText textPhysics,textChemistry, textMaths,textEnglish,textCS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_input);
        textChemistry = findViewById(R.id.textChemistry);
        textPhysics = findViewById(R.id.textPhysics);
        textMaths = findViewById(R.id.textMaths);
        textEnglish = findViewById(R.id.textEnglish);
        textCS = findViewById(R.id.textCS);

    }
}
