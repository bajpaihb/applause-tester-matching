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

@RestController
public class TesterExperienceResource {

  private static final Logger logger = LoggerFactory.getLogger(TesterExperienceResource.class);

  @Autowired
  private TesterExperienceService service;

  @GetMapping(value = "applause/testers/experiences")
  public ResponseEntity<Object> getTesterExperiences(
    @RequestParam(value = "country", required = false) List<String> countries,
    @RequestParam(value = "device", required = false) List<String> devices) {
    if (countries == null || devices == null) {
      return ResponseEntity.badRequest()
        .body("Country or Device can't be empty");
    }
    logger.debug("getTesterExperiences: Get tester experiences by countries: {} and devices: {}",
      countries, devices);
    return new ResponseEntity<>(service.getTestersBySortedExperiences(countries, devices),
      HttpStatus.OK);
  }
}
