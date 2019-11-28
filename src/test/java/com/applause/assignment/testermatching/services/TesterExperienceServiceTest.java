package com.applause.assignment.testermatching.services;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.applause.assignment.testermatching.dto.TesterExperience;
import com.applause.assignment.testermatching.models.Testers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TesterExperienceServiceTest {

  @Mock
  private ReputationLookUpService lookUpService;

  @InjectMocks
  private TesterExperienceService service;

  private List<String> countries;
  private List<String> devices;
  private Testers tester;
  private TesterExperience testerExperience;
  private List<TesterExperience> experiences;
  private String country = "JP";
  private String deviceName = "Iphone 4";
  private int testerId = 4;

  @BeforeEach
  public void setup() {
    tester = new Testers(testerId, "firstName", "lastName", country);
    testerExperience = new TesterExperience(tester.getFirstName() + " " + tester.getLastName(), 1);
    experiences = new ArrayList<>();
    countries = new ArrayList<String>();
    devices = new ArrayList<String>();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getTestersBySortedExperiencesByCountriesAndDevicesTest() {
    experiences.add(testerExperience);
    countries.add(country);
    devices.add(deviceName);
    Map<Testers, Integer> reputationMap = new HashMap<>();
    reputationMap.put(tester, 1);
    when(lookUpService.getReputationByCountriesAndDevices(countries, devices))
            .thenReturn(reputationMap);
    List<TesterExperience> response = service.getTestersBySortedExperiences(countries, devices);
    assertEquals("Name doesnt match", experiences.get(0).getTesterName(),
            response.get(0).getTesterName());
    assertEquals("Reputation doesnt match", experiences.get(0).getTesterReputation(),
            response.get(0).getTesterReputation());
    verify(lookUpService, times(1)).getReputationByCountriesAndDevices(anyList(), anyList());
  }

  @Test
  public void getTestersBySortedExperiencesByDevicesTest() {
    experiences.add(testerExperience);
    countries.add("ALL");
    devices.add(deviceName);
    Map<Testers, Integer> reputationMap = new HashMap<>();
    reputationMap.put(tester, 1);
    when(lookUpService.getReputationByDevices(devices)).thenReturn(reputationMap);
    List<TesterExperience> response = service.getTestersBySortedExperiences(countries, devices);
    assertEquals("Name doesnt match", experiences.get(0).getTesterName(),
            response.get(0).getTesterName());
    assertEquals("Reputation doesnt match", experiences.get(0).getTesterReputation(),
            response.get(0).getTesterReputation());
    verify(lookUpService, times(1)).getReputationByDevices(anyList());
  }

  @Test
  public void getTestersBySortedExperiencesByCountriesTest() {
    experiences.add(testerExperience);
    countries.add(country);
    devices.add("ALL");
    Map<Testers, Integer> reputationMap = new HashMap<>();
    reputationMap.put(tester, 1);
    when(lookUpService.getReputationByCountries(countries)).thenReturn(reputationMap);
    List<TesterExperience> response = service.getTestersBySortedExperiences(countries, devices);
    assertEquals("Name doesnt match", experiences.get(0).getTesterName(),
            response.get(0).getTesterName());
    assertEquals("Reputation doesnt match", experiences.get(0).getTesterReputation(),
            response.get(0).getTesterReputation());
    verify(lookUpService, times(1)).getReputationByCountries(anyList());
  }

  @Test
  public void getTestersBySortedExperiencesByAllTest() {
    experiences.add(testerExperience);
    countries.add("ALL");
    devices.add("ALL");
    Map<Testers, Integer> reputationMap = new HashMap<>();
    reputationMap.put(tester, 1);
    when(lookUpService.getReputationForAll()).thenReturn(reputationMap);
    List<TesterExperience> response = service.getTestersBySortedExperiences(countries, devices);
    assertEquals("Name doesnt match", experiences.get(0).getTesterName(),
            response.get(0).getTesterName());
    assertEquals("Reputation doesnt match", experiences.get(0).getTesterReputation(),
            response.get(0).getTesterReputation());
    verify(lookUpService, times(1)).getReputationForAll();
  }
}