package com.xinjia.carpool.repository;

import com.xinjia.carpool.model.Driver;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DriverRepository class is used to access the data from the database.
 * It extends JpaRepository interface.
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

  /**
   * This method is used to find all drivers by destination address and arrival time.
   *
   * @param destinationAddress destination address
   * @param startTime          start time
   * @param endTime            end time
   * @return a list of drivers
   */
  @Query("SELECT DISTINCT d FROM Driver d "
      + "WHERE COALESCE(d.ride.destinationAddress, '') = COALESCE(:destinationAddress, '') "
      + "AND d.ride.arrivalTime BETWEEN :startTime AND :endTime")
  List<Driver> findAllByDestinationAndArrivalTime
      (@Param("destinationAddress") String destinationAddress,
          @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);


  /**
   * This method is used to find a driver by username.
   * @param username username
   * @return a driver
   */
  Driver findByUsername(String username);

}
