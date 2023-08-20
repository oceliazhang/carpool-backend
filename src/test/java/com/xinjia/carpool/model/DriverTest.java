package com.xinjia.carpool.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Driver class
 */
public class DriverTest {
  private Driver driver;
  private Car car;

  /**
   * Set up a driver and a car
   */
  @BeforeEach
  public void setUp() {
    driver = new Driver();
    car = new Car();
    car.setId(1L);
    car.setMake("Toyota");
    car.setModel("Corolla");
    car.setNumSeats(4);
    car.setNumLuggage(2);
    car.setColor("Blue");
    car.setLicensePlate("ABC123");

    driver.setId(1L);
    driver.setUsername("Zhang");
    driver.setPassword("990624");
    driver.setName("Xinjia");
    driver.setGender(Gender.FEMALE);
    driver.setCar(car);
    driver.setRating(BigDecimal.valueOf(5.0));
    driver.setItinerary(new ArrayList<>());
    driver.setMatchWeight(0);
    driver.setRide(new Ride());
  }

  /**
   * Test getter and setter methods
   */
  @Test
  public void testGetterAndSetter() {
    assertEquals(1L, driver.getId());
    assertEquals("Zhang", driver.getUsername());
    assertEquals("990624", driver.getPassword());
    assertEquals("Xinjia", driver.getName());
    assertEquals(Gender.FEMALE, driver.getGender());
    assertEquals(car, driver.getCar());
    assertEquals(BigDecimal.valueOf(5.0), driver.getRating());
    assertEquals(new ArrayList<>(), driver.getItinerary());
    assertEquals(0, driver.getMatchWeight());
    assertNotNull(driver.getRide());
  }

  /**
   * Test add schedule method
   */
  @Test
  public void testAddSchedule() {
    Schedule schedule = new Schedule();
    Schedule schedule2 = new Schedule();
    driver.addSchedule(schedule);
    driver.addSchedule(schedule2);
    List<Schedule> itinerary = driver.getItinerary();
    assertEquals(2, itinerary.size());
    assertEquals(schedule, itinerary.get(0));
    assertEquals(schedule2, itinerary.get(1));
  }

  /**
   * Test remove schedule method
   */
  @Test
  public void testRemoveSchedule() {
    Schedule schedule1 = new Schedule();
    Schedule schedule2 = new Schedule();
    Schedule schedule3 = new Schedule();

    driver.addSchedule(schedule1);
    driver.addSchedule(schedule2);
    driver.addSchedule(schedule3);

    // Check the initial state of the itinerary
    List<Schedule> itinerary = driver.getItinerary();
    assertEquals(3, itinerary.size());
    assertEquals(schedule1, itinerary.get(0));
    assertEquals(schedule2, itinerary.get(1));
    assertEquals(schedule3, itinerary.get(2));

    // Remove schedule2 and check the updated state of the itinerary
    driver.removeSchedule(schedule2);
    itinerary = driver.getItinerary();
    assertEquals(2, itinerary.size());
    assertEquals(schedule1, itinerary.get(0));
    assertEquals(schedule3, itinerary.get(1));

    // Try to remove a schedule that does not exist in the itinerary
    Schedule nonExistentSchedule = new Schedule();
    driver.removeSchedule(nonExistentSchedule);
    itinerary = driver.getItinerary();
    assertEquals(2, itinerary.size());
    assertEquals(schedule1, itinerary.get(0));
    assertEquals(schedule3, itinerary.get(1));
  }

  /**
   * Test compareTo method
   */
  @Test
  public void testCompareTo() {
    Driver otherDriver = new Driver();
    otherDriver.setMatchWeight(5);

    driver.setMatchWeight(10);
    assertTrue(driver.compareTo(otherDriver) > 0);

    driver.setMatchWeight(5);
    assertEquals(0, driver.compareTo(otherDriver));

    driver.setMatchWeight(0);
    assertTrue(driver.compareTo(otherDriver) < 0);
  }
}
