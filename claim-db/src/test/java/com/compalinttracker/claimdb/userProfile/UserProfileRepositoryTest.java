package com.compalinttracker.claimdb.userProfile;

import com.compalinttracker.claimdb.userRole.UserRole;
import com.compalinttracker.claimdb.userRole.UserRoleRepository;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLink;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkRepository;
import org.apache.catalina.User;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@DataJpaTest(
        properties = {  // the following line need because the nullable properties in the UserProfile entity are not works with during the testing we need to set it here
                "spring.jpa.properties.javax.persistence.validation.mode = none"
        }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)   // whil testing use embedded h2 database, otherwise Postgres
class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository underTest;

    @Mock
    private UserRoleLinkRepository userRoleLinkRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);


    }



    @Test
    void itShouldSaveUser() {
        // Given
        UUID id = UUID.randomUUID();
        UserProfile user = new UserProfile(id, "Name", "Sample", "name.sample@gmail.com");
        user.setAnalysises(null);
        user.setComplaints(null);

        // When
        underTest.save(user);

        // Then
        Optional<UserProfile> optionalUserProfile = underTest.findUserProfileById(id);
        assertThat(optionalUserProfile)
                .isPresent()
                .hasValueSatisfying( u -> {
                    assertThat(u.getId()).isEqualTo(id);
                    assertThat(u.getFirstName()).isEqualTo("Name");
                    assertThat(u.getLastName()).isEqualTo("Sample");
                    assertThat(u.getEmail()).isEqualTo("name.sample@gmail.com");
                });
    }

    @Test
    void itShouldNotSaveUserWhenFirstNameIsNull() {
        // Given
        UUID id = UUID.randomUUID();
        UserProfile user = new UserProfile(id, null, "Sample", "name.sample@gmail.com");
        user.setAnalysises(null);
        user.setComplaints(null);

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining("not-null property references a null or transient value : com.compalinttracker.claimdb.userProfile.UserProfile.firstName")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void itShouldNotSaveUserWhenLastNameIsNull() {
        // Given
        UUID id = UUID.randomUUID();
        UserProfile user = new UserProfile(id, "Name", null, "name.sample@gmail.com");
        user.setAnalysises(null);
        user.setComplaints(null);

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining("not-null property references a null or transient value : com.compalinttracker.claimdb.userProfile.UserProfile.lastName")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void itShouldNotSaveUserWhenEmailIsNull() {
        // Given
        UUID id = UUID.randomUUID();
        UserProfile user = new UserProfile(id, "Name", "Sample", null);
        user.setAnalysises(null);
        user.setComplaints(null);

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(user))
                .hasMessageContaining("not-null property references a null or transient value : com.compalinttracker.claimdb.userProfile.UserProfile.email")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void itShouldDeleteUserProfileById() {
        // Given
        UUID id = UUID.randomUUID();
        UserProfile user = new UserProfile(id, "Name", "Sample", "name.sample@gmail.com");
        user.setAnalysises(null);
        user.setComplaints(null);
        underTest.save(user);

        // When
        int affectedRows = underTest.deleteUserProfileById(id);   // it should give back 1

        // Then
        assertThat(affectedRows).isEqualTo(1);
    }

    @Test
    void itShouldNotDeleteUserProfileWhenItDoesNotExists() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        int affectedRows = underTest.deleteUserProfileById(id);   // it should give back 1

        // Then
        assertThat(affectedRows).isEqualTo(0);
    }

}