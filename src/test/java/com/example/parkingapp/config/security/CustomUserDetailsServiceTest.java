package com.example.parkingapp.config.security;

import com.example.parkingapp.domain.User;
import com.example.parkingapp.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class CustomUserDetailsServiceTest implements WithAssertions {
    public static final String EXISTING_LOGIN = "user1";
    public static final String NOT_EXISTING_LOGIN = "user2";

    private UserRepository userRepositoryMock;

    private User user;

    private CustomUserDetailsService sut;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setLogin(EXISTING_LOGIN);
        user.setPasswordHash(RandomString.make());
        userRepositoryMock = Mockito.mock(UserRepository.class);

        sut = new CustomUserDetailsService(userRepositoryMock);
    }

    @Test
    public void shouldReturnUserByLogin() {
        //given
        when(userRepositoryMock.findOneWithAuthoritiesByLogin(EXISTING_LOGIN))
            .thenReturn(Optional.of(user));

        //when
        UserDetails result = sut.loadUserByUsername(EXISTING_LOGIN);

        //then
        assertThat(result.getUsername()).isEqualTo(EXISTING_LOGIN);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExceptionWhenUserNotFound() {
        //given
        when(userRepositoryMock.findOneWithAuthoritiesByLogin(NOT_EXISTING_LOGIN))
            .thenReturn(Optional.empty());

        //expect
        sut.loadUserByUsername(NOT_EXISTING_LOGIN);
    }
}
