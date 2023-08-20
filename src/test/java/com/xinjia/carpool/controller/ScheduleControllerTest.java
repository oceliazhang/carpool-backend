package com.xinjia.carpool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinjia.carpool.model.Schedule;
import com.xinjia.carpool.service.ScheduleService;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is a test class for ScheduleController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ScheduleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ScheduleService scheduleService;

  @Autowired
  private ObjectMapper objectMapper;


  /**
   * test the updateSchedule method in ScheduleController.
   * @throws Exception
   */
  @Test
  public void testUpdateSchedule_success() throws Exception {

    Schedule schedule = new Schedule();

    mockMvc.perform(put("/schedule/update/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(schedule)))
        .andExpect(status().isOk());
  }

}
