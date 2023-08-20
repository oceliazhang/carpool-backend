package com.xinjia.carpool.service;

import com.xinjia.carpool.model.Passenger;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.repository.PassengerRepository;
import com.xinjia.carpool.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ScheduleService class.
 */
@SpringBootTest
public class ScheduleServiceTest {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private PassengerRepository passengerRepository;

  @Autowired
  private ScheduleService scheduleService;

  /**
   * test updateSchedule method.
   */
  @Test
  @Transactional
  public void updateScheduleNormalCaseTest() {
    Passenger passenger = new Passenger();
    passenger.setUsername("scheduleUser");
    passenger.setNumOfPeople(1);
    passengerRepository.save(passenger);

    Schedule schedule = new Schedule();
    schedule.setPickupTime(LocalDateTime.now());
    schedule.setArrivalTime(LocalDateTime.now().plusHours(2));
    schedule.setPickupAddress("Test Pickup Address");
    schedule.setDestinationAddress("Test Destination Address");
    passenger.setSchedule(schedule);
    schedule.setPassenger(passenger);
    scheduleRepository.save(schedule);

    Schedule newSchedule = new Schedule();
    newSchedule.setPickupTime(LocalDateTime.now().plusDays(1));
    newSchedule.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
    newSchedule.setPickupAddress("New Test Pickup Address");
    newSchedule.setDestinationAddress("New Test Destination Address");

    Schedule updatedSchedule = scheduleService.updateSchedule(newSchedule, passenger.getId());

    assertNotNull(updatedSchedule);
    assertEquals(newSchedule.getPickupTime(), updatedSchedule.getPickupTime());
    assertEquals(newSchedule.getArrivalTime(), updatedSchedule.getArrivalTime());
    assertEquals(newSchedule.getPickupAddress(), updatedSchedule.getPickupAddress());
    assertEquals(newSchedule.getDestinationAddress(), updatedSchedule.getDestinationAddress());
  }

  /**
   * test updateSchedule method with nonexistent passenger.
   */
  @Test
  @Transactional
  public void updateScheduleNonexistentPassengerTest() {
    Schedule schedule = new Schedule();
    schedule.setPickupTime(LocalDateTime.now());
    schedule.setArrivalTime(LocalDateTime.now().plusHours(2));
    schedule.setPickupAddress("Test Pickup Address");
    schedule.setDestinationAddress("Test Destination Address");

    assertThrows(IllegalArgumentException.class, () -> {
      scheduleService.updateSchedule(schedule, 1000L);
    });
  }
}
