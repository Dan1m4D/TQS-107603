package tqs.cars.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import tqs.cars.model.Car;

class CarTest {

    @Test
    void testEquals() {
        Car audi = new Car(1L, "Audi", "A8");
        Car audi2 = new Car(1L, "Audi", "A8");
        Car bmw = new Car("BMW", "X5");
        Car citroen = new Car("Citroen", "C4");

        assertEquals(audi, audi);
        assertEquals(audi, audi2);
        assertNotEquals(audi, bmw);
        assertNotEquals(audi, citroen);
        assertNotEquals("Audi", audi);
        assertNotEquals(null, citroen);
    }

    @Test
    void testHashCode() {
        Car audi = new Car(1L, "Audi", "A8");
        Car audi2 = new Car(1L, "Audi", "A8");
        Car bmw = new Car("BMW", "X5");
        Car citroen = new Car("Citroen", "C4");

        assertEquals(audi.hashCode(), audi2.hashCode());
        assertNotEquals(audi.hashCode(), bmw.hashCode());
        assertNotEquals(audi.hashCode(), citroen.hashCode());
    }

}
