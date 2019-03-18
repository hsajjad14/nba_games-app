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
import java.util.UUID;

public class GameFragment extends Fragment {

    private ArrayList<Games> games = new ArrayList<Games>();
    GameListFragment gm;
    Games mGame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID gameId = (UUID)getActivity().getIntent().getSerializableExtra(GameActivity.EXTRA_GAME_ID);
        games = gm.games;
        for (Games x:games){
            if (x.getId() == gameId){
                mGame = x;
            }
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        String stuff;


        return v;

    }

}
