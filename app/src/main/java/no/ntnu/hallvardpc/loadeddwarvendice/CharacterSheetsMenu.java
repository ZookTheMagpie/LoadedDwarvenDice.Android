package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class CharacterSheetsMenu extends AppCompatActivity {
    ThumbnailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheets_menu);

        /**final Button buttonCharacterSheets = (Button) findViewById(R.id.buttontemporary);
        buttonCharacterSheets.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CharacterSheetsMenu.this, CharacterSheetActivity.class);
                startActivity(intent);
            }
        });*/

        RecyclerView rv = (RecyclerView) findViewById(R.id.thumbnails);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new ThumbnailAdapter(this);
        rv.setAdapter(adapter);
        adapter.setSheets();

    }
}
