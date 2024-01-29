package com.example.bazadanych;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String stanowisko,zestaw,haslo,imie,nazwisko=new String();

    DBHandler dbHandler;
    String litery="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String cyfry="0123456789";
    String specjalne="@%+/'!#$^?:,.(){}[]~-_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText Poleimie = findViewById(R.id.imie);
        EditText Polenazwisko = findViewById(R.id.nazwisko);
        EditText ile = findViewById(R.id.ileznakow);
        CheckBox checklitery = findViewById(R.id.litery);
        CheckBox  checkcyfry= findViewById(R.id.cyfry);
        CheckBox checkSpecjalne = findViewById(R.id.specialne);
        Button gen = findViewById(R.id.gen);
        Button add = findViewById(R.id.add);
        Spinner wybor = findViewById(R.id.wybor);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Integer.parseInt(ile.getText().toString())>0) {
                        haslo = "";
                        zestaw="";
                        if(checkcyfry.isChecked() || checklitery.isChecked() || checkSpecjalne.isChecked()){
                            if (checklitery.isChecked()){
                                 zestaw = litery;
                            }
                            if (checkcyfry.isChecked()) {
                                zestaw += cyfry;
                            }
                            if (checkSpecjalne.isChecked()) {
                                zestaw += specjalne;
                            }
                            if (!ile.getText().toString().isEmpty()) {
                                for (int x = 0; x < Integer.parseInt(ile.getText().toString()); x++) {
                                    int rand = (int) (Math.random() * zestaw.length());
                                    haslo += zestaw.charAt(rand);
                                }
                                Toast.makeText(MainActivity.this, "Twoje hasło to: " + haslo, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Podaj dlugosć hasła", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Zaznacz co najmniej jedną z opcji",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Długosc musi byc liczbą dodatnią",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Długosc musi byc liczbą",Toast.LENGTH_SHORT).show();
                }
            }
        });
        wybor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stanowisko=wybor.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validate = "[a-zA-Z]+";
                if((!Poleimie.getText().toString().matches(validate))){
                    Toast.makeText(getApplicationContext(),"Wypełnij poprawnie imię",Toast.LENGTH_SHORT).show();
                }else if((!Polenazwisko.getText().toString().matches(validate))){
                    Toast.makeText(getApplicationContext(),"Wypełnij poprawnie nazwisko",Toast.LENGTH_SHORT).show();
                }
                else if(!Poleimie.getText().toString().isEmpty() && !Polenazwisko.getText().toString().isEmpty()) {
                    imie = Poleimie.getText().toString();
                    nazwisko = Polenazwisko.getText().toString();
                    if (haslo != null && haslo != "") {
                        Toast.makeText(getApplicationContext(), "Dodano użykownika: " + imie + " " + nazwisko + " " + stanowisko + " " + haslo, Toast.LENGTH_SHORT).show();
                        Poleimie.setText("");
                        Polenazwisko.setText("");
                        wybor.setSelection(0);
                        ile.setText("");
                        checklitery.setChecked(true);
                        checkcyfry.setChecked(false);
                        checkSpecjalne.setChecked(false);
                        dbHandler = new DBHandler(MainActivity.this);
                        dbHandler.addNewCourse(imie, nazwisko, stanowisko, haslo);

                    }else {
                        Toast.makeText(getApplicationContext(),"Wygeneruj hasło",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wypełnij dane osobowe",Toast.LENGTH_SHORT).show();
                }
                imie=nazwisko=haslo="";
            }
        });
    }
}