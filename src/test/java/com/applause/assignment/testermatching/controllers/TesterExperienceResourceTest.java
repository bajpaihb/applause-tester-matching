package com.applause.assignment.testermatching.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.applause.assignment.testermatching.dto.TesterExperience;
import com.applause.assignment.testermatching.services.TesterExperienceService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TesterExperienceResourceTest {

  @Mock
  private TesterExperienceService service;

  @InjectMocks
  private TesterExperienceResource resource;

  private String testerName = "Fake Tester";
  private int deviceId = 222;
  private ArrayList<TesterExperience> experiences;
  private List<String> listOfCountries;
  private List<String> listOfDevices;

  @BeforeEach
  public void setupTest() {
    listOfCountries = new ArrayList<String>(Arrays.asList("US", "GB"));
    listOfDevices = new ArrayList<String>(Arrays.asList("Pixel", "Iphone"));
    TesterExperience testerExperience = new TesterExperience(testerName, deviceId);
    MockitoAnnotations.initMocks(this);
    experiences = new ArrayList<TesterExperience>(Arrays.asList(testerExperience));
    when(service.getTestersBySortedExperiences(listOfCountries, listOfDevices))
      .thenReturn(experiences);
  }

  @Test
  public void getTesterExperiencesTest() {
    ResponseEntity response = resource.getTesterExperiences(listOfCountries, listOfDevices);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(experiences, response.getBody());

    verify(service).getTestersBySortedExperiences(listOfCountries, listOfDevices);
  }
}
