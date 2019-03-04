package com.haider.nba_games;

public class Games {

    private String team1;



    private String team2;
    private int team1_score;

    private int team2_score;


    public Games(String t1, int t1s){
        this.team1 = t1;

        this.team1_score = t1s;
    }
    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setTeam2_score(int team2_score) {
        this.team2_score = team2_score;
    }

    public String getTeam1() {
        return team1;
    }


    public int getTeam1_score() {
        return team1_score;
    }


    public String getTeam2() {
        return team2;
    }



    public int getTeam2_score() {
        return team2_score;
    }
}
