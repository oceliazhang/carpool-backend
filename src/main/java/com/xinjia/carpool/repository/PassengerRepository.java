package com.xinjia.carpool.repository;

import com.xinjia.carpool.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * PassengerRepository class is used to access the data from the database.
 * It extends JpaRepository interface, and implements CrudRepository interface.
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>,
    CrudRepository<Passenger, Long> {

  /**
   * This method is used to find a passenger by username.
   * @param username username
   * @return a passenger
   */
  Passenger findByUsername(String username);
}
