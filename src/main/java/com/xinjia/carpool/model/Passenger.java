package com.xinjia.carpool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * A class that represents a passenger in the system.
 * Extends the User class.
 * Contains information about the passenger's car preferences, number of people, and schedule.
 */
@Entity
@Table
@Getter
@Setter
public class Passenger extends User {
  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<CarFeature> carPreferences;

  @Column
  private Integer numOfPeople;

  @OneToOne(mappedBy = "passenger", cascade = CascadeType.ALL)
  @JsonIgnore
  private Schedule schedule;

}
