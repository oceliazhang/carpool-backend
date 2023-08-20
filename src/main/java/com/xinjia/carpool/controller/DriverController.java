package com.xinjia.carpool.controller;

import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is the REST API controller for managing drivers.
 */
@RestController
@RequestMapping("/drivers")
public class DriverController {

  @Autowired
  private DriverService driverService;

  /**
   * Creates a new driver.
   *
   * @param driver The driver to create.
   * @return A ResponseEntity containing the saved driver and a status code of 201 (Created).
   *         If an error occurs, a bad request response is returned.
   */
  @PostMapping("/create")
  public ResponseEntity<Object> createDriver(@RequestBody Driver driver) {
    try {
      Driver savedDriver = driverService.createDriver(driver);
      return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Cancels a ride for the driver with the given username.
   *
   * @param username The username of the driver.
   * @return A ResponseEntity containing the updated driver and a status code of 201 (Created).
   *         If an error occurs, a bad request response is returned.
   */
  @PutMapping("/cancel-ride")
  public ResponseEntity<Object> cancelRide(@RequestBody String username) {
    try {
      Driver savedDriver = driverService.cancelRide(username);
      return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Gets the driver with the given ID.
   *
   * @param id The ID of the driver to get.
   * @return The driver with the given ID.
   */
  @GetMapping("/{id}")
  public Driver getDriver(@PathVariable Long id) {
    return driverService.getDriverById(id);
  }
}

