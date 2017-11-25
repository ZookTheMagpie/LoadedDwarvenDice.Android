package no.ntnu.hallvardpc.loadeddwarvendice;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hallvard on 23.11.2017.
 */

public class LoadIDs extends AsyncTask<URL, Integer, List<Integer>> {
    public interface OnPostExecute {
        void onPostExecute(List<Integer> ids);
    }
    LoadIDs.OnPostExecute callback;
    public LoadIDs(LoadIDs.OnPostExecute callback)
    {
        this.callback = callback;
    }

    @Override
    protected List<Integer> doInBackground(URL... urls)
    {
        if(urls.length < 1) return Collections.EMPTY_LIST;

        HttpURLConnection con = null;


        ArrayList<Integer> results = new ArrayList<>();
        try
        {
            con = (HttpURLConnection)urls[0].openConnection();
            JsonReader jr = new JsonReader((new InputStreamReader(con.getInputStream(),"UTF-8")));
            jr.beginArray();
            while(jr.hasNext())
            {
                jr.beginObject();

                int id = 0;

                id = jr.nextInt();
                results.add(id);

                jr.endObject();
            }
            jr.endArray();
        } catch (IOException e) {
            Log.e("LoadMessages","Failed to load messages from: " + urls[0],e);
        }


        return results;
    }

    @Override
    protected void onPostExecute(List<Integer> ids)
    {
        if (callback != null)
        {
            callback.onPostExecute(ids);
        }
    }
}
