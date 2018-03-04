package com.example.parkingapp.service.rate;

import com.example.parkingapp.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.parkingapp.config.security.AuthoritiesConstants.VIP_DRIVER;

@Component
public class ParkingRateFactory {
    private RegularParkingRate regularParkingRate;
    private VipParkingRate vipParkingRate;

    public ParkingRateFactory(RegularParkingRate regularParkingRate, VipParkingRate vipParkingRate) {
        this.regularParkingRate = regularParkingRate;
        this.vipParkingRate = vipParkingRate;
    }

    public ParkingRate getParkingRate(User user) {
        if (isVip(user)) {
            return vipParkingRate;
        } else {
            return regularParkingRate;
        }
    }

    private boolean isVip(User user) {
        return Optional.ofNullable(user).map(u -> u.getAuthorities().stream()
            .anyMatch(a -> a.getName().equals(VIP_DRIVER)))
            .orElse(false);
    }
}
