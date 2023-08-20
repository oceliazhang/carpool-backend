package com.xinjia.carpool.service;

import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Passenger;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.repository.DriverRepository;
import com.xinjia.carpool.repository.PassengerRepository;
import com.xinjia.carpool.repository.ScheduleRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PassengerService class.
 */
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PassengerServiceTest {

  @Autowired
  private PassengerService passengerService;

  @Autowired
  private PassengerRepository passengerRepository;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private DriverRepository driverRepository;

  /**
   * Test for createPassenger method.
   */
  @Test
  @Transactional
  public void createPassengerTest() {
    Passenger newPassenger = new Passenger();
    newPassenger.setUsername("newUser");
    newPassenger.setNumOfPeople(1);

    // Test normal case
    Passenger createdPassenger = passengerService.createPassenger(newPassenger);
    assertNotNull(createdPassenger);
    assertEquals(newPassenger.getUsername(), createdPassenger.getUsername());

    // Test edge case: duplicate username
    Passenger duplicatePassenger = new Passenger();
    duplicatePassenger.setUsername("newUser");
    duplicatePassenger.setNumOfPeople(1);

    assertThrows(IllegalArgumentException.class, () -> {
      passengerService.createPassenger(duplicatePassenger);
    });
  }

  /**
   * Test for getPassengerById method.
   */
  @Test
  public void getPassengerByIdTest() {
    // Test normal case
    Passenger existingPassenger = passengerService.getPassengerById(1L);
    assertNotNull(existingPassenger);
    assertEquals(1L, existingPassenger.getId());

    // Test edge case: nonexistent ID
    Passenger nonexistentPassenger = passengerService.getPassengerById(1000L);
    assertNull(nonexistentPassenger);
  }

  /**
   * Test for chooseDriver method.
   * @throws NotFoundException when driver is not found / not enough seats
   */
  @Test
  @Transactional
  public void chooseDriverTest() throws NotFoundException {
    // Test normal case
    Driver chosenDriver = passengerService.chooseDriver("huahua", 2L);
    assertNotNull(chosenDriver);
    assertEquals(2L, chosenDriver.getId());

    // Test edge case: nonexistent passenger
    assertThrows(NotFoundException.class, () -> {
      passengerService.chooseDriver("nonexistentUser", 2L);
    });

    // Test edge case: not enough seats
    assertThrows(IllegalArgumentException.class, () -> {
      passengerService.chooseDriver("huahua", 3L);
    });
  }

  /**
   * Test for cancelRide method.
   * @throws NotFoundException when passenger is not found
   */
  @Test
  @Transactional
  public void cancelRideTest() throws NotFoundException {
    // Test normal case
    Passenger canceledPassenger = passengerService.cancelRide(1L);
    assertNotNull(canceledPassenger);
    assertEquals(1L, canceledPassenger.getId());
    assertNull(canceledPassenger.getSchedule().getDriver());

    // Test edge case: nonexistent passenger
    assertThrows(NotFoundException.class, () -> {
      passengerService.cancelRide(1000L);
    });

    // Test edge case: passenger without a schedule
    Passenger passengerWithoutSchedule = new Passenger();
    passengerWithoutSchedule.setId(123L);
    passengerWithoutSchedule.setUsername("noScheduleUser");
    passengerWithoutSchedule.setNumOfPeople(1);
    passengerRepository.save(passengerWithoutSchedule);

    assertThrows(NotFoundException.class, () -> {
      passengerService.cancelRide(123L);
    });
  }

  /**
   * Test for createPassenger method, with a schedule but no driver
   */
  private Passenger createPassengerWithScheduleNoDriver() {
    Passenger passenger = new Passenger();
    passenger.setUsername("scheduleNoDriver");
    passenger.setNumOfPeople(1);
    Schedule schedule = new Schedule();
    passenger.setSchedule(schedule);
    passengerRepository.save(passenger);
    schedule.setPassenger(passenger);
    scheduleRepository.save(schedule);
    return passenger;
  }

  /**
   * Test for updateSchedule method, when schedule is null
   */
  @Test
  @Transactional
  public void updateScheduleWhenScheduleIsNullTest() {
    Passenger passenger = new Passenger();
    passenger.setUsername("noSchedule");
    passenger.setNumOfPeople(1);
    passengerRepository.save(passenger);

    assertThrows(NotFoundException.class, () -> {
      passengerService.chooseDriver(passenger.getUsername(), 2L);
    });
  }

  /**
   * Test for updateSchedule method, when driver is null
   */
  @Test
  @Transactional
  public void updateScheduleWhenDriverIsNullTest() {
    Passenger passenger = createPassengerWithScheduleNoDriver();

    assertThrows(NotFoundException.class, () -> {
      passengerService.chooseDriver(passenger.getUsername(), 1000L);
    });
  }

  /**
   * Test for cancelRide method, when schedule is null
   * @throws NotFoundException when schedule is null
   */
  @Test
  @Transactional
  public void cancelRideWhenNullScheduleTest() throws NotFoundException {
    Passenger passenger = new Passenger();
    passenger.setUsername("noSchedule");
    passenger.setNumOfPeople(1);
    passengerRepository.save(passenger);

    Passenger returnedPassenger = passengerService.cancelRide(passenger.getId());

    assertNotNull(returnedPassenger);
    assertEquals(passenger.getId(), returnedPassenger.getId());
    assertNull(returnedPassenger.getSchedule());
  }


}