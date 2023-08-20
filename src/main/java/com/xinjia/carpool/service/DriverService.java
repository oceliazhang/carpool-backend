package com.xinjia.carpool.service;

import com.xinjia.carpool.model.Car;
import com.xinjia.carpool.model.CarFeature;
import com.xinjia.carpool.model.DTO.FindDriverRequest;
import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.repository.CarRepository;
import com.xinjia.carpool.repository.DriverRepository;
import com.xinjia.carpool.repository.PassengerRepository;
import com.xinjia.carpool.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DriverService class contains all the business logic for the Driver class.
 */
@Service
public class DriverService {

  @Autowired
  private DriverRepository driverRepository;

  @Autowired
  private PassengerRepository passengerRepository;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private ScheduleRepository scheduleRepository;

  /**
   * Retrieves a Driver object by its ID.
   *
   * @param driverId the ID of the Driver object to retrieve
   * @return the Driver object with the specified ID, or null if it does not exist
   */
  public Driver getDriverById(long driverId) {
    return driverRepository.findById(driverId).orElse(null);
  }

  /**
   * Finds all drivers that match the given FindDriverRequest object.
   *
   * @param findDriverRequest the FindDriverRequest object containing the passenger's information
   * @return a list of Driver objects that match the given request
   */
  public List<Driver> findMatchingDrivers(FindDriverRequest findDriverRequest) {
    LocalDateTime passengerArrivalTime = findDriverRequest.getArrivalTime();
    LocalDateTime startTime = passengerArrivalTime.minusMinutes(15);
    LocalDateTime endTime = passengerArrivalTime.plusMinutes(15);

    List<Driver> drivers = driverRepository.findAllByDestinationAndArrivalTime(
        findDriverRequest.getDestinationAddress(), startTime, endTime);

    List<Driver> matchingDrivers = new ArrayList<>();
    for (Driver driver : drivers) {
      if (driver.getCar().getNumSeats() >= findDriverRequest.getNumOfPeople()) {
        matchingDrivers.add(driver);
      }
    }

    for (Driver driver : matchingDrivers) {
      int weight = 0;

      // Check if the driver's car has all the required features
      List<CarFeature> requiredFeatures = findDriverRequest.getCarPreferences();
      List<CarFeature> driverFeatures = driver.getCar().getCarFeatures();
      if (new HashSet<>(driverFeatures).containsAll(requiredFeatures)) {
        weight += requiredFeatures.size();
      } else {
        int numMatchingFeatures = 0;
        for (CarFeature feature : requiredFeatures) {
          if (driverFeatures.contains(feature)) {
            numMatchingFeatures++;
          }
        }
        weight += numMatchingFeatures;
      }
      driver.setMatchWeight(weight);
    }

    // Sort the drivers by match weight
    matchingDrivers.sort(Collections.reverseOrder());
    return matchingDrivers;

  }

  /**
   * Creates a new Driver object.
   *
   * @param driver the Driver object to create
   * @return the created Driver object
   * @throws IllegalArgumentException if a Driver object with the same username already exists
   */
  public Driver createDriver(Driver driver) {

    // check if the username already exists
    if (driverRepository.findByUsername(driver.getUsername())!= null ||
        passengerRepository.findByUsername(driver.getUsername()) != null){
      throw new IllegalArgumentException("Username already exists");
    }

    Car car = new Car();
    BeanUtils.copyProperties(driver.getCar(), car);
    driver.setCar(car);
    carRepository.save(car);

    return driverRepository.save(driver);
  }

  /**
   * Cancels the current ride for the specified driver.
   *
   * @param username the username of the driver
   * @return the updated Driver object
   * @throws IllegalArgumentException if a Driver object with the specified username does not exist
   */
  public Driver cancelRide(String username) {
    Driver driver = driverRepository.findByUsername(username);
    if (driver == null) {
      throw new IllegalArgumentException("Username does not exist");
    }
    driver.setRide(null);
    if (driver.getItinerary() != null) {
      for (Schedule schedule : driver.getItinerary()) {
        schedule.setDriver(null);
        scheduleRepository.save(schedule);
      }
      driver.setItinerary(null);
    }
    return driverRepository.save(driver);
  }

}
