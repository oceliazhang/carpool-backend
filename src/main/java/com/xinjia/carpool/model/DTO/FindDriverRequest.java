package com.xinjia.carpool.model.DTO;

import com.xinjia.carpool.model.Car;
import com.xinjia.carpool.model.CarFeature;
import com.xinjia.carpool.model.Gender;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * A class representing a request to find available drivers based on certain criteria.
 */
@Getter
@Setter
public class FindDriverRequest {

  // The destination address of the passenger.
  private String destinationAddress;

  // The estimated arrival time of the passenger at the destination.
  private LocalDateTime arrivalTime;

  // The number of people who will be riding with the passenger.
  private Integer numOfPeople;

  // A list of car features that the passenger prefers for their ride.
  private List<CarFeature> carPreferences;
}

