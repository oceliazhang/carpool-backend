package com.xinjia.carpool.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Schedule
 */
public class ScheduleTest {

  private Schedule schedule;

  /**
   * Test getter and setter
   */
  @Test
  public void testGetterAndSetter() {

    schedule = new Schedule();

    // First set of fields
    long id1 = 1L;
    String pickupAddress1 = "123 Main St, New York, NY";
    String destinationAddress1 = "456 Broadway St, New York, NY";
    LocalDateTime pickupTime1 = LocalDateTime.of
        (2023, 5, 1, 9, 0);
    LocalDateTime arrivalTime1 = LocalDateTime.of
        (2023, 5, 1, 14, 0);
    Driver driver1 = new Driver();
    Passenger passenger1 = new Passenger();

    // Second set of fields
    long id2 = 2L;
    String pickupAddress2 = "789 Park Ave, New York, NY";
    String destinationAddress2 = "321 Wall St, New York, NY";
    LocalDateTime pickupTime2 = LocalDateTime.of
        (2023, 5, 2, 8, 0);
    LocalDateTime arrivalTime2 = LocalDateTime.of
        (2023, 5, 2, 16, 0);
    Driver driver2 = new Driver();
    Passenger passenger2 = new Passenger();

    // Test first set of fields
    schedule.setId(id1);
    schedule.setPickupAddress(pickupAddress1);
    schedule.setDestinationAddress(destinationAddress1);
    schedule.setPickupTime(pickupTime1);
    schedule.setArrivalTime(arrivalTime1);
    schedule.setDriver(driver1);
    schedule.setPassenger(passenger1);

    assertEquals(id1, schedule.getId());
    assertEquals(pickupAddress1, schedule.getPickupAddress());
    assertEquals(destinationAddress1, schedule.getDestinationAddress());
    assertEquals(pickupTime1, schedule.getPickupTime());
    assertEquals(arrivalTime1, schedule.getArrivalTime());
    assertEquals(driver1, schedule.getDriver());
    assertEquals(passenger1, schedule.getPassenger());

    // Test second set of fields
    schedule.setId(id2);
    schedule.setPickupAddress(pickupAddress2);
    schedule.setDestinationAddress(destinationAddress2);
    schedule.setPickupTime(pickupTime2);
    schedule.setArrivalTime(arrivalTime2);
    schedule.setDriver(driver2);
    schedule.setPassenger(passenger2);

    assertEquals(id2, schedule.getId());
    assertEquals(pickupAddress2, schedule.getPickupAddress());
    assertEquals(destinationAddress2, schedule.getDestinationAddress());
    assertEquals(pickupTime2, schedule.getPickupTime());
    assertEquals(arrivalTime2, schedule.getArrivalTime());
    assertEquals(driver2, schedule.getDriver());
    assertEquals(passenger2, schedule.getPassenger());
  }
}
