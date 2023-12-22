package org.example;

public class Ingredients {
    public double water;
    public double sugar;
    public double fructose;
    public String choc_type;
    public double vanillin;

    public Ingredients() {
    }

    public Ingredients(double water, double sugar, double fructose,
                       String choc_type, double vanillin) {
        this.water = water;
        this.sugar = sugar;
        this.fructose = fructose;
        this.choc_type = choc_type;
        this.vanillin = vanillin;
    }
}
