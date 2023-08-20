package com.xinjia.carpool.repository;

import com.xinjia.carpool.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CarRepository class is used to access the data from the database.
 * It extends JpaRepository interface.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
