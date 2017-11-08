


package no.ntnu.hallvardpc.loadeddwarvendice;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LoadValues extends AsyncTask<URL, Integer, Map<String,String>>{
    public interface OnPostExecute {
        void onPostExecute(Map<String,String> messages);
    }
    OnPostExecute callback;
    public LoadValues(OnPostExecute callback)
    {
        this.callback = callback;
    }

    @Override
    protected Map<String,String> doInBackground(URL... urls)
    {
        if(urls.length < 1) return Collections.EMPTY_MAP;

        HttpURLConnection con = null;


        HashMap<String, String> results = new HashMap<String, String>();
        try
        {
            con = (HttpURLConnection)urls[0].openConnection();
            JsonReader jr = new JsonReader((new InputStreamReader(con.getInputStream(),"UTF-8")));
            jr.beginArray();
            while(jr.hasNext())
            {
                jr.beginObject();

                String name = jr.nextName();
                String value = null;
                if (name.equals("sSearch"))
                {
                    name = "Search";
                }
                value = jr.nextString();
                results.put(name,value);

                jr.endObject();
            }
            jr.endArray();
        } catch (IOException e) {
            Log.e("LoadMessages","Failed to load messages from: " + urls[0],e);
        }


        return results;
    }

    @Override
    protected void onPostExecute(Map<String,String> values)
    {
        if (callback != null)
        {
            callback.onPostExecute(values);
        }
    }
}

