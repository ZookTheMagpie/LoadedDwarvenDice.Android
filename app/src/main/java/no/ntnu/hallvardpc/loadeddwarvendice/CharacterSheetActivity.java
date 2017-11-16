package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
            }).execute(new URL("http://158.38.101.111:8080/LoadedDwarvenDice.Server-1.0-SNAPSHOT/webresources/characterSheets?name=" + "1"));
        }   catch (MalformedURLException e)
        {
            e.printStackTrace();
    }
        HashMap<Integer,String> list = this.getEditTexts();
        Iterator it = list.keySet().iterator();
        while(it.hasNext()) {
            final int id = (Integer) it.next();

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

        int id = view.getId();
        HashMap<Integer,String> list = this.getEditTexts();
        String name = list.get(id);

        System.out.println("In sendValue " + name + text);

        new PostValueToDatabase() {
            @Override
            protected void onPostExecute(Boolean status) {
                System.out.println("Got on post value " + status);
            }
        }.execute(new PostValueToDatabase.PostValue("http://158.38.101.111:8080/LoadedDwarvenDice.Server-1.0-SNAPSHOT/webresources/characterSheets/add?name = 1" ,text, name));
    }


    private HashMap<Integer,String> getEditTexts()
    {
        HashMap<Integer,String> list = new HashMap<>();
        list.put(R.id.AC,"AC");
        list.put(R.id.HitPoints,"HitPoints");
        list.put(R.id.eye,"eye");
        list.put(R.id.skin,"skin");
        list.put(R.id.hair,"hair");
        list.put(R.id.gender,"gender");
        list.put(R.id.characterName,"characterName");
        list.put(R.id.player,"player");
        list.put(R.id.cClass,"cClass");
        list.put(R.id.classLevel,"classLevel");
        list.put(R.id.alignment,"alignment");
        list.put(R.id.religion,"religion");
        list.put(R.id.height,"height");
        list.put(R.id.weight,"weight");
        list.put(R.id.race,"race");
        list.put(R.id.Strength,"Strength");
        list.put(R.id.Dexterity,"Dexterity");
        list.put(R.id.Constitution,"Constitution");
        list.put(R.id.Intelligence,"Intelligence");
        list.put(R.id.Wisdom,"Wisdom");
        list.put(R.id.Charisma,"Charisma");
        list.put(R.id.Appraise,"Appraise");
        list.put(R.id.Balance,"Balance");
        list.put(R.id.Bluff,"Bluff");
        list.put(R.id.Climb,"Climb");
        list.put(R.id.Concentration,"Concentration");
        list.put(R.id.Craft,"Craft");
        list.put(R.id.DecipherScript,"DecipherScript");
        list.put(R.id.Diplomacy,"Diplomacy");
        list.put(R.id.DisableDevice,"DisableDevice");
        list.put(R.id.Disguise,"Disguise");
        list.put(R.id.EscapeArtist,"EscapeArtist");
        list.put(R.id.Forgery,"Forgery");
        list.put(R.id.GatherInformation,"GatherInformation");
        list.put(R.id.HandleAnimal,"HandleAnimal");
        list.put(R.id.Heal,"Heal");
        list.put(R.id.Hide,"Hide");
        list.put(R.id.Intimidate,"Intimidate");
        list.put(R.id.Jump,"Jump");
        list.put(R.id.KnowledgeArcana,"KnowledgeArcana");
        list.put(R.id.KnowledgeArchitecture,"KnowledgeArchitecture");
        list.put(R.id.KnowledgeDungeoneering,"KnowledgeDungeoneering");
        list.put(R.id.KnowledgeGeography,"KnowledgeGeography");
        list.put(R.id.KnowledgeHistory,"KnowledgeHistory");
        list.put(R.id.KnowledgeLocal,"KnowledgeLocal");
        list.put(R.id.KnowledgeNature,"KnowledgeNature");
        list.put(R.id.KnowledgeNobility,"KnowledgeNobility");
        list.put(R.id.KnowledgeReligion,"KnowledgeReligion");
        list.put(R.id.KnowledgeThePlanes,"KnowledgeThePlanes");
        list.put(R.id.Listen,"Listen");
        list.put(R.id.MoveSilently,"MoveSilently");
        list.put(R.id.OpenLock,"OpenLock");
        list.put(R.id.Perform,"Perform");
        list.put(R.id.Profession,"Profession");
        list.put(R.id.Ride,"Ride");
        list.put(R.id.Search,"Search");
        list.put(R.id.SenseMotive,"SenseMotive");
        list.put(R.id.SleightOfHand,"SleightOfHand");
        list.put(R.id.Spellcraft,"Spellcraft");
        list.put(R.id.Spot,"Spot");
        list.put(R.id.Survival,"Survival");
        list.put(R.id.Swim,"Swim");
        list.put(R.id.Tumble,"Tumble");
        list.put(R.id.UseMagicDevice,"UseMagicDevice");
        list.put(R.id.UseRope,"UseRope");



        return list;
    }


}

