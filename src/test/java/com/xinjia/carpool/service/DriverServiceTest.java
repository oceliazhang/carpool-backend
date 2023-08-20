package com.xinjia.carpool.service;

import static org.junit.jupiter.api.Assertions.*;

import com.xinjia.carpool.model.CarFeature;
import com.xinjia.carpool.model.DTO.FindDriverRequest;
import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Ride;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.repository.CarRepository;
import com.xinjia.carpool.repository.DriverRepository;
import com.xinjia.carpool.repository.PassengerRepository;
import com.xinjia.carpool.repository.ScheduleRepository;
import com.xinjia.carpool.model.Car;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for DriverService class.
 */
@SpringBootTest
class DriverServiceTest {

  @Autowired
  private DriverService driverService;

  @Autowired
  private DriverRepository driverRepository;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private PassengerRepository passengerRepository;

  /**
   * test getDriverById method.
   */
  @Test
  void testGetDriverById() {

    assertEquals(2, driverService.getDriverById(2).getId());
    assertEquals("jiajia", driverService.getDriverById(2).getUsername());

    assertEquals(3, driverService.getDriverById(3).getId());
    assertEquals("irene", driverService.getDriverById(3).getUsername());

    assertEquals(5, driverService.getDriverById(5).getId());
    assertEquals("evan", driverService.getDriverById(5).getUsername());

    assertNull(driverService.getDriverById(1));
    assertNull(driverService.getDriverById(4));
    assertNull(driverService.getDriverById(6));

  }

  /**
   * test getDriverByUsername method.
   */
  @Test
  @Transactional
  void testFindMatchingDrivers() {

    Driver testDriver = new Driver();
    testDriver.setUsername("testDriver");
    Ride testRide = new Ride();
    testRide.setDestinationAddress("123 Main St");
    testRide.setArrivalTime(LocalDateTime.now().plusHours(1));
    testDriver.setRide(testRide);


    Car testCar = new Car();
    testCar.setNumSeats(4);
    testCar.setCarFeatures(Arrays.asList(CarFeature.HAS_AC));
    Car savedCar = carRepository.save(testCar); // Save the car entity first
    testDriver.setCar(savedCar);

    driverRepository.save(testDriver);

    FindDriverRequest request = new FindDriverRequest();
    request.setDestinationAddress("123 Main St");
    request.setArrivalTime(LocalDateTime.now().plusHours(1));
    request.setNumOfPeople(3);
    request.setCarPreferences(Arrays.asList(CarFeature.HAS_AC));

    List<Driver> matchingDrivers = driverService.findMatchingDrivers(request);

    assertNotNull(matchingDrivers);
    assertEquals(1, matchingDrivers.size());
    assertEquals("testDriver", matchingDrivers.get(0).getUsername());
    assertEquals(1, matchingDrivers.get(0).getMatchWeight());

  }

  /**
   * test createDriver method.
   */
  @Test
  void createDriver() {
    // Set three drivers with different usernames
    Driver driver1 = new Driver();
    driver1.setUsername("newDriver1");
    Car car1 = new Car();
    driver1.setCar(car1);

    Driver driver2 = new Driver();
    driver2.setUsername("newDriver2");
    Car car2 = new Car();
    driver2.setCar(car2);

    Driver driver3 = new Driver();
    driver3.setUsername("newDriver3");
    Car car3 = new Car();
    driver3.setCar(car3);

    // create three drivers
    Driver createdDriver1 = driverService.createDriver(driver1);
    Driver createdDriver2 = driverService.createDriver(driver2);
    Driver createdDriver3 = driverService.createDriver(driver3);

    // test if the three drivers are created successfully
    assertNotNull(createdDriver1);
    assertEquals(driver1.getUsername(), createdDriver1.getUsername());
    assertNotNull(createdDriver1.getCar());

    assertNotNull(createdDriver2);
    assertEquals(driver2.getUsername(), createdDriver2.getUsername());
    assertNotNull(createdDriver2.getCar());

    assertNotNull(createdDriver3);
    assertEquals(driver3.getUsername(), createdDriver3.getUsername());
    assertNotNull(createdDriver3.getCar());
  }

  /**
   * test createDriver method with invalid username.
   */
  @Test
  void createDriverWithExistingUsername() {

    // we already have a driver with username "evan" in the database
    assertThrows(IllegalArgumentException.class, () -> {
      Driver newDriver = new Driver();
      newDriver.setUsername("evan");
      driverService.createDriver(newDriver);
    });

    // we already have a driver with username "jiajia" in the database
    assertThrows(IllegalArgumentException.class, () -> {
      Driver newDriver = new Driver();
      newDriver.setUsername("jiajia");
      driverService.createDriver(newDriver);
    });

    // we already have a driver with username "irene" in the database
    assertThrows(IllegalArgumentException.class, () -> {
      Driver newDriver = new Driver();
      newDriver.setUsername("irene");
      driverService.createDriver(newDriver);
    });

  }

  /**
   * test cancelRide method.
   */
  @Test
  @Transactional
  void cancelRide() {

    // test nonexistent user, throw an exception
    String invalidUsername = "nonexistent_user";
    assertThrows(IllegalArgumentException.class, () -> driverService.cancelRide(invalidUsername));

    // test driver with no ride, return the driver
    Driver testDriver = new Driver();
    testDriver.setUsername("testDriver");
    driverRepository.save(testDriver);
    Driver updatedDriver = driverService.cancelRide(testDriver.getUsername());
    assertNotNull(updatedDriver);
    assertNull(updatedDriver.getRide());
    assertNull(updatedDriver.getItinerary());

    // test driver with an itinerary, return the driver and set the itinerary to null
    Schedule testSchedule = new Schedule();
    testSchedule.setDriver(testDriver);
    scheduleRepository.save(testSchedule);
    List<Schedule> itinerary = new ArrayList<>();
    itinerary.add(testSchedule);
    testDriver.setItinerary(itinerary);
    driverRepository.save(testDriver);

    Driver updatedDriver2 = driverService.cancelRide(testDriver.getUsername());
    assertNotNull(updatedDriver2);
    assertNull(updatedDriver2.getRide());
    assertNull(updatedDriver2.getItinerary());
    assertNull(testSchedule.getDriver());
  }
}
