package no.ntnu.hallvardpc.loadeddwarvendice;

import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hallvard on 11.11.2017.
 */

public class PostValueToDatabase extends AsyncTask<PostValueToDatabase.PostValue,Void,Boolean> {
    public PostValueToDatabase() {
        super();
    }

    /**
     * This method will run as a background thread
     *
     * @param values
     * @return
     */
    @Override
    protected Boolean doInBackground(PostValue... values) {
        for (PostValue value : values) {
            try {
                // Setup request
                URL url = new URL(value.getUrl());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);    // We are going to write to the server
                con.setUseCaches(false);  // Not interested in browser/http cache
                con.setRequestMethod("POST"); // Using POST command
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");  // JSON
                //        con.connect();
                // Write Message object
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
                JsonWriter jw = new JsonWriter(bw); // Android JSON support library
                jw.beginObject();
                jw.name("name").value(value.getName());
                jw.name("text").value(value.getText());

                    jw.endObject();

                    jw.close();

                // Get response from server
                StringBuilder result = new StringBuilder();
                int len;
                if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Reader br = new InputStreamReader(new BufferedInputStream(con.getInputStream()),"UTF-8");
                    char[] cbuff = new char[1024];
                    while((len = br.read(cbuff)) != -1) {
                        result.append(cbuff,0,len);
                    }
                    br.close();
                } else {
                    Log.e("PostValue", "ResponseCode: " + con.getResponseCode());
                }
                con.disconnect();
            } catch (IOException e) {
                Log.e("PostValue", "doInBackground: ", e);
            }
        }

        return true;
    }





    public static class PostValue {
        String url;
        String text;
        String name;

        public PostValue(String url, String text, String name) {
            this.url = url;
            this.name = name;
            this.text = text;


        }

        public String getName(){return name;}

        public String getUrl() {
            return url;
        }

        public String getText() {
            return text;
        }
    }
}
