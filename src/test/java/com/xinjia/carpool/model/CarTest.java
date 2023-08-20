package com.xinjia.carpool.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Car class
 */
public class CarTest {

  /**
   * Test getter and setter methods
   */
  @Test
  public void testGetterAndSetter() {
    Car car = new Car();

    car.setId(1L);
    car.setMake("Toyota");
    car.setModel("Corolla");
    car.setNumSeats(4);
    car.setNumLuggage(2);
    car.setColor("Blue");
    car.setLicensePlate("ABC123");

    List<CarFeature> carFeatures = Arrays.asList(CarFeature.HAS_AC, CarFeature.PET_ALLOWED);
    car.setCarFeatures(carFeatures);

    assertEquals(1L, car.getId());
    assertEquals("Toyota", car.getMake());
    assertEquals("Corolla", car.getModel());
    assertEquals(4, car.getNumSeats());
    assertEquals(2, car.getNumLuggage());
    assertEquals("Blue", car.getColor());
    assertEquals("ABC123", car.getLicensePlate());
    assertEquals(carFeatures, car.getCarFeatures());

    Car car2 = new Car();

    car2.setId(2L);
    car2.setMake("Audi");
    car2.setModel("A5");
    car2.setNumSeats(3);
    car2.setNumLuggage(1);
    car2.setColor("Black");
    car2.setLicensePlate("999417");

    List<CarFeature> carFeatures2 = Arrays.asList(CarFeature.HAS_AC, CarFeature.PET_ALLOWED);
    car2.setCarFeatures(carFeatures2);

    assertEquals(2L, car2.getId());
    assertEquals("Audi", car2.getMake());
    assertEquals("A5", car2.getModel());
    assertEquals(3, car2.getNumSeats());
    assertEquals(1, car2.getNumLuggage());
    assertEquals("Black", car2.getColor());
    assertEquals("999417", car2.getLicensePlate());
    assertEquals(carFeatures, car2.getCarFeatures());
  }
}
