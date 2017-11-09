package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

}
