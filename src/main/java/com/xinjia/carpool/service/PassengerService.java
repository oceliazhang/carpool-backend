package com.xinjia.carpool.service;

import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Passenger;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.repository.DriverRepository;
import com.xinjia.carpool.repository.PassengerRepository;
import com.xinjia.carpool.repository.ScheduleRepository;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 * PassengerService class contains all the business logic for the Passenger class.
 */
@Service
public class PassengerService {

  @Autowired
  private PassengerRepository passengerRepository;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private DriverRepository driverRepository;

  /**
   * Creates a new Passenger object and saves it to the database.
   * Returns the created Passenger object, or throws an IllegalArgumentException if the username
   * already exists.
   *
   * @param passenger the Passenger object to create
   * @return the created Passenger object
   * @throws IllegalArgumentException if the username already exists
   */
  @Transactional
  public Passenger createPassenger(Passenger passenger) {

    // check if the username already exists
    if (passengerRepository.findByUsername(passenger.getUsername()) != null ||
        driverRepository.findByUsername(passenger.getUsername())!= null) {
      throw new IllegalArgumentException("Username already exists");
    }

    Schedule schedule = new Schedule();
    passenger.setSchedule(schedule);
    passengerRepository.save(passenger);

    schedule.setPassenger(passenger);
    scheduleRepository.save(schedule);

    return passenger;
  }

  /**
   * Retrieves a Passenger object by its ID.
   *
   * @param passengerId the ID of the Passenger object to retrieve
   * @return the Passenger object with the specified ID, or null if it does not exist
   */
  public Passenger getPassengerById(long passengerId) {
    return passengerRepository.findById(passengerId).orElse(null);
  }

  /**
   * Chooses a driver for the passenger with the specified username and assigns them to a schedule.
   * Returns the chosen driver,
   * or throws a NotFoundException if the passenger or driver is not found,
   * or an IllegalArgumentException if the driver's car doesn't have enough seats for the passenger.
   *
   * @param username the username of the passenger choosing the driver
   * @param driverId the ID of the driver to choose
   * @return the chosen driver
   * @throws NotFoundException if the passenger or driver is not found
   * @throws IllegalArgumentException
   * if the driver's car doesn't have enough seats for the passenger
   */
  public Driver chooseDriver(String username, Long driverId) throws NotFoundException {
    Passenger passenger = passengerRepository.findByUsername(username);
    if (passenger == null) {
      throw new NotFoundException();
    }
    Schedule schedule = passenger.getSchedule();
    if (schedule == null) {
      throw new NotFoundException();
    }
    Driver driver = driverRepository.findById(driverId).orElse(null);
    if (driver == null) {
      throw new NotFoundException();
    }
    if (driver.getCar().getNumSeats() < passenger.getNumOfPeople()) {
      throw new IllegalArgumentException("Not enough seats");
    }
    schedule.setDriver(driver);
    driver.addSchedule(schedule);
    Integer numSeats = driver.getCar().getNumSeats();
    numSeats -= passenger.getNumOfPeople();
    driver.getCar().setNumSeats(numSeats);
    driverRepository.save(driver);
    scheduleRepository.save(schedule);
    passengerRepository.save(passenger);

    Driver myDriver = new Driver();
    BeanUtils.copyProperties(driver, myDriver,
        "password", "itinerary", "matchWeight", "ride");

    return myDriver;
  }

  /**
   * Cancels the ride of the passenger with the specified ID
   * by removing them from the driver's itienrary.
   * Returns the canceled passenger,
   * or throws a NotFoundException if the passenger is not found or has no schedule.
   *
   * @param passengerId the ID of the passenger to cancel the ride for
   * @return the canceled passenger
   * @throws NotFoundException if the passenger is not found or has no schedule
   */
  public Passenger cancelRide(Long passengerId) throws NotFoundException {
    Passenger passenger = passengerRepository.findById(passengerId).orElse(null);
    if (passenger == null) {
      throw new NotFoundException();
    }
    if (passenger.getSchedule() == null) {
      return passenger;
    }
    // cancel the ride by removing the passenger from the driver's schedule
    Driver driver = passenger.getSchedule().getDriver();
    if (driver != null) {
      driver.removeSchedule(passenger.getSchedule());
      Integer numSeats = driver.getCar().getNumSeats();
      numSeats += passenger.getSchedule().getPassenger().getNumOfPeople();
      driver.getCar().setNumSeats(numSeats);
      driverRepository.save(driver);
    }

    Schedule schedule = passenger.getSchedule();
    Schedule nullSchedule = new Schedule();
    BeanUtils.copyProperties(nullSchedule, schedule, "id", "passenger");
    scheduleRepository.save(schedule);

    return passenger;
  }
}