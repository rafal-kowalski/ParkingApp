package com.example.parkingapp.service;

import com.example.parkingapp.api.rest.exceptions.VehicleNotParkedException;
import com.example.parkingapp.api.rest.vm.ParkingStatisticsVM;
import com.example.parkingapp.api.rest.vm.VehicleStatusVM;
import com.example.parkingapp.config.ParkingRecordStatus;
import com.example.parkingapp.domain.ParkingRecord;
import com.example.parkingapp.domain.ParkingRecordID;
import com.example.parkingapp.repository.ParkingRecordRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ParkingAdministrationServiceTest implements WithAssertions {
    private static final String EXISTING_LICENSE_NUMBER = "ABC 1234";
    private static final String NOT_EXISTING_LICENSE_NUMBER = "DEF 5678";

    private ParkingAdministrationService sut;
    private ParkingRecordRepository parkingRecordRepositoryMock;

    @Before
    public void setUp() throws Exception {
        parkingRecordRepositoryMock = Mockito.mock(ParkingRecordRepository.class);
        sut = new ParkingAdministrationService(parkingRecordRepositoryMock);
    }

    @Test(expected = VehicleNotParkedException.class)
    public void shouldThrowExceptionWhenVehicleNotParked() {
        //given
        when(parkingRecordRepositoryMock.findByLicensePlateAndStatus(NOT_EXISTING_LICENSE_NUMBER, ParkingRecordStatus.STARTED))
            .thenReturn(Optional.empty());
        //expect
        sut.checkVehicleStatus(NOT_EXISTING_LICENSE_NUMBER);
    }

    @Test
    public void shouldReturnVehicleStatus() {
        //given
        LocalDateTime dateTime = LocalDateTime.now();
        ParkingRecordID id = new ParkingRecordID();
        id.setLicensePlate(EXISTING_LICENSE_NUMBER);
        id.setStartDate(dateTime);
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setId(id);

        when(parkingRecordRepositoryMock.findByLicensePlateAndStatus(EXISTING_LICENSE_NUMBER, ParkingRecordStatus.STARTED))
            .thenReturn(Optional.of(parkingRecord));

        //when
        VehicleStatusVM result = sut.checkVehicleStatus(EXISTING_LICENSE_NUMBER);

        //then
        assertThat(result.getStartDate()).isEqualTo(dateTime);
        assertThat(result.getLicensePlate()).isEqualTo(EXISTING_LICENSE_NUMBER);
    }

    @Test
    public void shouldReturnEarningsForGivenDate() {
        //given
        LocalDate date = LocalDate.now();
        when(parkingRecordRepositoryMock.sumAllPaymentsWithinDate(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(BigDecimal.TEN);

        //when
        ParkingStatisticsVM result = sut.getEarningsForGivenDay(date);

        //then
        assertThat(result.getEarnings()).isEqualByComparingTo(BigDecimal.TEN);
    }
}
