package com.haider.nba_games;


import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainFragment extends Fragment {

    private TextView main;
    private ImageButton b1;

    public ArrayList<Games> getGames() {
        return games;
    }

    private ArrayList<Games> games = new ArrayList<Games>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment, container, false);
       //View v = inflater.inflate(R.layout.fragment_main, container, false);

       // this.main = (TextView)v.findViewById(R.id.main);
        //this.b1 = (ImageButton)v.findViewById(R.id.bt1);

//        this.b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
        new run().execute();
               // main.setText("AHHHHHHHHHHHHHHHHHhh");
//            }
//        });


        String stuff;


        return v;

    }

    public class run extends AsyncTask<Void,Void,Void>{
        String line = "";
        String data = "";
        String teams = "";
        @Override
        protected Void doInBackground(Void... voids) {

            try {

                URL url = new URL("https://stats.nba.com/stats/scoreboard/?GameDate=02/13/2019&LeagueID=00&DayOffset=0");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));

                while(line!=null){
                    line = bf.readLine();
                    data += line;
                }
                JSONObject jo = new JSONObject(data);

                JSONArray ja = (JSONArray) jo.get("resultSets");
                JSONObject jo2 = (JSONObject) ja.get(1);//Linescores
                JSONArray ja3 = (JSONArray) jo2.get("rowSet");
                JSONArray ja4;

                int count = 0;
                for(int i = 0;i<ja3.length();i++){
                     ja4 = new JSONArray(ja3.get(i).toString());
                    //teams+=ja4.get(4)+": score =  "+ja4.get(21).toString()+"\n";
                    if(count == 1) {
                        games.get(games.size() - 1).setTeam2(ja4.get(4).toString());
                        games.get(games.size() - 1).setTeam2_score(Integer.parseInt(ja4.get(21).toString()));
                        count = 0;
                    }else{
                        games.add(new Games(ja4.get(4).toString(), Integer.parseInt(ja4.get(21).toString())));
                        count = 1;
                    }
                }

                for(int i = 0;i<games.size();i++) {
                    teams += games.get(i).getTeam1()+": "+games.get(i).getTeam1_score()+" vs "
                            + games.get(i).getTeam2()+": "+games.get(i).getTeam2_score()+"\n";
                }


                }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            main.setText(teams);

        }
    }
}
