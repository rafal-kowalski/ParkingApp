package com.example.parkingapp.repository;

import com.example.parkingapp.config.ParkingRecordStatus;
import com.example.parkingapp.domain.Earnings;
import com.example.parkingapp.domain.ParkingRecord;
import com.example.parkingapp.domain.ParkingRecordID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, ParkingRecordID> {
    @Query("SELECT pr FROM ParkingRecord pr WHERE pr.id.licensePlate = ?1 AND pr.status = ?2")
    Optional<ParkingRecord> findByLicensePlateAndStatus(String licensePlate, ParkingRecordStatus status);

    @Query("SELECT new com.example.parkingapp.domain.Earnings(SUM(pr.payment), pr.currency) " +
        "FROM ParkingRecord pr WHERE pr.id.startDate > ?1 AND pr.endDate < ?2 GROUP BY pr.currency")
    Set<Earnings> sumAllPaymentsWithinDate(LocalDateTime start, LocalDateTime end);
}
