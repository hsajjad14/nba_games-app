package com.haider.nba_games;

import android.support.v4.app.Fragment;

public class GameListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new GameListFragment();
    }

}
