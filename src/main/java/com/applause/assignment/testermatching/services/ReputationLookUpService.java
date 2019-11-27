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

@Component
public class ReputationLookUpService {

  private static final Logger logger = LoggerFactory.getLogger(ReputationLookUpService.class);

  @Autowired
  private BugsRepository bugsRepository;

  @Autowired
  private TesterRepository testerRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  public Map<Testers, Integer> getReputationForAll() {
    logger.debug("getReputationForAll: Querying for all the testers");
    List<Testers> listOfTesters = testerRepository.findAll();
    return populateReputationByTesters(listOfTesters);
  }

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
        deviceIds, listOfTesters);
    List<Bugs> listOfBugs = bugsRepository.findByDeviceIdInAndTestersIn(deviceIds, listOfTesters);
    return populateReputationByBugsAndTesters(listOfBugs, listOfTesters);
  }

  public Map<Testers, Integer> getReputationByCountries(List<String> countries) {
    logger
      .debug("getReputationByCountries: Querying for all the testers by countries: {}", countries);
    List<Testers> listOfTesters = testerRepository.findAllByCountryIn(countries);
    return populateReputationByTesters(listOfTesters);
  }

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
        deviceIds, listOfTesters);
    List<Bugs> listOfBugs = bugsRepository.findByDeviceIdInAndTestersIn(deviceIds, listOfTesters);
    return populateReputationByBugsAndTesters(listOfBugs, listOfTesters);
  }

  private Map<Testers, Integer> populateReputationByBugsAndTesters(List<Bugs> listOfBugs,
    List<Testers> listOfTesters) {
    Map<Testers, Integer> testerReputationMap = new HashMap<>();
    for (Testers tester : listOfTesters) {
      testerReputationMap.put(tester, 0);
    }
    for (Bugs bug : listOfBugs) {
      logger
        .debug("populateReputationByBugsAndTesters: Querying for the tester by Id: {}",
          bug.getTesterId());
      Testers testers = testerRepository.findById(bug.getTesterId());
      testerReputationMap.put(testers, 1 + testerReputationMap.get(testers));
    }
    return testerReputationMap;
  }

  private Map<Testers, Integer> populateReputationByTesters(List<Testers> listOfTesters) {
    Map<Testers, Integer> testerReputationMap = new HashMap<>();
    for (Testers tester : listOfTesters) {
      int bugCount = tester.getBugs().size();
      testerReputationMap.put(tester, bugCount);
    }
    return testerReputationMap;
  }
}
