package com.example.parkingapp.service.rate;

import com.example.parkingapp.config.security.AuthoritiesConstants;
import com.example.parkingapp.domain.Authority;
import com.example.parkingapp.domain.User;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

public class ParkingRateFactoryTest implements WithAssertions {
    private ParkingRateFactory sut;
    private RegularParkingRate regularParkingRate;
    private VipParkingRate vipParkingRate;

    @Before
    public void setUp() throws Exception {
        this.regularParkingRate = new RegularParkingRate();
        this.vipParkingRate = new VipParkingRate();
        sut = new ParkingRateFactory(regularParkingRate, vipParkingRate);
    }

    @Test
    public void shouldReturnVipIfUserIsVip() {
        //given
        Authority vipAuthority = new Authority();
        vipAuthority.setName(AuthoritiesConstants.VIP_DRIVER);
        User vipUser = new User();
        vipUser.setAuthorities(new HashSet<>(Collections.singleton(vipAuthority)));

        //when
        ParkingRate result = sut.getParkingRate(vipUser);

        //then
        assertThat(result).isOfAnyClassIn(VipParkingRate.class);
    }

    @Test
    public void shouldReturnRegularIfUserIsRegular() {
        //given
        Authority regularDriverAuthority = new Authority();
        regularDriverAuthority.setName(AuthoritiesConstants.REGULAR_DRIVER);
        User regularUser = new User();
        regularUser.setAuthorities(new HashSet<>(Collections.singleton(regularDriverAuthority)));

        //when
        ParkingRate result = sut.getParkingRate(regularUser);

        //then
        assertThat(result).isOfAnyClassIn(RegularParkingRate.class);
    }

    @Test
    public void shouldReturnRegularIfUserAnonymous() {
        //when
        ParkingRate result = sut.getParkingRate(null);

        //then
        assertThat(result).isOfAnyClassIn(RegularParkingRate.class);
    }
}
