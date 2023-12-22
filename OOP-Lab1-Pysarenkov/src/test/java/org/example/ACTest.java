package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.util.List;

public class ACTest extends TestCase{
    AirComp ac;
    public ACTest(String testName){
        super( testName );
        try {
            ac = AirComp.getAC("aviakomp.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Test suite()
    {
        return new TestSuite( ACTest.class );
    }
    public void testTotalSeats(){
        int actualSeats = ac.countSeats();
        assertEquals( 3389, actualSeats);
    }

    public int[] parseIds(){
        int[] ids = new int[ac.planes.size()];
        int i = 0;
        for(Plane p : ac.planes){
            ids[i] = p.getId();
            i++;
        }
        return ids;
    }
    public void testTotalCapacity(){
        double actualCap = ac.countCargoCapacity();
        assertEquals( 420.7, actualCap);
    }
    public void testSortByRange(){
        int[] expectedIds = {5, 12, 15, 1, 7, 9, 4, 6, 10, 11, 2, 13, 14, 8, 3};
        ac.sortByRange();
        int[] actualIds = parseIds();
        for (int i = 0; i < 15; i++) {
            assertEquals("Failure at element " + i, expectedIds[i], actualIds[i]);
        }
    }
    public boolean modelInList(String model, List<Plane> list){
        for(Plane p : list){
            if (model.equals(p.getModelName())) {
                return true;
            }
        }
        return false;
    }
    public void testFilter(){
        List<Plane> filtered = ac.findPlanesByFuelConsumption(2.7, 3.7);
        assertTrue(modelInList("Boeing 747", filtered));
        assertFalse(modelInList("Antonov AN-225", filtered));
        assertTrue(modelInList("Boeing 787", filtered));
        assertFalse(modelInList("Cessna 172", filtered));
    }
}
