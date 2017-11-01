package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!loggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            loggedIn = true;
            startActivity(intent);
        }

        final Button buttonCharacterSheets = (Button) findViewById(R.id.buttonCharacterSheets);
        buttonCharacterSheets.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startCharacterSheets();
            }
        });
        final Button buttonDiceRoller = (Button) findViewById(R.id.buttonDiceRoller);
        buttonDiceRoller.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDiceRoller();
            }
        });
    }

    public void startCharacterSheets()
    {
        Intent intent = new Intent(this, CharacterSheetsMenu.class);
        startActivity(intent);
    }

    public void startDiceRoller()
    {
        Intent intent = new Intent(this, DiceRoller.class);
        startActivity(intent);
    }


}
