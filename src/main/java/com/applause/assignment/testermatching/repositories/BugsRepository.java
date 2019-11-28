package com.applause.assignment.testermatching.repositories;

import org.springframework.data.repository.CrudRepository;
import com.applause.assignment.testermatching.models.Bugs;
import com.applause.assignment.testermatching.models.Testers;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface to query Bugs database table
 */
@Repository
public interface BugsRepository extends CrudRepository<Bugs, Integer> {
  List<Bugs> findAll();
  List<Bugs> findByDeviceIdInAndTestersIn(List<Integer> deviceIds, List<Testers> testers);
}
