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
            }).execute(new URL("http://158.38.101.111/characterSheets/?name=" + ""));
        }   catch (MalformedURLException e)
        {
            e.printStackTrace();
    }
        //Believe we need an array with all the names of the EditText we want to listen to, and then run a while loop for them.
        while() {
            EditText targetEditText = (EditText) findViewById(R.id.target);
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
        }.execute(new PostValueToDatabase.PostValue("http://158.38.101.111/characterSheets/0000000000000000" ,text));
    }

    private ArrayList<String> getTextViews()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("AC");
        list.add("HitPoints");
        list.add("eye");
        list.add("skin");
        list.add("hair");
        list.add("gender");
        list.add("characterName");
        list.add("player");
        list.add("cClass");
        list.add("classLevel");
        list.add("alignment");
        list.add("religion");
        list.add("height");
        list.add("weight");
        list.add("race");
        list.add("Strength");
        list.add("Dexterity");
        list.add("Constitution");
        list.add("Intelligence");
        list.add("Wisdom");
        list.add("Charisma");
        list.add("Appraise");
        list.add("Balance");
        list.add("Bluff");
        list.add("Climb");
        list.add("Concentration");
        list.add("Craft");
        list.add("DecipherScript");
        list.add("Diplomacy");
        list.add("DisableDevice");
        list.add("Disguise");
        list.add("EscapeArtist");
        list.add("Forgery");
        list.add("GatherInformation");
        list.add("HandleAnimal");
        list.add("Heal");
        list.add("Hide");
        list.add("Intimidate");
        list.add("Jump");
        list.add("KnowledgeArcana");
        list.add("KnowledgeArchitecture");
        list.add("KnowledgeDungeoneering");
        list.add("KnowledgeGeography");
        list.add("KnowledgeHistory");
        list.add("KnowledgeLocal");
        list.add("KnowledgeNature");
        list.add("KnowledgeNobility");
        list.add("KnowledgeReligion");
        list.add("KnowledgeThePlanes");
        list.add("Listen");
        list.add("MoveSilently");
        list.add("OpenLock");
        list.add("Perform");
        list.add("Profession");
        list.add("Ride");
        list.add("Search");
        list.add("SenseMotive");
        list.add("SleightOfHand");
        list.add("Spellcraft");
        list.add("Spot");
        list.add("Survival");
        list.add("Swim");
        list.add("Tumble");
        list.add("UseMagicDevice");
        list.add("UseRope");
        list.add("");


        return list;
    }


}


//TODO: set android:imeOptions="actionDone" for all EditText in the xml.
