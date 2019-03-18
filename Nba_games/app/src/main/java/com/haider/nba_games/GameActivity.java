package com.haider.nba_games;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class GameActivity extends SingleFragmentActivity {
    public static final String EXTRA_GAME_ID =
            "com.haider.nba_games.crime_id";

    public static Intent newIntent(Context packageContext, UUID gameID) {
        Intent intent = new Intent(packageContext, GameActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameID);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        return new GameFragment();
    }


}
