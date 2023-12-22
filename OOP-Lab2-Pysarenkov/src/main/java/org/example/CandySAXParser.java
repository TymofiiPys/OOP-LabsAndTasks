package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

public class CandySAXParser implements XMLParser {
    List<Candy> parsedCandyList;

    private class MyHandler extends DefaultHandler {
        private StringBuilder curElement;

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if(curElement == null){
                curElement = new StringBuilder();
            } else {
                curElement.append(ch, start, length);
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "CandyList":
                    parsedCandyList = new ArrayList<>();
                    break;
                case "Candy":
                    parsedCandyList.add(new Candy());
                    latestCandy().ID = Integer.parseInt(attributes.getValue(0).substring(3));
                    latestCandy().type = attributes.getValue(1);
                    break;
                case "Name":
                    curElement = new StringBuilder();
                    break;
                case "Energy":
                    curElement = new StringBuilder();
                    break;
//                case "Type":
//                    curElement = new StringBuilder();
//                    break;
                case "Ingredients":
                    latestCandy().ingredients = new Ingredients();
                    break;
                case "water":
                    curElement = new StringBuilder();
                    break;
                case "sugar":
                    curElement = new StringBuilder();
                    break;
                case "fructose":
                    curElement = new StringBuilder();
                    break;
                case "choc_type":
                    curElement = new StringBuilder();
                    break;
                case "vanillin":
                    curElement = new StringBuilder();
                    break;
                case "Value":
                    latestCandy().value = new Value();
                    break;
                case "protein":
                    curElement = new StringBuilder();
                    break;
                case "fat":
                    curElement = new StringBuilder();
                    break;
                case "carbohydrates":
                    curElement = new StringBuilder();
                    break;
                case "Production":
                    curElement = new StringBuilder();
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case "Name":
                    latestCandy().name = curElement.toString();
                    break;
                case "Energy":
                    latestCandy().energy = Integer.parseInt(curElement.toString());
                    break;
//                case "Type":
//                    latestCandy().type = curElement.toString();
//                    break;
//                case "Ingredients":
                case "water":
                    latestCandy().ingredients.water = Double.parseDouble(curElement.toString());
                    break;
                case "sugar":
                    latestCandy().ingredients.sugar = Double.parseDouble(curElement.toString());
                    break;
                case "fructose":
                    latestCandy().ingredients.fructose= Double.parseDouble(curElement.toString());
                    break;
                case "choc_type":
                    latestCandy().ingredients.choc_type = curElement.toString();
                    break;
                case "vanillin":
                    latestCandy().ingredients.vanillin = Double.parseDouble(curElement.toString());
                    break;
//                case "Value":
                case "protein":
                    latestCandy().value.protein = Double.parseDouble(curElement.toString());
                    break;
                case "fat":
                    latestCandy().value.fat = Double.parseDouble(curElement.toString());
                    break;
                case "carbohydrates":
                    latestCandy().value.carbohydrates = Double.parseDouble(curElement.toString());
                    break;
                case "Production":
                    latestCandy().production = curElement.toString();
                    break;
            }
        }

        private Candy latestCandy() {
            return parsedCandyList.get(parsedCandyList.size() - 1);
        }
    }

    @Override
    public List<Candy> parseFromFile(String filename) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        parsedCandyList = null;
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(filename, new MyHandler());
        } catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }
        return parsedCandyList;
    }
}
