package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CharacterSheetsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheets_menu);

        final Button buttonCharacterSheets = (Button) findViewById(R.id.buttontemporary);
        buttonCharacterSheets.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(this, CharacterSheetActivity.class);
                startActivity(intent);
            }
        });
    }
}
