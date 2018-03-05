package com.example.parkingapp.service;

import com.example.parkingapp.api.rest.exceptions.VehicleAlreadyParkedException;
import com.example.parkingapp.api.rest.exceptions.VehicleNotParkedException;
import com.example.parkingapp.api.rest.vm.LicensePlateVM;
import com.example.parkingapp.api.rest.vm.ParkingMeterStartedVM;
import com.example.parkingapp.config.ParkingRecordStatus;
import com.example.parkingapp.domain.ParkingRecord;
import com.example.parkingapp.domain.User;
import com.example.parkingapp.repository.ParkingRecordRepository;
import com.example.parkingapp.repository.UserRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingMeterServiceTest implements WithAssertions {
    private static final String LICENSE_NUMBER = "ABC 12345";

    private UserRepository userRepository;
    private ParkingRecordRepository parkingRecordRepository;
    private PasswordEncoder passwordEncoder;
    private ParkingCostCalculator costCalculator;
    private ParkingMeterService sut;

    @Before
    public void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        parkingRecordRepository = Mockito.mock(ParkingRecordRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        costCalculator = Mockito.mock(ParkingCostCalculator.class);

        sut = new ParkingMeterService(userRepository, parkingRecordRepository, passwordEncoder, costCalculator);
    }

    @Test(expected = VehicleAlreadyParkedException.class)
    public void shouldThrowExceptionWhenStartIssuedButVehicleAlreadyParked() {
        //given
        when(parkingRecordRepository.findByLicensePlateAndStatus(LICENSE_NUMBER, ParkingRecordStatus.STARTED))
            .thenReturn(Optional.of(new ParkingRecord()));
        //expect
        sut.startParkingMeter(LICENSE_NUMBER);
    }

    @Test
    public void shouldStartParkingMeterForAuthenticated() {
        //given
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
            new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User("user", "", Collections.emptySet()), "")
        );
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findOneByLogin(any())).thenReturn(Optional.of(new User()));
        //when
        ParkingMeterStartedVM result = sut.startParkingMeter(LICENSE_NUMBER);
        //then
        assertThat(result.getLicensePlateNumber()).isEqualTo(LICENSE_NUMBER);
        assertThat(result.getPinCode()).isNullOrEmpty();
        verify(parkingRecordRepository, times(1)).save(any());
    }

    @Test
    public void shouldStartParkingMeterForAnonymous() {
        //given
        when(userRepository.findOneByLogin(null)).thenReturn(Optional.empty());
        //when
        ParkingMeterStartedVM result = sut.startParkingMeter(LICENSE_NUMBER);
        //then
        assertThat(result.getLicensePlateNumber()).isEqualTo(LICENSE_NUMBER);
        assertThat(result.getPinCode()).isNotEmpty();
        verify(parkingRecordRepository, times(1)).save(any());
    }

    @Test(expected = VehicleNotParkedException.class)
    public void shouldThrowExceptionWhenStopIssuedButVehicleNotParked() {
        //given
        LicensePlateVM licensePlateVM = new LicensePlateVM();
        licensePlateVM.setNumber(LICENSE_NUMBER);
        when(parkingRecordRepository.findByLicensePlateAndStatus(LICENSE_NUMBER, ParkingRecordStatus.STARTED))
            .thenReturn(Optional.empty());
        //expect
        sut.stopParkingMeter(licensePlateVM, "PLN");
    }
}
