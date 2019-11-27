package com.applause.assignment.testermatching.services;

import com.applause.assignment.testermatching.dto.TesterExperience;
import com.applause.assignment.testermatching.models.Testers;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TesterExperienceService {

  private static final String ALL = "ALL";
  private static final Logger logger = LoggerFactory.getLogger(TesterExperienceService.class);

  @Autowired
  private ReputationLookUpService reputationLookUpService;

  public List<TesterExperience> getTestersBySortedExperiences(List<String> countries,
    List<String> devices) {

    Map<Testers, Integer> testerReputationMap = null;
    if (!countries.contains(ALL) && !devices.contains(ALL)) {
      testerReputationMap = reputationLookUpService
        .getReputationByCountriesAndDevices(countries, devices);
    } else if (!countries.contains(ALL) && devices.contains(ALL)) {
      testerReputationMap = reputationLookUpService.getReputationByCountries(countries);
    } else if (countries.contains(ALL) && !devices.contains(ALL)) {
      testerReputationMap = reputationLookUpService.getReputationByDevices(devices);
    } else if (countries.contains(ALL) && devices.contains(ALL)) {
      testerReputationMap = reputationLookUpService.getReputationForAll();
    } else {
      logger.debug("Invalid values supplied");
    }
    return generateSortedExperienceList(testerReputationMap);
  }


  private TesterExperience generateTesterReputationDto(Testers testers, Integer reputation) {
    return new TesterExperience((testers.getFirstName() + " " + testers.getLastName()), reputation);
  }

  private List<TesterExperience> generateSortedExperienceList(
    Map<Testers, Integer> testerReputationMap) {
    return testerReputationMap.entrySet().stream()
      .map(x -> generateTesterReputationDto(x.getKey(), x.getValue()))
      .sorted(Comparator.comparingInt(TesterExperience::getTesterReputation)
        .reversed()).collect(Collectors.toList());
  }

}
