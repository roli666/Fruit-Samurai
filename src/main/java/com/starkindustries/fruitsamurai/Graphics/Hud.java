package com.starkindustries.fruitsamurai.Graphics;

import com.starkindustries.fruitsamurai.Engine.Window;
import com.starkindustries.fruitsamurai.GameLogic.Player;
import com.starkindustries.fruitsamurai.Utils.FileUtils;

import org.lwjgl.nanovg.NVGColor;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.system.MemoryUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.lwjgl.system.MemoryUtil.NULL;

import static org.lwjgl.nanovg.NanoVGGL3.*;

public class Hud {
    private static final String FONT_NAME = "BOLD";

    private long vg;
    private NVGColor color;
    private ByteBuffer fontBuffer;
    private DoubleBuffer posx;
    private DoubleBuffer posy;
    private int counter;
    private String playerName;

    public void init(Window window) throws Exception {
        this.vg = window.getOptions().antialiasing ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
        if (this.vg == NULL) {
            throw new Exception("Could not init nanovg");
        }

        fontBuffer = FileUtils.ioResourceToByteBuffer(FileUtils.getFontsFolder() + "calibri.ttf", 150 * 1024);
        int font = nvgCreateFontMem(vg, FONT_NAME, fontBuffer, 0);
        if (font == -1) {
            throw new Exception("Could not add font");
        }
        color = NVGColor.create();

        posx = MemoryUtil.memAllocDouble(1);
        posy = MemoryUtil.memAllocDouble(1);
        playerName = "";
        counter = 0;
    }

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
            if (window.isKeyPressed(GLFW_KEY_ENTER)) {
                player.setShowGetName(false);
                player.setName(playerName);
                makeXML(player);
                try {
                    addXMLnode(player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                window.disableInput();
            }
            if (window.isKeyPressed(GLFW_KEY_BACKSPACE)) {
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

    private NVGColor rgba(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);

        return colour;
    }

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

    void makeXML(Player player) {
        try {

            File file = new File(FileUtils.getResourcesFolder() + "scores.xml");
            file.createNewFile();
            JAXBContext jaxbContext = JAXBContext.newInstance(Player.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(player, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addXMLnode(Player player) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(FileUtils.getResourcesFolder() + "scores.xml");
        Element root = document.getDocumentElement();

        Element newPlayer = document.createElement("Player");
        Element name = document.createElement("name");
        Element score = document.createElement("score");
        name.appendChild(document.createTextNode(player.getName()));
        score.appendChild(document.createTextNode(Integer.toString(player.getScore())));
        newPlayer.appendChild(name);
        newPlayer.appendChild(score);
        root.appendChild(newPlayer);

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(FileUtils.getResourcesFolder() + "scores.xml");
        transformer.transform(source, result);
    }
}
