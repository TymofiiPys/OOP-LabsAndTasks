package org.example;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void sortListByCandyName() {
        App a = new App();
        a.candyList = TestDataContainer.referenceList2();
        a.sortListByCandyName();
        List<Candy> expected = TestDataContainer.referenceList2Sorted();
        List<Candy> actual = a.candyList;
        assertTrue(CheckListIdentity.checkListsEqual(expected, actual));
    }
}