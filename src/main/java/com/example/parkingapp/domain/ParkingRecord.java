package com.example.parkingapp.domain;

import com.example.parkingapp.config.AcceptedCurrencies;
import com.example.parkingapp.config.ParkingRecordStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Record storing information about parking meter events
 */
@Entity
@Table(name = "parking_records")
public class ParkingRecord implements Serializable {
    @EmbeddedId
    private ParkingRecordID id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User user;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "pin_code_hash")
    private String pinCode;

    @Enumerated(EnumType.STRING)
    private ParkingRecordStatus status = ParkingRecordStatus.STARTED;

    @Enumerated(EnumType.STRING)
    private AcceptedCurrencies currency;

    @Column(name = "payment")
    private BigDecimal payment;

    public ParkingRecordID getId() {
        return id;
    }

    public void setId(ParkingRecordID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ParkingRecordStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingRecordStatus status) {
        this.status = status;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public AcceptedCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(AcceptedCurrencies currency) {
        this.currency = currency;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingRecord that = (ParkingRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ParkingRecord{" +
            "id=" + id +
            ", user=" + user +
            ", endDate=" + endDate +
            '}';
    }
}
