package com.xinjia.carpool.repository;

import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Ride;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


/**
 * Tests for DriverRepository class.
 */
@DataJpaTest
@Transactional
class DriverRepositoryTest {

  @Autowired
  private DriverRepository driverRepository;

  @Autowired
  private TestEntityManager entityManager;

  /**
   * Sets up test data for each test method.
   */
  @BeforeEach
  void setUp() {
    Ride ride1 = new Ride();
    ride1.setDestinationAddress("600 California St");
    ride1.setArrivalTime(LocalDateTime.of(2023, 4, 27, 11, 0));

    Ride ride2 = new Ride();
    ride2.setDestinationAddress("1010 Louis St");
    ride2.setArrivalTime(LocalDateTime.of(2023, 4, 27, 11, 0));

    Driver driver1 = new Driver();
    driver1.setUsername("driverA");
    driver1.setRide(ride1);

    Driver driver2 = new Driver();
    driver2.setUsername("driverB");
    driver2.setRide(ride1);

    Driver driver3 = new Driver();
    driver3.setUsername("driverC");
    driver3.setRide(ride2);

    entityManager.persist(driver1);
    entityManager.persist(driver2);
    entityManager.persist(driver3);
    entityManager.flush();
  }

  /**
   * Tests the findAllByDestinationAndArrivalTime method.
   * Verifies that the correct drivers are returned based on the given destination and arrival time.
   */
  @Test
  void testFindAllByDestinationAndArrivalTime() {
    String destinationAddress = "600 California St";
    LocalDateTime start = LocalDateTime.of(2023, 4, 27, 10, 45);
    LocalDateTime end = LocalDateTime.of(2023, 4, 27, 11, 15);

    List<Driver> drivers = driverRepository.findAllByDestinationAndArrivalTime
        (destinationAddress, start, end);
    Driver driver1 = entityManager.find(Driver.class, 6L);
    Driver driver2 = entityManager.find(Driver.class, 7L);
    Driver driver3 = entityManager.find(Driver.class, 8L);

    assertEquals(2, drivers.size(),
        "There should be 2 drivers with the specified destination and arrival time");

    assertTrue(drivers.contains(driver1),
        "The first driver should be included in the results");
    assertTrue(drivers.contains(driver2),
        "The second driver should be included in the results");
    assertFalse(drivers.contains(driver3),
        "The third driver should not be included in the results");
  }

  /**
   * Tests the findByUsername method. Verifies that a driver with
   * the given username is returned.
   */
  @Test
  void testFindByUsername() {

    String username1 = "driverA";
    Driver driver1 = driverRepository.findByUsername(username1);
    assertNotNull(driver1, "A driver with username driverA should exist");
    assertEquals(username1, driver1.getUsername(),
        "The driver's username should match the queried username");

    String username2 = "driverB";
    Driver driver2 = driverRepository.findByUsername(username2);
    assertNotNull(driver2, "A driver with username driverB should exist");
    assertEquals(username2, driver2.getUsername(),
        "The driver's username should match the queried username");

    // Test a nonexistent username and verifies that null is returned.
    Driver driver3 = driverRepository.findByUsername("nonexistent");
    Driver driver4 = driverRepository.findByUsername("nobody");
    assertNull(driver3, "No driver with the specified username should exist");
    assertNull(driver4, "No driver with the specified username should exist");

  }

}
