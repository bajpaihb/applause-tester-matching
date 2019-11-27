package com.applause.assignment.testermatching.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TesterExperience {
  private String testerName;
  private int testerReputation;

  public TesterExperience(String testerName, int testerReputation) {
    this.testerName = testerName;
    this.testerReputation = testerReputation;
  }

  @JsonProperty("Name")
  public String getTesterName() {
    return testerName;
  }

  @JsonProperty("Reputation")
  public int getTesterReputation() {
    return testerReputation;
  }
}
