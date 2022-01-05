package com.compalinttracker.claimdb.userProfile;

import com.compalinttracker.claimdb.userRole.UserRoleRepository;
import com.compalinttracker.claimdb.userRole.UserRoleServiceImpl;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

class UserProfileServiceImplTest {



    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRoleLinkRepository userRoleLinkRepository;

    @Mock
    private UserRoleRepository userRoleRepository;


    @Captor
    private ArgumentCaptor<UserProfile> userProfileArgumentCaptor;


    private UserProfileServiceImpl underTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserProfileServiceImpl(userProfileRepository,  userRoleRepository, userRoleLinkRepository );
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
    void itShouldGetUserProfileList() {
        // Given
        int page = 0;
        int limit = 2;

        List<UserProfile> profiles = new ArrayList<>();
        UserProfile profileOne = new UserProfile(UUID.randomUUID(), "FirstTest1", "LastTest1", "f1l1@gmail.com");
        UserProfile profileTwo = new UserProfile(UUID.randomUUID(), "FirstTest2", "LastTest2", "f2l2@gmail.com");

        profiles.add(profileOne);
        profiles.add(profileTwo);

        // When
        when(userProfileRepository.findAll()).thenReturn(profiles);

        // Then
        assertEquals(2, underTest.list(limit, page).size());
    }



    @Test
    void itShouldGetUserProfileById() {
        // Given
        UUID id = UUID.randomUUID();

        // ... Existing UserProfile
        given(userProfileRepository.findUserProfileById(id)).willReturn(Optional.of(Mockito.mock(UserProfile.class)));

        // When
        UserProfile userProfile = underTest.get(id);

        // Then
        assertThat(userProfile).isNotNull();
    }

    @Test
    void itShouldThrownWhenUserProfileIsNotExists() {
        // Given
        UUID id = UUID.randomUUID();

        // ... UserProfile is not exists
        given(userProfileRepository.findUserProfileById(id)).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.get(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(String.format("No value present"));
    }

    @Test
    void itShouldThrownWhenIdIsNull() {
        // Given
        UUID id = null;

        // When
        // Then
        assertThatThrownBy(() -> underTest.get(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("id is null"));

    }

    @Test
    void itShouldUpdate() {
        // Given
        // When
        // Then

    }

    @Test
    void itShouldDeleteUserProfile() {
        // Given
        UUID id = UUID.randomUUID();

        given(userProfileRepository.findUserProfileById(id)).willReturn(Optional.of(Mockito.mock(UserProfile.class)));

        // ... UserProfile exists
        given(userProfileRepository.deleteUserProfileById(id)).willReturn(1);

        // When
        boolean isDeleted = underTest.delete(id);

        // Then
        assertThat(isDeleted).isTrue();
    }

    @Test
    void itShouldNotDeleteUserProfileWhenItDoesNotExists() {
        // Given
        UUID id = UUID.randomUUID();
        given(userProfileRepository.findUserProfileById(id)).willReturn(Optional.empty());

        // When
        boolean isDeleted = underTest.delete(id);

        // Then
        assertThat(isDeleted).isFalse();
        then(userProfileRepository).should(never()).delete(any(UserProfile.class));
    }

    @Test
    void itShouldAddUserRoleToUserProfile() {
        // Given
        // When
        // Then

    }
}