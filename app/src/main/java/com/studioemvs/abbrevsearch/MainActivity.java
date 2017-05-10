package com.studioemvs.abbrevsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AsyncResponse{
    EditText searchText;
    Button search;
    RecyclerView listOfAbbrv;
    String searchTxt;
    String result;
    HttpGetRequest getRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = (EditText)findViewById(R.id.searchAbbrv);
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(this);
        listOfAbbrv = (RecyclerView)findViewById(R.id.abbrvList);
        getRequest = new HttpGetRequest();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search:
                searchTxt = searchText.getText().toString();
                getRequest.delegate = this;
                getRequest.execute("http://www.abbreviations.com/services/v1/abbr.aspx?tokenid=tk1567&term="+searchTxt);
        }
    }

    @Override
    public void processFinish(String output) {
        Toast.makeText(this, ""+output, Toast.LENGTH_SHORT).show();
    }
}
