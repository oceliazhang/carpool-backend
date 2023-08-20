package com.xinjia.carpool.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Ride entity with destination address and arrival time.
 */
@Embeddable
@Getter
@Setter
public class Ride {

  private String destinationAddress;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime arrivalTime;

  /**
   * Default constructor for the Ride class.
   */
  public Ride() {

  }
}
