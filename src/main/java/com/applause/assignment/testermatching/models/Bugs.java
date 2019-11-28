package com.applause.assignment.testermatching.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity object representing Bugs database table
 */
@Entity
@Table(name = "BUGS")
public class Bugs implements Serializable {
  private static final long serialVersionUID = -3895912114846915844L;

  @Id
  @Column(name = "BUG_ID")
  private int bugId;

  @Column(name = "DEVICE_ID")
  private int deviceId;

  @Column(name = "TESTER_ID")
  private int testerId;

  /**
   * Bugs Constructor
   */
  public Bugs(int bugId, int deviceId, int testerId,
              Testers testers) {
    this.bugId = bugId;
    this.deviceId = deviceId;
    this.testerId = testerId;
    this.testers = testers;
  }

  @ManyToOne()
  @JoinColumn(name = "TESTER_ID", referencedColumnName = "TESTER_ID", insertable = false, updatable = false)
  private Testers testers;

  public int getTesterId() {
    return testerId;
  }

  public int getBugId() {
    return bugId;
  }

  public int getDeviceId() {
    return deviceId;
  }

  public Testers getTesters() {
    return testers;
  }
}