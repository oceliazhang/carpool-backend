package com.xinjia.carpool.repository;

import com.xinjia.carpool.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ScheduleRepository class is used to access the data from the database.
 * It extends JpaRepository interface, and implements CrudRepository interface.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>,
    CrudRepository<Schedule, Long> {

}
