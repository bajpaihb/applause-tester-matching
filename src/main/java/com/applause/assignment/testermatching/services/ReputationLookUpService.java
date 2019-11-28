package com.applause.assignment.testermatching.services;

import com.applause.assignment.testermatching.models.Bugs;
import com.applause.assignment.testermatching.models.Devices;
import com.applause.assignment.testermatching.models.Testers;
import com.applause.assignment.testermatching.repositories.BugsRepository;
import com.applause.assignment.testermatching.repositories.DeviceRepository;
import com.applause.assignment.testermatching.repositories.TesterRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service class for querying repositories to look for tester reputation
 */
@Component
public class ReputationLookUpService {

  private static final Logger logger = LoggerFactory.getLogger(ReputationLookUpService.class);

  @Autowired
  private BugsRepository bugsRepository;

  @Autowired
  private TesterRepository testerRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  /**
   * Get reputation for all the testers
   *
   * @return Map of testerReputation which consist of tester and calculated reputation score
   */
  public Map<Testers, Integer> getReputationForAll() {
    logger.debug("getReputationForAll: Querying for all the testers");
    List<Testers> listOfTesters = testerRepository.findAll();
    return populateReputationByTesters(listOfTesters);
  }

  /**
   * Get reputation for all the testers based on devices name
   *
   * @param devices list of devices based on which testers are to be found
   * @return Map of testerReputation which consist of tester and calculated reputation score
   */
  public Map<Testers, Integer> getReputationByDevices(List<String> devices) {
    logger
            .debug("getReputationByDevices: Querying for all the devices by description: {}", devices);
    List<Devices> listOfDevices = deviceRepository.findAllByDescriptionIn(devices);
    List<Testers> listOfTesters = listOfDevices.stream()
            .flatMap(device -> device.getTesters().stream()).collect(
                    Collectors.toList());
    List<Integer> deviceIds = listOfDevices.stream().map(device -> device.getDeviceId())
            .collect(Collectors.toList());
    logger
            .debug("getReputationByDevices: Querying for all the bugs by deviceIds: {} and testers {}",
                    deviceIds, listOfTesters.toString());
    List<Bugs> listOfBugs = bugsRepository.findByDeviceIdInAndTestersIn(deviceIds, listOfTesters);
    return populateReputationByBugsAndTesters(listOfBugs, listOfTesters);
  }

  /**
   * Get reputation for all the testers based on countries name
   *
   * @param countries list of countries based on which testers are to be found
   * @return Map of testerReputation which consist of tester and calculated reputation score
   */
  public Map<Testers, Integer> getReputationByCountries(List<String> countries) {
    logger
            .debug("getReputationByCountries: Querying for all the testers by countries: {}", countries);
    List<Testers> listOfTesters = testerRepository.findAllByCountryIn(countries);
    return populateReputationByTesters(listOfTesters);
  }

  /**
   * Get reputation for all the testers based on countries and devices name
   *
   * @param countries list of countries based on which testers are to be found
   * @param devices list of devices based on which testers are to be found
   * @return Map of testerReputation which consist of tester and calculated reputation score
   */
  public Map<Testers, Integer> getReputationByCountriesAndDevices(List<String> countries,
                                                                  List<String> devices) {
    logger
            .debug("getReputationByCountriesAndDevices: Querying for all the devices by description: {}",
                    devices);
    List<Devices> listOfDevices = deviceRepository.findAllByDescriptionIn(devices);
    logger.debug(
            "getReputationByCountriesAndDevices: Querying for all the testers by countries: {} and devices: {}",
            countries, listOfDevices);
    List<Testers> listOfTesters = testerRepository
            .findAllByCountryInAndDevicesIn(countries, listOfDevices);
    List<Integer> deviceIds = listOfDevices.stream().map(device -> device.getDeviceId())
            .collect(Collectors.toList());
    logger
            .debug(
                    "getReputationByCountriesAndDevices: Querying for all the bugs by deviceIds: {} and testers {}",
                    deviceIds, listOfTesters.toString());
    List<Bugs> listOfBugs = bugsRepository.findByDeviceIdInAndTestersIn(deviceIds, listOfTesters);
    return populateReputationByBugsAndTesters(listOfBugs, listOfTesters);
  }

  /**
   * calculate tester reputation based on bugs and testers
   *
   * @return Map of testerReputation which consist of tester and calculated reputation score
   */
  private Map<Testers, Integer> populateReputationByBugsAndTesters(List<Bugs> listOfBugs,
                                                                   List<Testers> listOfTesters) {
    Map<Testers, Integer> testerReputationMap = new HashMap<>();
    for (Testers testers : listOfTesters) {
      testerReputationMap.put(testers, 0);
    }
    for (Bugs bugs : listOfBugs) {
      Testers testers = testerRepository.findById(bugs.getTesterId());
      testerReputationMap.put(testers, 1 + testerReputationMap.get(testers));
    }
    return testerReputationMap;
  }

  /**
   * calculate tester reputation based on testers
   *
   * @return Map of testerReputation which consist of tester and calculated reputation score
   */
  private Map<Testers, Integer> populateReputationByTesters(List<Testers> listOfTesters) {
    Map<Testers, Integer> testerReputationMap = new HashMap<>();
    for (Testers testers : listOfTesters) {
      int bugCount = testers.getBugs().size();
      testerReputationMap.put(testers, bugCount);
    }
    return testerReputationMap;
  }
}
