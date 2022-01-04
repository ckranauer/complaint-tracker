package com.compalinttracker.claimdb.userProfile;

import com.compalinttracker.claimdb.userRole.UserRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRoleServiceImpl userRoleServiceImpl;

    @Captor
    private ArgumentCaptor<UserProfile> userProfileArgumentCaptor;


    private UserProfileServiceImpl underTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserProfileServiceImpl(userProfileRepository, userRoleServiceImpl);
    }

    @Test
    void itShouldCreateUserProfile() {
        // Given an user profile
        UUID id = UUID.randomUUID();
        UserProfile user = new UserProfile(id, "Name", "Sample", "name.sample@gmail.com");
        user.setAnalysises(null);
        user.setComplaints(null);

        // ... user profile with this email is not taken
        given(userProfileRepository.selectUserProfileByEmail(user.getEmail())).willReturn(Optional.empty());

        // TODO: email validator

        // When
        underTest.create(user);

        // Then
        then(userProfileRepository).should().save(userProfileArgumentCaptor.capture());
        UserProfile userProfileArgumentCaptorValue = userProfileArgumentCaptor.getValue();
        assertThat(userProfileArgumentCaptorValue).isEqualTo(user);

    }

    @Test
    void itShouldNotCreateUserProfileWhenProfileExists() {
        // Given an user profile
        UUID id = UUID.randomUUID();
        UserProfile user = new UserProfile(id, "Name", "Sample", "name.sample@gmail.com");
        user.setAnalysises(null);
        user.setComplaints(null);

        // ... an existing profile is returned
        given(userProfileRepository.selectUserProfileByEmail(user.getEmail())).willReturn(Optional.of(user));

        // TODO: email validator

        // When
        underTest.create(user);

        // Then
        then(userProfileRepository).should(never()).save(any());
    }

    @Test
    void itShouldThrownWhenEmailIsTaken() {
        // Given an user profile
        UUID id = UUID.randomUUID();
        String email = "name.sample@gmail.com";
        UserProfile user = new UserProfile(id, "Name", "Sample", email);
        user.setAnalysises(null);
        user.setComplaints(null);

        UserProfile userTwo = new UserProfile(id, "NameTwo", "SampleTwo", email);
        userTwo.setAnalysises(null);
        userTwo.setComplaints(null);

        // ... user profile with this email is not taken
        given(userProfileRepository.selectUserProfileByEmail(user.getEmail())).willReturn(Optional.of(userTwo));

        // TODO: email validator

        // When
        // Then
        assertThatThrownBy(() -> underTest.create(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("email "+ user.getEmail() + " is taken"));

        // Finally
        then(userProfileRepository).should(never()).save(any(UserProfile.class));

    }

    @Test
    void itShouldCreateUserProfileWhenIdIsNull() {
        // Given an user profile

        UserProfile user = new UserProfile(null, "Name", "Sample", "name.sample@gmail.com");
        user.setAnalysises(null);
        user.setComplaints(null);

        // ... user profile with this email is not taken
        given(userProfileRepository.selectUserProfileByEmail(user.getEmail())).willReturn(Optional.empty());

        // TODO: email validator

        // When
        underTest.create(user);

        // Then
        then(userProfileRepository).should().save(userProfileArgumentCaptor.capture());
        UserProfile userProfileArgumentCaptorValue = userProfileArgumentCaptor.getValue();
        assertThat(userProfileArgumentCaptorValue.getId()).isNotNull();

    }

    @Test
    void itShouldList() {
        // Given
        // When
        // Then

    }

    @Test
    void itShouldGet() {
        // Given
        // When
        // Then

    }

    @Test
    void itShouldUpdate() {
        // Given
        // When
        // Then

    }

    @Test
    void itShouldDelete() {
        // Given
        // When
        // Then

    }

    @Test
    void itShouldAddUserRoleToUserProfile() {
        // Given
        // When
        // Then

    }
}