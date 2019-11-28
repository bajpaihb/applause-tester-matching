package com.applause.assignment.testermatching.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer Object representing Tester Experience
 */
public class TesterExperience {
  private String testerName;
  private int testerReputation;

  /**
   * Constructor for the class
   */
  public TesterExperience(String testerName, int testerReputation) {
    this.testerName = testerName;
    this.testerReputation = testerReputation;
  }

  @JsonProperty("Name")
  public String getTesterName() {
    return testerName;
  }

  @JsonProperty("Experience")
  public int getTesterReputation() {
    return testerReputation;
  }
}
