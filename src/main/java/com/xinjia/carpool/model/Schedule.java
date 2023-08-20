package com.xinjia.carpool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Schedule entity with pickup address, destination address, pickup time,
 * and arrival time.
 */
@Entity
@Table(name = "schedule")
@Getter
@Setter
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String pickupAddress;

  @Column
  private String destinationAddress;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime pickupTime;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime arrivalTime;

  @ManyToOne
  @JsonIgnore
  private Driver driver;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "passenger_id", referencedColumnName = "id")
  @JsonIgnore
  private Passenger passenger;
}

