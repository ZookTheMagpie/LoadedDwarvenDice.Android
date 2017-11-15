package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CharacterSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        try {
            new LoadValues(new LoadValues.OnPostExecute() {
                @Override
                public void onPostExecute(Map<String,String> values) {
                    setStats(values);
                }
            }).execute(new URL("http://158.38.101.111/characterSheets/?name=" + "1"));
        }   catch (MalformedURLException e)
        {
            e.printStackTrace();
    }
        ArrayList<Integer> list = this.getEditTexts();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            int id = (Integer) it.next();

            EditText targetEditText = (EditText) findViewById(id);
            targetEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    sendValue(textView);
                    return true;
                }
            });
        }
    }


    private void setStats(Map<String,String> map)
    {

        Iterator it = map.keySet().iterator();
        while(it.hasNext())
        {
            String name = (String) it.next();
            Resources res = getResources();
            int id = res.getIdentifier(name, "id", getPackageName());
            TextView textView = (TextView) this.findViewById(id);
            textView.setText(map.get(name));

        }
    }

    public void sendValue(TextView view) {
        String text = view.getText().toString();
        System.out.println("In sendValue " + text);
        new PostValueToDatabase() {
            @Override
            protected void onPostExecute(Boolean status) {
                System.out.println("Got on post value " + status);
            }
        }.execute(new PostValueToDatabase.PostValue("http://158.38.101.111/characterSheets/add" ,text));
    }


    private ArrayList<Integer> getEditTexts()
    {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.id.AC);
        list.add(R.id.HitPoints);
        list.add(R.id.eye);
        list.add(R.id.skin);
        list.add(R.id.hair);
        list.add(R.id.gender);
        list.add(R.id.characterName);
        list.add(R.id.player);
        list.add(R.id.cClass);
        list.add(R.id.classLevel);
        list.add(R.id.alignment);
        list.add(R.id.religion);
        list.add(R.id.height);
        list.add(R.id.weight);
        list.add(R.id.race);
        list.add(R.id.Strength);
        list.add(R.id.Dexterity);
        list.add(R.id.Constitution);
        list.add(R.id.Intelligence);
        list.add(R.id.Wisdom);
        list.add(R.id.Charisma);
        list.add(R.id.Appraise);
        list.add(R.id.Balance);
        list.add(R.id.Bluff);
        list.add(R.id.Climb);
        list.add(R.id.Concentration);
        list.add(R.id.Craft);
        list.add(R.id.DecipherScript);
        list.add(R.id.Diplomacy);
        list.add(R.id.DisableDevice);
        list.add(R.id.Disguise);
        list.add(R.id.EscapeArtist);
        list.add(R.id.Forgery);
        list.add(R.id.GatherInformation);
        list.add(R.id.HandleAnimal);
        list.add(R.id.Heal);
        list.add(R.id.Hide);
        list.add(R.id.Intimidate);
        list.add(R.id.Jump);
        list.add(R.id.KnowledgeArcana);
        list.add(R.id.KnowledgeArchitecture);
        list.add(R.id.KnowledgeDungeoneering);
        list.add(R.id.KnowledgeGeography);
        list.add(R.id.KnowledgeHistory);
        list.add(R.id.KnowledgeLocal);
        list.add(R.id.KnowledgeNature);
        list.add(R.id.KnowledgeNobility);
        list.add(R.id.KnowledgeReligion);
        list.add(R.id.KnowledgeThePlanes);
        list.add(R.id.Listen);
        list.add(R.id.MoveSilently);
        list.add(R.id.OpenLock);
        list.add(R.id.Perform);
        list.add(R.id.Profession);
        list.add(R.id.Ride);
        list.add(R.id.Search);
        list.add(R.id.SenseMotive);
        list.add(R.id.SleightOfHand);
        list.add(R.id.Spellcraft);
        list.add(R.id.Spot);
        list.add(R.id.Survival);
        list.add(R.id.Swim);
        list.add(R.id.Tumble);
        list.add(R.id.UseMagicDevice);
        list.add(R.id.UseRope);



        return list;
    }


}


//TODO: set android:imeOptions="actionDone" for all EditText in the xml.
