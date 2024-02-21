/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;

    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(2);
        setB = BoundedSetOfNaturals.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
        setC = BoundedSetOfNaturals.fromArray(new int[] { 50, 60 });
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @DisplayName("Test add element to set")
    @Test
    public void testAddElement() {

        // add element to set
        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());
        
        // try to add an element to a set that already contains it
        assertThrows(IllegalArgumentException.class, () -> setA.add(99));
        
        // try to add a negative element to a set
        assertThrows(IllegalArgumentException.class, () -> setA.add(-99));

        // try to add an element to a full set
        setA.add(100);
        assertThrows(IllegalArgumentException.class, () -> setA.add(101));

    }

    @DisplayName("Test add elements to set from array")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[] { 10, -20, -30 };

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));

        // must fail with exception
        int[] elems2 = new int[] { 10, 20, 20, 30};
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems2));
    }

    @DisplayName("Test equals")
    @Test
    public void testEquals() {
        // test for equality
        assertTrue(setB.equals(setB), "equals: set not equal to itself");
        assertTrue(setB.equals(BoundedSetOfNaturals.fromArray(new int[] { 10, 20, 30, 40, 50, 60 })), "equals: set not equal to itself");
        
        // test for inequality
        assertFalse(setB.equals(null), "equals: set equal to null");
        assertFalse(setB.equals(new Object()), "equals: set equal to another object");
        assertFalse(setB.equals(setA), "equals: set equal to another set");
        assertFalse(setB.equals(setC), "equals: set equal to another set");
    }

    @DisplayName("Test intersection")
    @Test
    public void testIntersects() {
        // test for intersection
        assertTrue(setB.intersects(setC), "intersects: sets do not intersect");
        assertTrue(setC.intersects(setB), "intersects: sets do not intersect");
        
        // test for no intersection
        assertFalse(setA.intersects(setB), "intersects: sets intersect");
        assertFalse(setB.intersects(setA), "intersects: sets intersect");

        setA.add(10);
        assertTrue(setA.intersects(setB), "intersects: sets do not intersect");

    }
    

}
