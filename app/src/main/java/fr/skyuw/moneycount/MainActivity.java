package fr.skyuw.moneycount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.Profile = (ImageView) findViewById(R.id.ProfileButton);
        Profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent addProfileActivity = new Intent(getApplicationContext(), Profile.class);
                startActivity(addProfileActivity);
                finish();
            }
        });
    }
}