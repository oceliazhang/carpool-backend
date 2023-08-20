package com.xinjia.carpool.model.DTO;

import com.xinjia.carpool.model.CarFeature;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for FindDriverRequest.
 */
public class FindDriverRequestTest {

  private FindDriverRequest findDriverRequest;

  /**
   * Test getter and setter methods.
   */
  @Test
  public void testGetterAndSetter() {

    findDriverRequest = new FindDriverRequest();

    // First set of fields
    String destinationAddress1 = "600 California St";
    LocalDateTime arrivalTime1 = LocalDateTime.now().plusHours(1);
    Integer numOfPeople1 = 2;
    List<CarFeature> carPreferences1 = Arrays.asList(CarFeature.HAS_KIDS_SEAT, CarFeature.HAS_AC);

    // Second set of fields
    String destinationAddress2 = "456 Bush St";
    LocalDateTime arrivalTime2 = LocalDateTime.now().plusHours(2);
    Integer numOfPeople2 = 3;
    List<CarFeature> carPreferences2 = Arrays.asList(CarFeature.SMOKING_ALLOWED, CarFeature.HAS_AC);

    // Test first set of fields
    findDriverRequest.setDestinationAddress(destinationAddress1);
    findDriverRequest.setArrivalTime(arrivalTime1);
    findDriverRequest.setNumOfPeople(numOfPeople1);
    findDriverRequest.setCarPreferences(carPreferences1);

    assertEquals(destinationAddress1, findDriverRequest.getDestinationAddress());
    assertEquals(arrivalTime1, findDriverRequest.getArrivalTime());
    assertEquals(numOfPeople1, findDriverRequest.getNumOfPeople());
    assertEquals(carPreferences1, findDriverRequest.getCarPreferences());

    // Test second set of fields
    findDriverRequest.setDestinationAddress(destinationAddress2);
    findDriverRequest.setArrivalTime(arrivalTime2);
    findDriverRequest.setNumOfPeople(numOfPeople2);
    findDriverRequest.setCarPreferences(carPreferences2);

    assertEquals(destinationAddress2, findDriverRequest.getDestinationAddress());
    assertEquals(arrivalTime2, findDriverRequest.getArrivalTime());
    assertEquals(numOfPeople2, findDriverRequest.getNumOfPeople());
    assertEquals(carPreferences2, findDriverRequest.getCarPreferences());
  }
}
