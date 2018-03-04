package com.example.parkingapp.api.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * View Model to store car's license plate
 */
public class LicensePlateVM {
    @NotNull
    private String number;
    @Pattern(regexp = "\\d{5}")
    private String pinCode;

    public LicensePlateVM() {
    }

    public LicensePlateVM(@NotNull String number, @Pattern(regexp = "\\d{5}") String pinCode) {
        this.number = number;
        this.pinCode = pinCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "LicensePlateVM{" +
            "number='" + number + '\'' +
            '}';
    }
}
