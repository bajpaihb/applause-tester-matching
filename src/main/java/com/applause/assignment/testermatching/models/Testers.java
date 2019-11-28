package com.applause.assignment.testermatching.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity object representing Testers database table
 */
@Entity
@Table(name = "TESTERS")
public class Testers implements Serializable {

  private static final long serialVersionUID = 3691138139160872932L;

  @Id
  @Column(name = "TESTER_ID")
  private int id;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "LAST_LOGIN")
  private Timestamp lastLogin;

  @ManyToMany(cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
  })
  @JoinTable(name = "TESTER_DEVICE",
          joinColumns = @JoinColumn(name = "tester_id"),
          inverseJoinColumns = @JoinColumn(name = "device_id")
  )
  private List<Devices> devices;

  @OneToMany(mappedBy = "testers", cascade = CascadeType.ALL)
  private List<Bugs> bugs;

  /**
   * Constructor for Testers
   */
  public Testers(int id, String firstName, String lastName, String country) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getCountry() {
    return country;
  }

  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public List<Bugs> getBugs() {
    return bugs;
  }

  public void setBugs(List<Bugs> bugs) {
    this.bugs = bugs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Testers testers = (Testers) o;
    return id == testers.id &&
            Objects.equals(firstName, testers.firstName) &&
            Objects.equals(lastName, testers.lastName) &&
            Objects.equals(country, testers.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, country);
  }

  @Override
  public String toString() {
    return "Tester{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }
}