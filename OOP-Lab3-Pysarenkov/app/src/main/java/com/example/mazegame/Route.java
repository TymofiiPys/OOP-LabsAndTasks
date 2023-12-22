package com.example.mazegame;


import java.util.ArrayList;
import java.util.List;

public class Route {
    List<Integer> nodes;
    List<Short> directions;

    public Route(List<Integer> nodes, List<Short> directions) {
        this.nodes = nodes;
        this.directions = directions;
    }

    public static Route routeExample1() {
        List<Integer> nodes = new ArrayList<>();
        List<Short> directions = new ArrayList<>();
        nodes.add(350);
        directions.add((short)1);
        nodes.add(400);
        directions.add((short)2);
        nodes.add(300);
        directions.add((short)1);
        nodes.add(200);
        directions.add((short)2);
        nodes.add(300);
        directions.add((short)3);
        nodes.add(300);
        directions.add((short)2);
        nodes.add(400);
        directions.add((short)1);
        nodes.add(400);
        directions.add((short)0);
        nodes.add(125);
        directions.add((short)1);
        return new Route(nodes, directions);
    }
}
