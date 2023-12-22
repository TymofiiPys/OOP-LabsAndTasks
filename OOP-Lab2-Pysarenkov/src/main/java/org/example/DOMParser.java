package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DOMParser implements XMLParser {
    @Override
    public List<Candy> parseFromFile(String filename) {
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = null;
        try {
            doc = db.parse(new File(filename));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        Element root = doc.getDocumentElement();

        List<Candy> parsedCandyList = new ArrayList<>();

        if (root.getTagName().equals("CandyList")) {
            NodeList listCandies = root.getElementsByTagName("Candy");
            for (int i = 0; i < listCandies.getLength(); i++) {
                Element candyElt = (Element) listCandies.item(i);
                NodeList candyProperties = candyElt.getChildNodes();
                Candy curCandy = new Candy();
                curCandy.ID = Integer.parseInt(candyElt.getAttribute("id").substring(3));
                curCandy.type = candyElt.getAttribute("Type");
                for (int j = 0; j < candyProperties.getLength(); j++) {
                    Node curNode = candyProperties.item(j);
                    switch (curNode.getNodeName()) {
                        case "Name":
                            curCandy.name = curNode.getTextContent();
                            break;
                        case "Energy":
                            curCandy.energy = Integer.parseInt(curNode.getTextContent());
                            break;
//                        case "Type":
//                            curCandy.type = curNode.getTextContent();
//                            break;
                        case "Ingredients":
                            curCandy.ingredients = new Ingredients();
                            NodeList ingrNodes = curNode.getChildNodes();
                            for (int k = 0; k < ingrNodes.getLength(); k++) {
                                Node curIngr = ingrNodes.item(k);
                                switch (curIngr.getNodeName()) {
                                    case "water":
                                        curCandy.ingredients.water = Double.parseDouble(curIngr.getTextContent());
                                        break;
                                    case "sugar":
                                        curCandy.ingredients.sugar = Double.parseDouble(curIngr.getTextContent());
                                        break;
                                    case "fructose":
                                        curCandy.ingredients.fructose = Double.parseDouble(curIngr.getTextContent());
                                        break;
                                    case "choc_type":
                                        curCandy.ingredients.choc_type = curIngr.getTextContent();
                                        break;
                                    case "vanillin":
                                        curCandy.ingredients.vanillin = Double.parseDouble(curIngr.getTextContent());
                                        break;
                                }
                            }
                            break;
                        case "Value":
                            curCandy.value = new Value();
                            NodeList valueNodes = curNode.getChildNodes();
                            for (int k = 0; k < valueNodes.getLength(); k++) {
                                Node curValue = valueNodes.item(k);
                                switch (curValue.getNodeName()) {
                                    case "protein":
                                        curCandy.value.protein = Double.parseDouble(curValue.getTextContent());
                                        break;
                                    case "fat":
                                        curCandy.value.fat = Double.parseDouble(curValue.getTextContent());
                                        break;
                                    case "carbohydrates":
                                        curCandy.value.carbohydrates = Double.parseDouble(curValue.getTextContent());
                                        break;
                                }
                            }
                            break;
                        case "Production":
                            curCandy.production = curNode.getTextContent();
                            break;
                    }
                }
                parsedCandyList.add(curCandy);
            }
        }

        return parsedCandyList;
    }
}
