package com.studioemvs.abbrevsearch;

import android.annotation.TargetApi;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


/**
 * Created by vijsu on 04-01-2017.
 */
public class HttpGetRequest extends AsyncTask<String,Void,String>{
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    String TAG = "Post Execute";
    public AsyncResponse delegate = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {
        try {
            return loadXmlData(params[0]);
        }  catch (IOException e) {
            return e.toString();
        } catch (XmlPullParserException e) {
            return e.toString();
        }
    }

    private String loadXmlData(String stringUrl) throws XmlPullParserException,IOException{
        InputStream stream = null;
        XmlParser xmlParser = new XmlParser();
        List<Abbrevation> results = null;
        String term = null;
        String definition = null;
        Calendar rightNow = Calendar.getInstance();
        StringBuilder resultAbbrv = new StringBuilder();
        try {
            stream = downloadUrl(stringUrl);
            results = xmlParser.parse(stream);
        }finally {
            if (stream!=null){
                stream.close();
            }
        }
        for (Abbrevation abbrevation:results){
            resultAbbrv.append(abbrevation.term);
            resultAbbrv.append(abbrevation.definition);
            Log.d(TAG, "loadXmlData: "+resultAbbrv.toString());
        }
        return resultAbbrv.toString();
    }

    private InputStream downloadUrl(String stringUrl) throws IOException {
        Log.d(TAG, "downloadUrl: downloading XML file");
        URL url = new URL(stringUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        Log.d(TAG, "downloadUrl: "+stream);
        return stream;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute: "+s);
    }


}
