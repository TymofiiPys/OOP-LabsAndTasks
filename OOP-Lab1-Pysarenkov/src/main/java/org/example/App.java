package org.example;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

/**
 * Lab1 main class.
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            AirComp ac = AirComp.getAC("aviakomp.db");
            while (true) {
                System.out.println("Оберіть дію:\n" +
                        "p - вивести базу даних\n" +
                        "s - сортувати базу даних за дальністю польоту\n" +
                        "f - знайти літак із споживанням пального у даному діапазоні\n" +
                        "1 - підрахувати сумарну місткість літаків\n" +
                        "2 - підрахувати сумарну вантажопідйомність літаків\n" +
                        "a - додати літак у базу даних\n" +
                        "d - видалити літак із бази даних\n" +
                        "e - вихід");
                String op = sc.nextLine();
                if (op.length() != 1) {
                    continue;
                }
                switch (op.charAt(0)) {
                    case 'p':
                        ac.printAllPlanes();
                        break;
                    case 's':
                        ac.sortByRange();
                        ac.printAllPlanes();
                        break;
                    case 'f':
                        double min, max;
                        System.out.print("Введіть ліву межу діапазону споживання пального: ");
                        try {
                            min = Double.parseDouble(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Помилка вводу");
                            continue;
                        }
                        System.out.print("Введіть праву межу діапазону споживання пального: ");
                        try {
                            max = Double.parseDouble(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Помилка вводу");
                            continue;
                        }
                        List<Plane> diap = ac.findPlanesByFuelConsumption(min, max);
                        ac.printAllPlanes(diap);
                        break;
                    case '1':
                        System.out.println("Загальна місткість усіх літаків у базі даних складає "
                                + ac.countSeats() + " осіб");
                        break;
                    case '2':
                        System.out.println("Загальна вантажопідйомність усіх літаків у базі даних складає "
                                + ac.countCargoCapacity() + " тон");
                        break;
                    case 'a':
                        System.out.print("Введіть тип літака: П - пасажирський, В - вантажний");
                        String type = sc.nextLine();
                        if(!type.equals("П") && !type.equals("В")){
                            continue;
                        }
                        System.out.print("Введіть модель літака: ");
                        String model = sc.nextLine();
                        System.out.print("Введіть дальність польоту літака: ");
                        int range = 0, seats = 0;
                        double fuelConsumption = 0., cargo = 0.;
                        try {
                            range = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Помилка вводу");
                            continue;
                        }
                        System.out.print("Введіть середнє споживання пального літака: ");
                        try {
                            fuelConsumption = Double.parseDouble(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Помилка вводу");
                            continue;
                        }
                        Plane p = null;
                        switch (type.charAt(0)){
                            case 'П':
                                System.out.print("Введіть місткість літака: ");
                                try {
                                    seats = Integer.parseInt(sc.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Помилка вводу");
                                    continue;
                                }
                                p = new PassengerPlane(ac.getNextPlaneId(), model, range, fuelConsumption, seats);
                            case 'В':
                                System.out.print("Введіть вантажопідйомність літака: ");
                                try {
                                    cargo = Double.parseDouble(sc.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Помилка вводу");
                                    continue;
                                }
                                p = new CargoPlane(ac.getNextPlaneId(), model, range, fuelConsumption, cargo);
                        }
                        ac.addPlane(p);
                        break;
                    case 'd':
                        System.out.print("Введіть id літака, який потрібно видалити: ");
                        int id;
                        try {
                            id = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Помилка вводу");
                            continue;
                        }
                        ac.deletePlane(id);
                        break;
                    case 'e':
                        return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Помилка зчитування бази даних");
        }

    }
}
