package com.applause.assignment.testermatching.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity object representing Devices database table
 */
@Entity
@Table(name = "DEVICES")
public class Devices implements Serializable {

  private static final long serialVersionUID = -5373739784648069313L;
  @Id
  @Column(name = "DEVICE_ID")
  private int deviceId;
  @Column(name = "DESCRIPTION")
  private String description;

  @ManyToMany(mappedBy = "devices")
  private List<Testers> testers;

  public int getDeviceId() {
    return deviceId;
  }

  public List<Testers> getTesters() {
    return testers;
  }

  public String getDescription() {
    return description;
  }

  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }

  public void setTesters(List<Testers> testers) {
    this.testers = testers;
  }
}
