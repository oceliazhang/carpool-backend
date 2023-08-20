package com.xinjia.carpool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinjia.carpool.model.CarFeature;
import com.xinjia.carpool.model.DTO.FindDriverRequest;
import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Passenger;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.service.DriverService;
import com.xinjia.carpool.service.PassengerService;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class contains unit tests for PassengerController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PassengerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private DriverService driverService;

  @Autowired
  private PassengerService passengerService;

  private Passenger testPassenger;
  private FindDriverRequest findDriverRequest;

  /**
   * This method is run before each test case.
   */
  @BeforeEach
  public void setup() {
    testPassenger = new Passenger();
    testPassenger.setUsername("rachel");
    testPassenger.setNumOfPeople(2);
    testPassenger.setName("Rachel");
    testPassenger.setCarPreferences(Arrays.asList(CarFeature.HAS_AC, CarFeature.PET_ALLOWED));
    testPassenger.setSchedule(new Schedule());

    findDriverRequest = new FindDriverRequest();

  }

  /**
   * This method tests the createPassenger method in PassengerController.
   */
  @Test
  public void testCreatePassenger() throws Exception {
    mockMvc.perform(post("/passengers/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testPassenger)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value(testPassenger.getUsername()))
        .andExpect(jsonPath("$.name").value(testPassenger.getName()))
        .andExpect(jsonPath("$.numOfPeople").value(testPassenger.getNumOfPeople()));
  }

  /**
   * This method tests the findMatchingDrivers method in PassengerController.
   */
  @Test
  public void testFindMatchingDrivers() throws Exception {
    List<Driver> matchingDrivers = Arrays.asList(new Driver(), new Driver());

    findDriverRequest.setDestinationAddress("NEU");
    findDriverRequest.setArrivalTime(LocalDateTime.of(2023, 5, 1, 12, 0));
    findDriverRequest.setNumOfPeople(1);
    findDriverRequest.setCarPreferences(Collections.emptyList());

    mockMvc.perform(get("/passengers/find-matching-drivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(findDriverRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].username").value("jiajia"))
        .andExpect(jsonPath("$[1].username").value("irene"));
  }

  /**
   * This method tests the chooseDriver method in PassengerController.
   */
  @Test
  public void testChooseDriver() throws Exception {

    mockMvc.perform(put("/passengers/choose-driver/{driverId}", 2)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString("huahua")))
        .andExpect(status().isOk());
  }

  /**
   * This method tests the getPassenger method in PassengerController.
   */
  @Test
  public void testGetPassenger() throws Exception {

    mockMvc.perform(get("/passengers/get-passenger/{passengerId}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("huahua"));
  }

  /**
   * This method tests the getPassenger method in PassengerController
   */
  @Test
  public void testCancelRide() throws Exception {

    mockMvc.perform(put("/passengers/cancel-ride/{passengerId}", 1)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
