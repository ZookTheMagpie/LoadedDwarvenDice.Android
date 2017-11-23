package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CharacterSheetsMenu extends AppCompatActivity {
    ThumbnailAdapter adapter;
    List<Integer> list;


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

        try {
            new LoadIDs(new LoadIDs.OnPostExecute() {
                @Override
                public void onPostExecute(List<Integer> ids) {

                    list = ids;
                }
            }).execute(new URL("http://158.38.101.111:8080/LoadedDwarvenDice.Server-1.0-SNAPSHOT/webresources/TOOOOOOOOOOOOOOOOOOODDDDDDDDDDDOOOOOOOOOOOOOO"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        final Button createNewSheetBtn = (Button) findViewById(R.id.buttonNewCharacterSheet);
        createNewSheetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int lastEntry = 0;
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    int id = (Integer) it.next();
                    if (!it.hasNext()) {
                        lastEntry = id;
                    }
                }

                list.get(lastEntry);

                //TODO: Send new number of charactersheets to the db? or does it fix it by itself?


                int newEntry = lastEntry + 1;
                list.add(newEntry);
                adapter.setSheets();
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.thumbnails);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ThumbnailAdapter(this, list);
        rv.setAdapter(adapter);
        adapter.setSheets();

    }
}


