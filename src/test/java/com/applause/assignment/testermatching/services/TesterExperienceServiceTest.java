package com.applause.assignment.testermatching.services;

import com.applause.assignment.testermatching.repositories.BugsRepository;
import com.applause.assignment.testermatching.repositories.DeviceRepository;
import com.applause.assignment.testermatching.repositories.TesterRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TesterExperienceServiceTest {
  @Mock
  private DeviceRepository deviceRepository;

  @Mock
  private TesterRepository testerRepository;

  @Mock
  private BugsRepository bugsRepository;

  @InjectMocks
  private TesterExperienceService service;

}
