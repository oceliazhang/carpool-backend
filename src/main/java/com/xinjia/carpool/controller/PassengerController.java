package com.xinjia.carpool.controller;

import com.xinjia.carpool.model.DTO.FindDriverRequest;
import com.xinjia.carpool.model.Driver;
import com.xinjia.carpool.model.Passenger;
import com.xinjia.carpool.service.DriverService;
import com.xinjia.carpool.service.PassengerService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The PassengerController class is a RESTful API controller that handles HTTP requests related to passenger operations.
 */
@RestController
@RequestMapping("/passengers")
public class PassengerController {

  @Autowired
  private DriverService driverService;

  @Autowired
  private PassengerService passengerService;

  /**
   * Creates a new passenger record in the database.
   * @param passenger The Passenger object containing the passenger's information.
   * @return A ResponseEntity containing the newly created Passenger object and an HTTP status code of 201 CREATED.
   * If an error occurs during the creation process, a bad request response is returned with an error message.
   */
  @PostMapping("/create")
  public ResponseEntity<Object> createPassenger(@RequestBody Passenger passenger) {
    try {
      Passenger savedPassenger = passengerService.createPassenger(passenger);
      return new ResponseEntity<>(savedPassenger, HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Finds a list of drivers that match the specified search criteria.
   * @param findDriverRequest A FindDriverRequest object containing the search criteria.
   * @return A List of Driver objects that match the search criteria.
   */
  @GetMapping("/find-matching-drivers")
  public List<Driver> findMatchingDrivers(@RequestBody FindDriverRequest findDriverRequest) {
    return driverService.findMatchingDrivers(findDriverRequest);
  }

  /**
   * Assigns a driver to a passenger's ride request.
   * @param driverId The ID of the driver to assign.
   * @param username The username of the passenger making the ride request.
   * @return The Driver object that was assigned to the ride request.
   * If an error occurs during the assignment process, null is returned.
   */
  @PutMapping("/choose-driver/{driverId}")
  public Driver chooseDriver(@PathVariable long driverId, @RequestBody String username) {
    try {
      return passengerService.chooseDriver(username, driverId);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Retrieves a passenger's record from the database.
   * @param passengerId The ID of the passenger to retrieve.
   * @return The Passenger object with the specified ID.
   * @throws NotFoundException If the specified passenger record cannot be found in the database.
   */
  @GetMapping("/get-passenger/{passengerId}")
  public Passenger getPassenger(@PathVariable long passengerId) throws NotFoundException {
    return passengerService.getPassengerById(passengerId);
  }

  /**
   * Cancels a passenger's ride request and removes the associated driver assignment.
   * @param passengerId The ID of the passenger making the ride request.
   * @return The updated Passenger object with the cancelled ride request.
   * @throws NotFoundException If the specified passenger record cannot be found in the database.
   */
  @PutMapping("/cancel-ride/{passengerId}")
  public Passenger cancelRide(@PathVariable long passengerId) throws NotFoundException {

    return passengerService.cancelRide(passengerId);
  }
}
