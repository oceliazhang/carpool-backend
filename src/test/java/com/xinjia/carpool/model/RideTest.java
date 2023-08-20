package com.xinjia.carpool.model;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Ride.
 */
public class RideTest {

  private Ride ride1;
  private Ride ride2;

  /**
   * Test getter and setter for Ride.
   */
  @Test
  public void testGetterAndSetter() {
    ride1 = new Ride();
    ride2 = new Ride();

    String destinationAddress1 = "600 California St, San Francisco, CA";
    ride1.setDestinationAddress(destinationAddress1);
    LocalDateTime arrivalTime1 = LocalDateTime.of(2023, 5, 1, 14, 0);
    ride1.setArrivalTime(arrivalTime1);

    assertEquals(destinationAddress1, ride1.getDestinationAddress());
    assertEquals(arrivalTime1, ride1.getArrivalTime());

    String destinationAddress2 = "650 California St, San Francisco, CA";
    ride2.setDestinationAddress(destinationAddress2);
    LocalDateTime arrivalTime2 = LocalDateTime.of(2023, 5, 1, 14, 0);
    ride2.setArrivalTime(arrivalTime2);

    assertEquals(destinationAddress2, ride2.getDestinationAddress());
    assertEquals(arrivalTime2, ride2.getArrivalTime());
  }

}


