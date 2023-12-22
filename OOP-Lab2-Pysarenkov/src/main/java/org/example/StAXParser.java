package org.example;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StAXParser implements XMLParser {
    @Override
    public List<Candy> parseFromFile(String filename) {
        List<Candy> parsedCandyList = new ArrayList<>();
        Candy curCandy = new Candy();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(filename));
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    switch (startElement.getName().getLocalPart()) {
//                        case "CandyList":
////                            parsedCandyList = new ArrayList<>();
                        case "Candy":
                            curCandy = new Candy();
                            curCandy.ID = Integer.parseInt(startElement
                                    .getAttributeByName(new QName("id"))
                                    .getValue().substring(3));
                            curCandy.type = startElement.getAttributeByName(new QName("Type")).getValue();
                            break;
                        case "Name":
                            nextEvent = reader.nextEvent();
                            curCandy.name = nextEvent.asCharacters().getData();
                            break;
                        case "Energy":
                            nextEvent = reader.nextEvent();
                            curCandy.energy = Integer.parseInt(nextEvent.asCharacters().getData());
                            break;
//                        case "Type":
//                            nextEvent = reader.nextEvent();
//                            curCandy.type = nextEvent.asCharacters().getData();
//                            break;
                        case "Ingredients":
                            curCandy.ingredients = new Ingredients();
                            break;
                        case "water":
                            nextEvent = reader.nextEvent();
                            curCandy.ingredients.water = Double.parseDouble(nextEvent.asCharacters().getData());
                            break;
                        case "sugar":
                            nextEvent = reader.nextEvent();
                            curCandy.ingredients.sugar = Double.parseDouble(nextEvent.asCharacters().getData());
                            break;
                        case "fructose":
                            nextEvent = reader.nextEvent();
                            curCandy.ingredients.fructose = Double.parseDouble(nextEvent.asCharacters().getData());
                            break;
                        case "choc_type":
                            nextEvent = reader.nextEvent();
                            curCandy.ingredients.choc_type = nextEvent.asCharacters().getData();
                            break;
                        case "vanillin":
                            nextEvent = reader.nextEvent();
                            curCandy.ingredients.vanillin = Double.parseDouble(nextEvent.asCharacters().getData());
                            break;
                        case "Value":
                            curCandy.value = new Value();
                            break;
                        case "protein":
                            nextEvent = reader.nextEvent();
                            curCandy.value.protein = Double.parseDouble(nextEvent.asCharacters().getData());
                            break;
                        case "fat":
                            nextEvent = reader.nextEvent();
                            curCandy.value.fat = Double.parseDouble(nextEvent.asCharacters().getData());
                            break;
                        case "carbohydrates":
                            nextEvent = reader.nextEvent();
                            curCandy.value.carbohydrates = Double.parseDouble(nextEvent.asCharacters().getData());
                            break;
                        case "Production":
                            nextEvent = reader.nextEvent();
                            curCandy.production = nextEvent.asCharacters().getData();
                            break;
                    }
                } else if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName()
                            .getLocalPart()
                            .equals("Candy")) {
                        parsedCandyList.add(curCandy);
                    }
                }
            }

        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return parsedCandyList;
    }
}
