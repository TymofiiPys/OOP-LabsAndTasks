package org.example;

import java.util.ArrayList;
import java.util.List;

public class TestDataContainer {
    public static List<Candy> referenceList() {
        List<Candy> ret = new ArrayList<>();
        ret.add(new Candy(1,
                "Смакулька",
                150,
                "Шоколадна",
                new Ingredients(10.5,
                        25.0,
                        15.2,
                        "Молочний шоколад",
                        2.0),
                new Value(2.5,
                        8.0,
                        30.5),
                "СмакульКО"));
        ret.add(new Candy(2,
                "НоваШоколада",
                120,
                "Шоколадна",
                new Ingredients(12.0,
                        22.5,
                        20.0,
                        "Темний шоколад",
                        1.5),
                new Value(1.8,
                        5.0,
                        28.0),
                "НоваКом"));
        return ret;
    }

    public static List<Candy> referenceList2() {
        List<Candy> ret = new ArrayList<>();
        ret.add(new Candy(1,
                "Смакулька",
                150,
                "Шоколадна",
                new Ingredients(10.5,
                        25.0,
                        15.2,
                        "Молочний шоколад",
                        2.0),
                new Value(2.5,
                        8.0,
                        30.5),
                "СмакульКО"));
        ret.add(new Candy(2,
                "НоваШоколада",
                120,
                "Шоколадна",
                new Ingredients(12.0,
                        22.5,
                        20.0,
                        "Темний шоколад",
                        1.5),
                new Value(1.8,
                        5.0,
                        28.0),
                "НоваКом"));
        ret.add(new Candy(3,
                "IRISка",
                130,
                "Ірис",
                new Ingredients(11.0,
                        20.0,
                        25.0,
                        " ",
                        1.0),
                new Value(2.2, 5.5, 30.0),
                "ШОКОЛАДМАСТЕРС"));
        return ret;
    }
    public static List<Candy> referenceList2Sorted() {
        List<Candy> ret = new ArrayList<>();
        ret.add(new Candy(3,
                "IRISка",
                130,
                "Ірис",
                new Ingredients(11.0,
                        20.0,
                        25.0,
                        " ",
                        1.0),
                new Value(2.2, 5.5, 30.0),
                "ШОКОЛАДМАСТЕРС"));
        ret.add(new Candy(2,
                "НоваШоколада",
                120,
                "Шоколадна",
                new Ingredients(12.0,
                        22.5,
                        20.0,
                        "Темний шоколад",
                        1.5),
                new Value(1.8,
                        5.0,
                        28.0),
                "НоваКом"));
        ret.add(new Candy(1,
                "Смакулька",
                150,
                "Шоколадна",
                new Ingredients(10.5,
                        25.0,
                        15.2,
                        "Молочний шоколад",
                        2.0),
                new Value(2.5,
                        8.0,
                        30.5),
                "СмакульКО"));
        return ret;
    }
}
