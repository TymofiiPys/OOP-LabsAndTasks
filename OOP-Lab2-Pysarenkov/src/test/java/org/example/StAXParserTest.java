package org.example;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StAXParserTest {
    @Test
    public void parseFromFile() {
        XMLParser parser = new StAXParser();
        List<Candy> expected = TestDataContainer.referenceList();
        List<Candy> actual = parser.parseFromFile("candylist_test.xml");
        assertTrue(CheckListIdentity.checkListsEqual(expected, actual));
    }
}