package com.studioemvs.abbrevsearch;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijsu on 09-05-2017.
 */

public class XmlParser {
    private static final String ns = null;
    String TAG = "XmlParser";

    public List<Abbrevation> parse(InputStream in) throws XmlPullParserException,IOException{
        try {
            Log.d(TAG, "parsing the input data");
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(in,null);
            parser.nextTag();
            return readFeed(parser);
        }finally {
            in.close();
        }
    }

    private List<Abbrevation> readFeed(XmlPullParser parser) throws XmlPullParserException,IOException{
        List<Abbrevation> results = new ArrayList<Abbrevation>();
        Log.d(TAG, "readFeed: "+ns);
            parser.require(XmlPullParser.START_TAG,ns,"feed");
            while (parser.next() != XmlPullParser.END_TAG){
                if (parser.getEventType() != XmlPullParser.START_TAG){
                    continue;
                }
                String name = parser.getName();
                if (name.equals("result")){
                    results.add(readResult(parser));
                }else {
                    skip(parser);
                }
            }
        return results;
    }

    private Abbrevation readResult(XmlPullParser parser) throws XmlPullParserException,IOException{
        parser.require(XmlPullParser.START_TAG,ns,"result");
        String term = null;
        String definition = null;
        while (parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if (name.equals("term")){
                term = readTerm(parser);
            }else if (name.equals("definition")){
                definition = readDefinition(parser);
            }else {
                skip(parser);
            }
        }
        return new Abbrevation(term,definition);
    }
    private String readTerm(XmlPullParser parser) throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"term");
        String term = readTerm(parser);
        parser.require(XmlPullParser.END_TAG,ns,"term");
        return term;
    }

    private String readDefinition(XmlPullParser parser) throws IOException,XmlPullParserException{
        parser.require(XmlPullParser.START_TAG,ns,"definition");
        String definition = readDefinition(parser);
        parser.require(XmlPullParser.END_TAG,ns,"definition");
        return definition;
    }
    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
