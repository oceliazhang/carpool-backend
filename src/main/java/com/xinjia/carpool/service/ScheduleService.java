package com.xinjia.carpool.service;

import com.xinjia.carpool.model.Passenger;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.repository.PassengerRepository;
import com.xinjia.carpool.repository.ScheduleRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ScheduleService class contains all the business logic for the Schedule class.
 */
@Service
public class ScheduleService {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private PassengerRepository passengerRepository;

  /**
   * Updates an existing schedule for a given passenger.
   *
   * @param schedule the new schedule information to be updated
   * @param passengerId the ID of the passenger whose schedule is being updated
   * @return the updated schedule object
   * @throws IllegalArgumentException if the passenger does not exist
   */
  public Schedule updateSchedule(Schedule schedule, Long passengerId){
    Optional<Passenger> passenger = passengerRepository.findById(passengerId);
    if(!passenger.isPresent()){
      throw new IllegalArgumentException("Passenger does not exist");
    }

    Schedule oldSchedule = passenger.get().getSchedule();
    BeanUtils.copyProperties(schedule, oldSchedule, "id", "passenger");
    return scheduleRepository.save(oldSchedule);
  }
}
