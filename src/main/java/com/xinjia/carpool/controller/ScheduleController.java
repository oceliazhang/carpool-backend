package com.xinjia.carpool.controller;

import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The ScheduleController class handles requests related to schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

  @Autowired
  private ScheduleService scheduleService;

  /**
   * Updates an existing schedule.
   *
   * @param schedule the schedule to update
   * @param id the ID of the schedule to update
   * @return a ResponseEntity with the updated Schedule and an HTTP status of OK, or a bad request
   *         response if the update failed
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule,
      @PathVariable Long id){
    try {
      Schedule updatedSchedule = scheduleService.updateSchedule(schedule, id);
      return ResponseEntity.ok(updatedSchedule);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

}

