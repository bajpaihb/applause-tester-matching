package com.applause.assignment.testermatching.repositories;

  import com.applause.assignment.testermatching.models.Devices;
  import java.util.List;
  import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface to query Devices database table
 */
public interface DeviceRepository extends CrudRepository<Devices, Integer> {
  List<Devices> findAllByDescriptionIn(List<String> devices);
}
