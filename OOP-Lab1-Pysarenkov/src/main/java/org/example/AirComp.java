package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AirComp {
    private static AirComp ac;

    private String filename;
    public List<Plane> planes;

    private AirComp() {
        this.planes = new ArrayList<>();
    }

    private AirComp(String filename) throws SQLException {
        this.planes = new ArrayList<>();
        this.filename = filename;
        parsePlanes(filename);
    }

    public static AirComp getAC() {
        if (ac == null) {
            ac = new AirComp();
        }
        return ac;
    }

    public static AirComp getAC(String filename) throws SQLException {
        if (ac == null) {
            ac = new AirComp(filename);
        }
        return ac;
    }

    private void parsePlanes(String filename) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        Statement statmt = conn.createStatement();
        ResultSet table = statmt.executeQuery("SELECT * FROM Авіакомпанія");

        while (table.next()) {
            System.out.println(table.getInt("id") + "\t" +
                    table.getString("Модель") + "\t" +
                    table.getString("Тип") + "\t" +
                    table.getInt("Місткість_осіб") + "\t" +
                    table.getDouble("Вантажопідйомність_т") + "\t" +
                    table.getInt("Дальність_польоту_км") + "\t" +
                    table.getDouble("Споживання_пального_л/км"));

            Plane p = null;
            if (table.getString("Тип").equals("Вантажний")) {
                p = new CargoPlane(table.getInt("id"),
                        table.getString("Модель"),
                        table.getInt("Дальність_польоту_км"),
                        table.getDouble("Споживання_пального_л/км"),
                        table.getDouble("Вантажопідйомність_т"));
            } else if (table.getString("Тип").equals("Пасажирський")) {
                p = new PassengerPlane(table.getInt("id"),
                        table.getString("Модель"),
                        table.getInt("Дальність_польоту_км"),
                        table.getDouble("Споживання_пального_л/км"),
                        table.getInt("Місткість_осіб"));
            }

            planes.add(p);
        }
    }

    public int getNextPlaneId() {
        int i = 1;
        List<Plane> sortedById = new ArrayList<>(planes);
        sortedById.sort(new Comparator<Plane>() {
            @Override
            public int compare(Plane p1, Plane p2) {
                return Integer.compare(p1.getId(), p2.getId());
            }
        });
        for (Plane p :
                sortedById) {
            if(p.getId() != i){
                break;
            }
            i++;
        }
        return i;
    }

    public void addPlane(Plane p) {
        planes.add(p);
    }

    public void deletePlane(int id) {
        for(Plane p : planes){
            if(p.getId() == id){
                planes.remove(p);
                return;
            }
        }
        System.out.println("Не знайдено літака з цим id");
    }

    public void printAllPlanes() {
        System.out.println("id\tМодель\tТип\tМісткість (осіб)\tВантажопідйомність (т)\t" +
                "Дальність польоту (км)\tСпоживання пального (л/км)");

        for (Plane p : planes) {
            String mcc = p.getMaxCargoCapacity() == 0 ? "NULL" : "" + p.getMaxCargoCapacity();
            String seats = p.getSeats() == 0 ? "NULL" : "" + p.getSeats();
            String type = "";
            if (mcc.equals("NULL")) {
                type = "Пасажирський";
            } else {
                type = "Вантажний";
            }
            System.out.println(p.getId() + "\t" + p.getModelName() + "\t" + type + "\t" + seats + "\t" + mcc
                    + "\t" + p.getRange() + "\t" + p.getFuelConsumption());
        }
    }

    public void printAllPlanes(List<Plane> subList) {
        System.out.println("id\tМодель\tТип\tМісткість (осіб)\tВантажопідйомність (т)\t" +
                "Дальність польоту (км)\tСпоживання пального (л/км)");

        for (Plane p : subList) {
            String mcc = p.getMaxCargoCapacity() == 0 ? "NULL" : "" + p.getMaxCargoCapacity();
            String seats = p.getSeats() == 0 ? "NULL" : "" + p.getSeats();
            String type = "";
            if (mcc.equals("NULL")) {
                type = "Пасажирський";
            } else {
                type = "Вантажний";
            }
            System.out.println(p.getId() + "\t" + p.getModelName() + "\t" + type + "\t" + seats + "\t" + mcc
                    + "\t" + p.getRange() + "\t" + p.getFuelConsumption());
        }
    }

    public double countCargoCapacity() {
        double totalCap = 0;
        for (Plane p : planes) {
            totalCap += p.getMaxCargoCapacity();
        }
        return totalCap;
    }

    public int countSeats() {
        int totalSeats = 0;
        for (Plane p : planes) {
            totalSeats += p.getSeats();
        }
        return totalSeats;
    }

    public void sortByRange() {
        planes.sort(new Comparator<Plane>() {
            @Override
            public int compare(Plane p1, Plane p2) {
                return Integer.compare(p1.getRange(), p2.getRange());
            }
        });
    }

    public List<Plane> findPlanesByFuelConsumption(double minFuelConsumption, double maxFuelConsumption) {
        return Stream.of(planes.toArray(new Plane[0]))
                .filter(p -> (p.getFuelConsumption() >= minFuelConsumption)
                        && (p.getFuelConsumption() <= maxFuelConsumption))
                .toList();
    }
}
