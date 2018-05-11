package com.starkindustries.fruitsamurai.GameLogic;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the player.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
@XmlRootElement(name = "Player")
public class Player {
    private int lives;
    private int score;
    private String name;
    private boolean playing;
    private boolean showGetName;

    /**
     * Standard constructor of the {@link Player} class.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Player(){
        playing = false;
        lives = 3;
        score = 0;
        name = "";
    }

    /**
     * @return lives of the player.
     */
    public int getLives() {
        return lives;
    }
    /**
     * @param lives sets the lives of the player.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    /**
     * @return the score of the player.
     */
    public int getScore() {
        return score;
    }
    /**
     * @param score sets the score of the player.
     */
    @XmlElement
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * @return a boolean to determine if the player is playing or not.
     */
    public boolean isPlaying(){return playing;}
    /**
     * @param b sets whether the player is playing or not.
     */
    public void setPlaying(boolean b){playing = b;}
    /**
     * @param b sets whether to show the name input or not.
     */
    public void setShowGetName(boolean b) {
        showGetName=b;
    }
    /**
     * @return a boolean to determine if the player is submitting a name.
     */
    public boolean getShowGetName() {
        return  showGetName;
    }
    /**
     * @param playerName sets the name of the player.
     */
    @XmlElement
    public void setName(String playerName) {
        name = playerName;
    }
    /**
     * @return the name of the player.
     */
    public String getName(){return name;}
}
