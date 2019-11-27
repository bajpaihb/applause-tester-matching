package com.applause.assignment.testermatching.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TESTER_DEVICE")
public class TesterDevice implements Serializable {
  private static final long serialVersionUID = -7003324516685466301L;

  @Id
  @Column(name = "TESTER_ID")
  private int testerId;

  @Column(name = "DEVICE_ID")
  private int deviceId;

  public int getTesterId() {
    return testerId;
  }

  public int getDeviceId() {
    return deviceId;
  }
}
