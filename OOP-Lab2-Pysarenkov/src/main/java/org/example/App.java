package org.example;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

public class App {
    public List<Candy> candyList;

    public void sortListByCandyName() {
        candyList.sort(new Comparator<Candy>() {
            @Override
            public int compare(Candy c1, Candy c2) {
                return c1.name.compareTo(c2.name);
            }
        });
    }

    public void printList() {
        System.out.println("Список цукерок\n");
        for (Candy curCandy : candyList) {
            System.out.println("ID: " + curCandy.ID);
            System.out.println("Назва: " + curCandy.name);
            System.out.println("Калорійність: " + curCandy.energy + " ккал");
            System.out.println("Тип: " + curCandy.type);
            System.out.println("Інгредієнти: ");
            System.out.println("\tВода: " + curCandy.ingredients.water + "%");
            System.out.println("\tЦукор: " + curCandy.ingredients.sugar + " мг");
            System.out.println("\tФруктоза: " + curCandy.ingredients.fructose + " мг");
            if (curCandy.type.equals("Шоколадна")) {
                System.out.println("\tТип шоколаду: " + curCandy.ingredients.choc_type);
            }
            System.out.println("\tВанілін: " + curCandy.ingredients.vanillin + " мг");
            System.out.println("Енергетична цінність:");
            System.out.println("\tБілки: " + curCandy.value.protein + " г");
            System.out.println("\tЖир: " + curCandy.value.fat + " г");
            System.out.println("\tВуглеводи: " + curCandy.value.carbohydrates + " г");
            System.out.println("Виробник: " + curCandy.production);
            System.out.println();
        }
    }

    public void parseFromXML(String xmlPath, String xsdPath, int parseType) {
        XMLParser parser = null;
        XSDValidator validator = new XSDValidator();
        try {
            if(!validator.isValid(xmlPath, xsdPath)){
                throw new SAXException();
            }
        } catch (IOException | SAXException e) {
            System.out.println("Файл не пройшов валідацію по xsd-схемі");
            return;
        }
        switch (parseType) {
            case 0:
                parser = new CandySAXParser();
                break;
            case 1:
                parser = new DOMParser();
                break;
            case 2:
                parser = new StAXParser();
                break;
        }
        if (parser != null) {
            candyList = parser.parseFromFile(xmlPath);
        }
    }

    public static void main(String[] args) {
        App a = new App();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Назва файлу: ");
        String filename = scanner.nextLine();
        String xsdPath = "candy.xsd";
        a.parseFromXML(filename, xsdPath, new Random().nextInt(3));
        if(!a.candyList.isEmpty())
            a.printList();
    }
}