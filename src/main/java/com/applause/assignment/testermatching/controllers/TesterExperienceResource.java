package com.applause.assignment.testermatching.controllers;

import com.applause.assignment.testermatching.services.TesterExperienceService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller class for TesterExperienceResource
 */
@RestController
public class TesterExperienceResource {

  private static final Logger logger = LoggerFactory.getLogger(TesterExperienceResource.class);

  @Autowired
  private TesterExperienceService service;

  /**
   * Get /applause/testers/experiences - Get sorted tester experiences based on countries
   * and devices
   *
   * @param countries List of countries as one of query criteria
   * @param devices List of devices as one of query criteria
   * @return Response entity with success or failure along with Json response body
   */
  @GetMapping(value = "applause/testers/experiences")
  public ResponseEntity<Object> getTesterExperiences(
          @RequestParam(value = "country", required = false) List<String> countries,
          @RequestParam(value = "device", required = false) List<String> devices) {
    if (countries == null || countries.isEmpty()) {
      return ResponseEntity.badRequest()
              .body("Country can't be empty");
    }
    if (devices == null || devices.isEmpty()) {
      return ResponseEntity.badRequest()
              .body("Devices can't be empty");
    }
    logger.debug("getTesterExperiences: Get tester experiences by countries: {} and devices: {}",
            countries, devices);
    return new ResponseEntity<>(service.getTestersBySortedExperiences(countries, devices),
            HttpStatus.OK);
  }
}