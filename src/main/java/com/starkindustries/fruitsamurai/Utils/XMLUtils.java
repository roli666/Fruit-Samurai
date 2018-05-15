package com.starkindustries.fruitsamurai.Utils;

import com.starkindustries.fruitsamurai.GameLogic.Player;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLUtils {
    /**
     * Creates an XML file to store the session data in.
     * For more information check the documentation: <a href="https://docs.oracle.com">Oracle Java Documentation</a>
     *
     * @param player a standard {@link Player} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    static public void makeXML(Player player) {
        File f = new File(XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/scores.xml");
        if (f.exists() && !f.isDirectory()) {
            try {
                addXMLnode(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();
                Element Players = doc.createElement("Players");
                doc.appendChild(Players);

                Element the_player = doc.createElement("Player");
                Attr name = doc.createAttribute("name");
                name.setValue(player.getName());
                the_player.setAttributeNode(name);
                Players.appendChild(the_player);

                Element score = doc.createElement("score");
                score.appendChild(doc.createTextNode(String.valueOf(player.getScore())));
                the_player.appendChild(score);

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/scores.xml"));
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                t.transform(source, result);
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Appends to an existing XML file to store the session data in.
     * For more information check the documentation: <a href="https://docs.oracle.com">Oracle Java Documentation</a>
     *
     * @param player a standard {@link Player} object
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    static private void addXMLnode(Player player) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/scores.xml");
        Element root = document.getDocumentElement();

        Element newPlayer = document.createElement("Player");
        Attr name = document.createAttribute("name");
        name.setValue(player.getName());
        newPlayer.setAttributeNode(name);

        Element score = document.createElement("score");
        score.appendChild(document.createTextNode(Integer.toString(player.getScore())));

        newPlayer.setAttributeNode(name);
        newPlayer.appendChild(score);
        root.appendChild(newPlayer);

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(XMLUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/scores.xml");
        transformer.transform(source, result);
    }
}
