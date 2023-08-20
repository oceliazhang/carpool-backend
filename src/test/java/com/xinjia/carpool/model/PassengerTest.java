package com.xinjia.carpool.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Passenger.
 */
public class PassengerTest {

  private Passenger passenger;
  private Passenger passenger2;

  /**
   * Test getter and setter.
   */
  @Test
  public void testGetterAndSetter() {

    passenger = new Passenger();
    List<CarFeature> carPreferences = Arrays.asList(CarFeature.HAS_KIDS_SEAT, CarFeature.HAS_AC);
    passenger.setCarPreferences(carPreferences);
    passenger.setNumOfPeople(3);
    Schedule schedule = new Schedule();
    passenger.setSchedule(schedule);

    assertEquals(carPreferences, passenger.getCarPreferences());
    assertEquals(3, passenger.getNumOfPeople());
    assertEquals(schedule, passenger.getSchedule());

    passenger2 = new Passenger();
    List<CarFeature> carPreferences2 = Arrays.asList(CarFeature.HAS_KIDS_SEAT, CarFeature.HAS_AC);
    passenger2.setCarPreferences(carPreferences2);
    passenger2.setNumOfPeople(4);
    Schedule schedule2 = new Schedule();
    passenger2.setSchedule(schedule2);

    assertEquals(carPreferences2, passenger2.getCarPreferences());
    assertEquals(4, passenger2.getNumOfPeople());
    assertEquals(schedule2, passenger2.getSchedule());

  }

}
