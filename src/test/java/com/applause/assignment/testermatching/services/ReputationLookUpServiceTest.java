package com.applause.assignment.testermatching.services;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.applause.assignment.testermatching.dto.TesterExperience;
import com.applause.assignment.testermatching.models.Bugs;
import com.applause.assignment.testermatching.models.Devices;
import com.applause.assignment.testermatching.models.Testers;
import com.applause.assignment.testermatching.repositories.BugsRepository;
import com.applause.assignment.testermatching.repositories.DeviceRepository;
import com.applause.assignment.testermatching.repositories.TesterRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReputationLookUpServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private ReputationLookUpService lookUpService;

    @Mock
    private TesterRepository testerRepository;

    @Mock
    private BugsRepository bugsRepository;

    private List<Devices> listOfDevices;
    private List<Testers> listOfTesters;
    private List<Bugs> listOfBugs;
    private List<String> countries;
    private List<String> devices;
    private List<Integer> deviceIds;
    private Devices device;
    private Testers tester;
    private Bugs bug;
    private TesterExperience testerExperience;
    private List<TesterExperience> experiences;
    private String country = "JP";
    private String deviceName = "Iphone 4";
    private int bugId = 2345;
    private int deviceId = 25632;
    private int testerId = 4;

    @BeforeEach
    public void setup() {
        tester = new Testers(testerId, "firstName", "lastName", country);
        testerExperience = new TesterExperience(tester.getFirstName() + " " + tester.getLastName(), 1);
        experiences = new ArrayList<>();
        listOfDevices = new ArrayList<Devices>();
        deviceIds = new ArrayList<Integer>();
        deviceIds.add(deviceId);
        listOfTesters = new ArrayList<Testers>();
        bug = new Bugs(bugId, deviceId, testerId, tester);
        countries = new ArrayList<String>();
        devices = new ArrayList<String>();
        listOfBugs = new ArrayList<Bugs>();
        listOfBugs.add(bug);
        listOfTesters.add(tester);
        device = new Devices();
        device.setDeviceId(deviceId);
        device.setTesters(listOfTesters);
        tester.setBugs(listOfBugs);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getReputationForAllTest() {
        when(testerRepository.findAll()).thenReturn(listOfTesters);
        Map<Testers, Integer> reputationMap = new HashMap<>();
        reputationMap.put(tester, 1);

        Map<Testers, Integer> response = lookUpService.getReputationForAll();

        assertEquals("Reputation doesnt match", reputationMap.get(tester),
                response.get(tester));
        verify(testerRepository, times(1)).findAll();
    }

    @Test
    public void getReputationByCountriesAndDevicesTest() {
        countries.add(country);
        devices.add(deviceName);
        listOfDevices.add(device);
        when(deviceRepository.findAllByDescriptionIn(devices)).thenReturn(listOfDevices);
        when(testerRepository.findAllByCountryInAndDevicesIn(countries, listOfDevices))
                .thenReturn(listOfTesters);
        when(bugsRepository.findByDeviceIdInAndTestersIn(deviceIds, listOfTesters))
                .thenReturn(listOfBugs);
        when(testerRepository.findById(anyInt())).thenReturn(tester);
        Map<Testers, Integer> reputationMap = new HashMap<>();
        reputationMap.put(tester, 1);

        Map<Testers, Integer> response = lookUpService
                .getReputationByCountriesAndDevices(countries, devices);

        verify(testerRepository, times(1)).findAllByCountryInAndDevicesIn(anyList(), anyList());
        verify(deviceRepository, times(1)).findAllByDescriptionIn(anyList());
        verify(bugsRepository, times(1)).findByDeviceIdInAndTestersIn(anyList(), anyList());
        verify(testerRepository, times(1)).findById(anyInt());
        assertEquals("Reputation doesnt match", reputationMap.get(tester),
                response.get(tester));
    }

    @Test
    public void getReputationByCountriesTest() {
        countries.add(country);
        when(testerRepository.findAllByCountryIn(countries)).thenReturn(listOfTesters);
        Map<Testers, Integer> reputationMap = new HashMap<>();
        reputationMap.put(tester, 1);
        Map<Testers, Integer> response = lookUpService.getReputationByCountries(countries);
        verify(testerRepository, times(1)).findAllByCountryIn(anyList());
        assertEquals("Reputation doesnt match", reputationMap.get(tester),
                response.get(tester));
    }

    @Test
    public void getReputationByDevicesTest() {
        devices.add(deviceName);
        listOfDevices.add(device);
        when(deviceRepository.findAllByDescriptionIn(devices)).thenReturn(listOfDevices);
        when(bugsRepository.findByDeviceIdInAndTestersIn(deviceIds, listOfTesters))
                .thenReturn(listOfBugs);
        when(testerRepository.findById(anyInt())).thenReturn(tester);
        Map<Testers, Integer> reputationMap = new HashMap<>();
        reputationMap.put(tester, 1);

        Map<Testers, Integer> response = lookUpService.getReputationByDevices(devices);

        verify(deviceRepository, times(1)).findAllByDescriptionIn(anyList());
        verify(bugsRepository, times(1)).findByDeviceIdInAndTestersIn(anyList(), anyList());
        verify(testerRepository, times(1)).findById(anyInt());

        assertEquals("Reputation doesnt match", reputationMap.get(tester),
                response.get(tester));
    }
}
