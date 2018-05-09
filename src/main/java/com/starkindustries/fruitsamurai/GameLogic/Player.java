package com.starkindustries.fruitsamurai.GameLogic;

public class Player {
    private int lives;
    private int score;

    public Player(){
        lives = 3;
        score = 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
