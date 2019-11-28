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

/**
 * Service class for generating and sorting tester experience
 */
@Component
public class TesterExperienceService {

  private static final String ALL = "ALL";
  private static final Logger logger = LoggerFactory.getLogger(TesterExperienceService.class);

  @Autowired
  private ReputationLookUpService reputationLookUpService;

  /**
   * Serves data to end point applause/testers/experiences by calculating and sorting
   * testers based on experiences/reputation
   *
   * @param countries List of Countries
   * @param devices List of Devices
   * @return Sorted list of TesterExperience based on reputations/experiences
   */
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
    }
    return generateSortedExperienceList(testerReputationMap);
  }

  /**
   * generate tester reputation DTO based on tester and reputation
   */
  private TesterExperience generateTesterReputationDto(Testers testers, Integer reputation) {
    logger.debug("Adding tester to the result map with name: {} and reputation: {}",
            testers.getFirstName() + " " + testers.getLastName(), reputation);
    return new TesterExperience((testers.getFirstName() + " " + testers.getLastName()), reputation);
  }

  /**
   * generate sorted experience list from the tester reputation map based on
   * tester reputation score.
   */
  private List<TesterExperience> generateSortedExperienceList(
          Map<Testers, Integer> testerReputationMap) {
    return testerReputationMap.entrySet().stream()
            .map(x -> generateTesterReputationDto(x.getKey(), x.getValue()))
            .sorted(Comparator.comparingInt(TesterExperience::getTesterReputation)
                    .reversed()).collect(Collectors.toList());
  }
}