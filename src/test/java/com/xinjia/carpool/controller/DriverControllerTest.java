package com.xinjia.carpool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinjia.carpool.model.Car;
import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.service.DriverService;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for the DriverController class.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DriverControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DriverService driverService;

  private ObjectMapper objectMapper;

  private Driver testDriver;

  /**
   * Setup method that runs before each test.
   */
  @BeforeEach
  public void setup() {
    objectMapper = new ObjectMapper();

    testDriver = new Driver();
    testDriver.setUsername("testDriver");
    testDriver.setName("Ocelia");

    // Add a car to the test driver
    Car car = new Car();
    car.setMake("Toyota");
    car.setModel("Camry");
    car.setNumSeats(4);
    testDriver.setCar(car);

    // Add a sample itinerary for the test driver
    Schedule schedule1 = new Schedule();
    schedule1.setDriver(testDriver);

    Schedule schedule2 = new Schedule();
    schedule2.setDriver(testDriver);

    List<Schedule> itinerary = Arrays.asList(schedule1, schedule2);
    testDriver.setItinerary(itinerary);
  }

  /**
   * Tests for the createDriver method.
   */
  @Test
  public void testCreateDriver_validDriver() throws Exception {
    String driverJson = objectMapper.writeValueAsString(testDriver);

    mockMvc.perform(post("/drivers/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(driverJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value(testDriver.getUsername()));
  }

  /**
   * Tests for the createDriver method, with a duplicate username.
   */
  @Test
  public void testCreateDriver_duplicateUsername() throws Exception {
    Driver savedDriver = driverService.createDriver(testDriver);
    String driverJson = objectMapper.writeValueAsString(testDriver);

    mockMvc.perform(post("/drivers/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(driverJson))
        .andExpect(status().isBadRequest());
  }

  /**
   * Tests for the createDriver method, with a null username.
   */
  @Test
  public void testCancelRide_validUsername() throws Exception {
    Driver savedDriver = driverService.createDriver(testDriver);

    // Wrap the username in quotes to create a valid JSON string
    String usernameJson = "testDriver";

    mockMvc.perform(put("/drivers/cancel-ride")
            .contentType(MediaType.APPLICATION_JSON)
            .content(usernameJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value(savedDriver.getUsername()))
        .andExpect(jsonPath("$.ride").doesNotExist())
        .andExpect(jsonPath("$.itinerary").doesNotExist());
  }


  /**
   * Tests for the cancelRide method, with a null username.
   */
  @Test
  public void testCancelRide_invalidUsername() throws Exception {
    String invalidUsername = "nonexistent_user";

    mockMvc.perform(put("/drivers/cancel-ride")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidUsername))
        .andExpect(status().isBadRequest());
  }

  /**
   * Tests for the getDriver method.
   */
  @Test
  public void testGetDriver_validId() throws Exception {
    Driver savedDriver = driverService.createDriver(testDriver);

    mockMvc.perform(get("/drivers/" + savedDriver.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(savedDriver.getId()))
        .andExpect(jsonPath("$.username").value(savedDriver.getUsername()));
  }

  /**
   * Tests for the getDriver method, with an invalid id.
   */
  @Test
  public void testGetDriver_invalidId() throws Exception {
    Long invalidId = -1L;

    mockMvc.perform(get("/drivers/" + invalidId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());
  }
}
