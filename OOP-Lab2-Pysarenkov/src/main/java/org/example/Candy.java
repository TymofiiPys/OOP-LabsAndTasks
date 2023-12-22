package org.example;

import java.util.List;

public class Candy {
    public int ID;
    public String name;
    public int energy;
    public String type;
    public Ingredients ingredients;
    public Value value;
    public String production;

    public Candy() {
    }

    public Candy(int ID, String name, int energy, String type,
                 Ingredients ingredients, Value value,
                 String production) {
        this.ID = ID;
        this.name = name;
        this.energy = energy;
        this.type = type;
        this.ingredients = ingredients;
        this.value = value;
        this.production = production;
    }
}
