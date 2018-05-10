package com.starkindustries.fruitsamurai.GameLogic;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Player")
public class Player {
    private int lives;
    private int score;
    private String name;
    private boolean playing;
    private boolean showGetName;

    public Player(){
        playing = false;
        lives = 3;
        score = 0;
        name = "";
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
    @XmlElement
    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}

    public void setShowGetName(boolean b) {
        showGetName=b;
    }

    public boolean getShowGetName() {
        return  showGetName;
    }

    @XmlElement
    public void setName(String playerName) {
        name = playerName;
    }

    public String getName(){return name;}
}
