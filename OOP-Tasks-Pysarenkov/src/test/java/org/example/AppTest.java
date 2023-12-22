package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testGetDiagonal(){
        double[][] A = {{38, 26, 41, 22}, {99,34,13,46},{47,75,15,4},{52,79,74,69}};
        double[] F = {62,61,36,23};
        Task2 t = new Task2(A, F);
        double[] aDiag = {0, 99, 75, 74};
        double[] bDiag = {26, 13, 4, 0};
        double[] cDiag = {38, 34, 15, 69};
        for (int i = 0; i < aDiag.length; i++) {
            assertEquals("Wrong lower diagonal, at element " + i, t.a[i], aDiag[i]);
        }
        for (int i = 0; i < bDiag.length; i++) {
            assertEquals("Wrong upper diagonal, at element " + i, t.b[i], bDiag[i]);
        }
        for (int i = 0; i < cDiag.length; i++) {
            assertEquals("Wrong main diagonal, at element " + i, t.c[i], cDiag[i]);
        }
    }

    public void testSeqThomas(){
        double[][] A = {{4, 8, 0, 0},
                {8, 18, 2, 0},
                {0, 2, 5, 1.5},
                {0, 0, 1.5, 1.75}};
        double[] F = {8, 18, 0.5, -1.75};
        Task2 t = new Task2(A, F);
        double[] expectedSol = {0, 1, 0, -1};
        double[] actualSol = t.seqThomas();
        for (int i = 0; i < expectedSol.length; i++) {
            assertEquals("Wrong solution component " + i, expectedSol[i], actualSol[i]);
        }
    }

    public void testParThomas(){
        double[][] A = {{4, 8, 0, 0},
                {8, 18, 2, 0},
                {0, 2, 5, 1.5},
                {0, 0, 1.5, 1.75}};
        double[] F = {8, 18, 0.5, -1.75};
        Task2 t = new Task2(A, F);
        double[] expectedSol = {0, 1, 0, -1};
        double[] actualSol = t.parThomas(2);
        for (int i = 0; i < expectedSol.length; i++) {
            assertEquals("Wrong solution component " + i, expectedSol[i], actualSol[i]);
        }
    }

    public void testForwardBackwardCom(){
        double[][] A = {{4, 8, 0, 0},
                {8, 18, 2, 0},
                {0, 2, 5, 1.5},
                {0, 0, 1.5, 1.75}};
        double[] F = {8, 18, 0.5, -1.75};
        Task2 test = new Task2(A, F);
        double[][] actualArr = test.testForwBackwCom(0, 4);
        double[][] expectedArr = {{4, 0, 0, 0, 0},
                {0, 2, 0, 0, 2},
                {0, 0, 3, 0, 0},
                {0, 0, 0, 1, -1}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals("Wrong solution component " + i + ", " + j, expectedArr[i][j], actualArr[i][j]);
            }
        }
    }
}
