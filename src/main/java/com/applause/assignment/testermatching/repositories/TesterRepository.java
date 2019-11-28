package com.applause.assignment.testermatching.repositories;

import com.applause.assignment.testermatching.models.Devices;
import com.applause.assignment.testermatching.models.Testers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface to query Testers database table
 */
public interface TesterRepository extends CrudRepository<Testers,Integer> {
  List<Testers> findAll();
  Testers findById(int testerid);
  List<Testers> findAllByCountryInAndDevicesIn(List<String> countries, List<Devices> devices);
  List<Testers> findAllByCountryIn(List<String> countries);
}
