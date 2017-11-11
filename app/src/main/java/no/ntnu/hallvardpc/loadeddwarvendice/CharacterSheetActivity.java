package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
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
        EditText targetEditText = (EditText)findViewById(R.id.target);
        targetEditText.setOnEditorActionListener(sendValue(target));
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

    public void sendValue(View view) {
        TextView t = (TextView) findViewById(R.id.target);
        String text = t.getText().toString();
        System.out.println("In sendValue " + text);
        new PostValueToDatabase() {
            @Override
            protected void onPostExecute(Boolean status) {
                System.out.println("Got on post value " + status);
            }
        }.execute(new PostValueToDatabase.PostValue("http://158.38.101.111/characterSheets/0000000000000000" ,text));
    }


}
