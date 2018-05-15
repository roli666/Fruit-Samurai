package com.starkindustries.fruitsamurai.Graphics;

import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.GameLogic.Player;
import com.starkindustries.fruitsamurai.Utils.FileUtils;

import com.starkindustries.fruitsamurai.Utils.XMLUtils;
import org.lwjgl.nanovg.NVGColor;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.system.MemoryUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.lwjgl.system.MemoryUtil.NULL;

import static org.lwjgl.nanovg.NanoVGGL3.*;
/**
 * This class is used to display the HUD in the game.
 * Uses <a href="https://github.com/memononen/nanovg">NanoVG</a>
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Hud {
    private static final String FONT_NAME = "BOLD";

    private long vg;
    private NVGColor color;
    private ByteBuffer fontBuffer;
    private DoubleBuffer posx;
    private DoubleBuffer posy;
    private int counter;
    private String playerName;
    /**
     * Initializes the NanoVG hud.
     * @param window a standard {@link Window} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @throws Exception
     */
    public void init(Window window) throws Exception {
        this.vg = window.getOptions().antialiasing ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.vg == NULL) {
            throw new Exception("Could not init NanoVG");
        }

        fontBuffer = FileUtils.ioResourceToByteBuffer("/fonts/calibri.ttf", 150 * 1024);
        int font = nvgCreateFontMem(vg, FONT_NAME, fontBuffer, 0);
        if (font == -1) {
            throw new Exception("Could not add Font");
        }
        color = NVGColor.create();

        posx = MemoryUtil.memAllocDouble(1);
        posy = MemoryUtil.memAllocDouble(1);
        playerName = "";
        counter = 0;
    }
    /**
     * Renders the NanoVG hud.
     * @param window a standard {@link Window} object
     * @param player a standard {@link Player} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void render(Window window, Player player) {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

        glfwGetCursorPos(window.getWindowID(), posx, posy);
        int x = (int) posx.get(0);
        int y = (int) posy.get(0);
        boolean hover = (x >= window.getWidth() / 2 - 150 && x <= window.getWidth() / 2 - 150 + 300) && (y >= window.getHeight() / 2 - 5 && y <= window.getHeight() / 2 + 100);

        //Write name here
        if (!player.isPlaying() && player.getShowGetName()) {
            nvgBeginPath(vg);
            nvgRect(vg, window.getWidth() / 2 - 150, window.getHeight() / 2 - 5, 300, 5);
            nvgFontSize(vg, 30.0f);
            nvgFontFace(vg, FONT_NAME);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, color));
            nvgStrokeColor(vg, rgba(0xff, 0xff, 0xff, 255, color));
            nvgText(vg, window.getWidth() / 2 - 150, window.getHeight() / 2 - 40, playerName += (window.lastChar != null && window.getElapsedTime() <= 0.2) ? window.lastChar[0] : "");
            if (hover) {
                window.setCursor(GLFW_IBEAM_CURSOR);
            } else {
                window.setCursor(GLFW_ARROW_CURSOR);
            }
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, color));
            nvgFill(vg);
            if (window.isKeyPressed(GLFW_KEY_ENTER) || window.isKeyPressed(GLFW_KEY_KP_ENTER)) {
                player.setShowGetName(false);
                player.setName(playerName);
                XMLUtils.makeXML(player);
                window.disableInput();
            }
            if (window.isKeyPressed(GLFW_KEY_BACKSPACE)) {
                if(playerName.length()>0)
                playerName = playerName.substring(0, playerName.length() - 1);
            }
        }

        if (!player.isPlaying() && !window.isInputEnabled()) {
            // Start Game
            nvgFontSize(vg, 30.0f);
            nvgFontFace(vg, FONT_NAME);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, color));
            nvgStrokeColor(vg, rgba(0xff, 0xff, 0xff, 255, color));
            nvgText(vg, 80, 280, "Start Game");

            // LeaderBoards
            nvgFontSize(vg, 30.0f);
            nvgFontFace(vg, FONT_NAME);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, color));
            nvgStrokeColor(vg, rgba(0xff, 0xff, 0xff, 255, color));
            nvgText(vg, 260, 170, "LeaderBoards");

            // Exit
            nvgFontSize(vg, 30.0f);
            nvgFontFace(vg, FONT_NAME);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, color));
            nvgStrokeColor(vg, rgba(0xff, 0xff, 0xff, 255, color));
            nvgText(vg, window.getWidth() - 220, 280, "Exit");
        }

        if (player.isPlaying()) {
            // Render score
            nvgFontSize(vg, 40.0f);
            nvgFontFace(vg, FONT_NAME);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, color));
            nvgStrokeColor(vg, rgba(0xff, 0xff, 0xff, 255, color));
            nvgText(vg, 0, 0, String.format("Score:%d", player.getScore()));
        }
        nvgEndFrame(vg);

        // Restore state
        window.restoreState();
    }
    /**
     * Constructs an NVGColor object.
     * @param r red component of the color
     * @param g green component of the color
     * @param b blue component of the color
     * @param a alpha component of the color
     * @param color an {@link NVGColor} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     * @return {@link NVGColor} object
     */
    private NVGColor rgba(int r, int g, int b, int a, NVGColor color) {
        color.r(r / 255.0f);
        color.g(g / 255.0f);
        color.b(b / 255.0f);
        color.a(a / 255.0f);

        return color;
    }

    /**
     * Cleans up the NanoVG hud.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void cleanup() {
        nvgDelete(vg);
        if (posx != null) {
            MemoryUtil.memFree(posx);
        }
        if (posy != null) {
            MemoryUtil.memFree(posy);
        }
    }

    public String getPlayerName() {
        return playerName;
    }
}
