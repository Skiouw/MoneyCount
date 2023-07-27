package fr.skyuw.moneycount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.VectorEnabledTintResources;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddProfile extends AppCompatActivity {

    private ImageButton back;
    private Button      validate;
    private EditText    nameEditText,
                        moneyEditText,
                        startTimeEditText,
                        endTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        this.nameEditText = (EditText) findViewById(R.id.nomProfil);
        this.moneyEditText = (EditText) findViewById(R.id.moneyProfil);
        this.startTimeEditText = (EditText) findViewById(R.id.startTime);
        this.endTimeEditText = (EditText) findViewById(R.id.endTime);
        this.back = (ImageButton) findViewById(R.id.back);
        this.validate = (Button) findViewById(R.id.validate);

        getProfiles();

        validate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), Profile.class);
                startActivity(back);
                finish();
            }
        });
    }

    public void erreurCreation(boolean isHour, boolean isStart){
        if (!isHour)
            Toast.makeText(this, "Veuillez saisir toutes les informations.", Toast.LENGTH_SHORT).show();
        else if(isStart)
            Toast.makeText(this, "Le format d'heure de début ne correspond pas.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Le format d'heure de fin ne correspond pas.", Toast.LENGTH_SHORT).show();
    }

    public void succesCreation(){
        Toast.makeText(this, "Profil créé avec succès.", Toast.LENGTH_SHORT).show();
    }

    public void saveProfile(String name, Double money, String startTime, String endTime){
        SharedPreferences profiles = getApplicationContext().getSharedPreferences("PROFILES",0);
        SharedPreferences.Editor editor = profiles.edit();
        editor.putString("profileName",name);
        editor.apply();
    }

    public void getProfiles(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("PROFILES", 0);
        String name = settings.getString("profileName", null);

        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    public void validate(){
        String name ;
        Double money;
        String startTime,
               endTime;
        String tmp;
        DateFormat hour = new SimpleDateFormat("hh:mm");

        //NOM
        name = nameEditText.getText().toString().trim();

        //SALAIRE
        tmp = moneyEditText.getText().toString().trim();
        if(!tmp.isEmpty())
            money = Double.valueOf(tmp);
        else
            money = 0.00;

        //HEURE DE DEBUT
        tmp = startTimeEditText.getText().toString().trim();
        try {
            startTime = new SimpleDateFormat("hh:mm").format(hour.parse(tmp.toString()));
        } catch (ParseException e) {
            startTime = null;
        }

        //HEURE DE FIN
        tmp = endTimeEditText.getText().toString().trim();
        try {
            endTime = new SimpleDateFormat("hh:mm").format(hour.parse(tmp.toString()));
        } catch (ParseException e) {
            endTime = null;
        }

        if(!name.isEmpty()){
            if(!money.equals(0.00)){
                if(startTime != null) {
                    if(endTime != null) {
                        succesCreation();
                        Intent back = new Intent(getApplicationContext(), Profile.class);
                        startActivity(back);
                        finish();
                    }
                    else
                        erreurCreation(true, false);
                }
                else
                    erreurCreation(true, true);
            }
            else
                erreurCreation(false, false);
        }
        else
            erreurCreation(false, false);

    }

}