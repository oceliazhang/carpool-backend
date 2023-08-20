package com.xinjia.carpool.repository;

import com.xinjia.carpool.model.Passenger;

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
class PassengerRepositoryTest {

  @Autowired
  private PassengerRepository passengerRepository;

  @Autowired
  private TestEntityManager entityManager;

  /**
   * Sets up test data for each test method.
   */
  @BeforeEach
  void setUp() {

    Passenger passenger1 = new Passenger();
    passenger1.setUsername("passengerA");

    Passenger passenger2 = new Passenger();
    passenger2.setUsername("passengerB");

    Passenger passenger3 = new Passenger();
    passenger3.setUsername("passengerC");

    entityManager.persist(passenger1);
    entityManager.persist(passenger2);
    entityManager.persist(passenger3);
    entityManager.flush();
  }

  /**
   * Tests the findByUsername method. Verifies that a passenger with
   * the given username is returned.
   */
  @Test
  void testFindByUsername() {

    String username1 = "passengerA";
    Passenger passenger1 = passengerRepository.findByUsername(username1);
    assertNotNull(passenger1, "A passenger with username passengerA should exist");
    assertEquals(username1, passenger1.getUsername(),
        "The passenger's username should match the queried username");

    String username2 = "passengerB";
    Passenger passenger2 = passengerRepository.findByUsername(username2);
    assertNotNull(passenger2, "A passenger with username passengerB should exist");
    assertEquals(username2, passenger2.getUsername(),
        "The passenger's username should match the queried username");

    String username3 = "passengerC";
    Passenger passenger3 = passengerRepository.findByUsername(username3);
    assertNotNull(passenger3, "A passenger with username passengerC should exist");
    assertEquals(username3, passenger3.getUsername(),
        "The passenger's username should match the queried username");

    // Test a nonexistent username and verifies that null is returned.
    Passenger passenger4 = passengerRepository.findByUsername("nonexistent");
    Passenger passenger5 = passengerRepository.findByUsername("nobody");
    assertNull(passenger4, "No passenger with the specified username should exist");
    assertNull(passenger5, "No passenger with the specified username should exist");

  }

}
