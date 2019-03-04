package com.haider.nba_games;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameListFragment extends Fragment {

    private RecyclerView mGameRecycleViewer;
    private GameAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        mGameRecycleViewer = (RecyclerView) view
                .findViewById(R.id.game_recycler_view);
        mGameRecycleViewer.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private class GameHolder extends RecyclerView.ViewHolder {
        private TextView mteam1;
        private TextView mteam2;
        private TextView mteam1score;
        private TextView mteam2score;

        private Games mGame;


        public GameHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_game_item, parent, false));

            mteam1 = (TextView) itemView.findViewById(R.id.team1);
            mteam2 = (TextView) itemView.findViewById(R.id.team2);
            mteam1score = (TextView) itemView.findViewById(R.id.score1);
            mteam2score = (TextView) itemView.findViewById(R.id.score2);
        }

        public void bind(Games game){
            mGame = game;
            mteam1.setText(mGame.getTeam1());
            mteam2.setText(mGame.getTeam2());
            mteam1score.setText(Integer.toString(mGame.getTeam1_score()));
            mteam2score.setText(Integer.toString(mGame.getTeam2_score()));

        }

    }
    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {
        private List<Games> mGames;
        public GameAdapter(List<Games> games) {
            mGames = games;
        }
        @Override
        public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new GameHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(GameHolder holder, int position) {
            Games game = mGames.get(position);
            holder.bind(game);
        }
        @Override
        public int getItemCount() {
            return mGames.size();
        }

    }
    ArrayList<Games> games = new ArrayList<>();

    private void updateUI() {

        class run extends AsyncTask<Void,Void,Void> {
            String line = "";
            String data = "";
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    String day = date.split("-")[2];
                    String month = date.split("-")[1];
                    String year = date.split("-")[0];
                    String comb = "https://stats.nba.com/stats/scoreboard/?GameDate="+month+"/"+day+"/"+year+"&LeagueID=00&DayOffset=0";
                            //02/13/2019&LeagueID=00&DayOffset=0"
                    Log.d("EEEEEEEEHHH",month+"/"+day+"/"+year);
                    //"https://stats.nba.com/stats/scoreboard/?GameDate=02/13/2019&LeagueID=00&DayOffset=0"
                    URL url = new URL(comb);

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

                        Log.d("IIIIII",Integer.toString(count));

                        //teams+=ja4.get(4)+": score =  "+ja4.get(21).toString()+"\n";
                        if(count == 1) {
                            if(ja4.get(21).toString() == "null"){
                                games.get(games.size() - 1).setTeam2(ja4.get(4).toString());
                                games.get(games.size() - 1).setTeam2_score(0);
                            }else{
                                games.get(games.size() - 1).setTeam2(ja4.get(4).toString());
                                games.get(games.size() - 1).setTeam2_score(Integer.parseInt(ja4.get(21).toString()));
                            }
                            count = 0;
                        }else{
                            if(ja4.get(21).toString() == "null"){
                                games.add(new Games(ja4.get(4).toString(), 0));
                            }else{
                                games.add(new Games(ja4.get(4).toString(), Integer.parseInt(ja4.get(21).toString())));

                            }
                            count = 1;
                        }
                        Log.d("AAAAAHHHHHHH",Integer.toString(games.size()));
                    }




                }catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //main.setText(teams);
                mAdapter = new GameAdapter(games);
                mGameRecycleViewer.setAdapter(mAdapter);

            }
        }
        run r = new run();

        r.execute();

        Log.d("OOOHHHHHHH",Integer.toString(games.size()));

//        mAdapter = new GameAdapter(games);
//        mGameRecycleViewer.setAdapter(mAdapter);
    }

}
