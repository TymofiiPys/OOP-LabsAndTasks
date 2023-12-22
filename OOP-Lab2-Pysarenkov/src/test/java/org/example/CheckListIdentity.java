package org.example;

import org.junit.Assert;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CheckListIdentity {
    private final static double testDelta = 0.0001;
    public static boolean checkListsEqual(List<Candy> expected, List<Candy> actual){
        assertEquals("Розміри списків не співпадають", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Candy candyExp = expected.get(i);
            Candy candyAct = actual.get(i);
            assertEquals("Поле id, елемент " + i, candyExp.ID, candyAct.ID);
            assertEquals("Поле Name, елемент " + i, candyExp.name, candyAct.name);
            assertEquals("Поле Energy, елемент " + i, candyExp.energy, candyAct.energy);
            assertEquals("Поле Type, елемент " + i, candyExp.type, candyAct.type);
            Ingredients ingrExp = candyExp.ingredients;
            Ingredients ingrAct = candyAct.ingredients;
            assertEquals("Поле water, елемент " + i, ingrExp.water, ingrAct.water, testDelta);
            assertEquals("Поле sugar, елемент " + i, ingrExp.sugar, ingrAct.sugar, testDelta);
            assertEquals("Поле fructose, елемент " + i, ingrExp.fructose, ingrAct.fructose, testDelta);
            assertEquals("Поле choc_type, елемент " + i, ingrExp.choc_type, ingrAct.choc_type);
            assertEquals("Поле vanillin, елемент " + i, ingrExp.vanillin, ingrAct.vanillin, testDelta);
            Value valueExp = candyExp.value;
            Value valueAct = candyAct.value;
            assertEquals("Поле protein, елемент " + i, valueExp.protein, valueAct.protein, testDelta);
            assertEquals("Поле fat, елемент " + i, valueExp.fat, valueAct.fat, testDelta);
            assertEquals("Поле carbohydrates, елемент " + i, valueExp.carbohydrates, valueAct.carbohydrates, testDelta);
            assertEquals("Поле Production, елемент " + i, candyExp.production, candyAct.production);
        }
        return true;
    }
}
