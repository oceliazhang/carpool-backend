package com.xinjia.carpool.model;

import java.math.BigDecimal;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * A class that represents a driver in the system.
 * Extends the User class and implements the Comparable interface.
 * Contains information about the driver's car, rating, itinerary, match weight and ride.
 */
@Entity
@Table
@Getter
@Setter
public class Driver extends User implements Comparable<Driver> {

  @OneToOne
  @JoinColumn(name = "car_id")
  private Car car;

  @Column
  private BigDecimal rating;

  @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
  private List<Schedule> itinerary;

  @Column
  private Integer matchWeight = 0;

  @Embedded
  private Ride ride;

  /**
   * Default constructor for the Driver class.
   */
  public Driver() {

  }

  /**
   * Adds a schedule to the driver's itinerary.
   *
   * @param schedule the schedule to be added
   */
  public void addSchedule(Schedule schedule) {
    itinerary.add(schedule);
  }

  /**
   * Removes a schedule from the driver's itinerary.
   *
   * @param schedule the schedule to be removed
   */
  public void removeSchedule(Schedule schedule) {
    itinerary.remove(schedule);
  }

  /**
   * Compares this Driver object with another Driver object based on their match weights.
   * The comparison is based on the matchWeight field of each object.
   *
   * @param other the other Driver object to be compared with this one
   * @return a negative integer, zero, or a positive integer as this Driver object is less than,
   *         equal to, or greater than the specified object, respectively
   */
  @Override
  public int compareTo(Driver other) {
    return Integer.compare(this.matchWeight, other.getMatchWeight());
  }

}
