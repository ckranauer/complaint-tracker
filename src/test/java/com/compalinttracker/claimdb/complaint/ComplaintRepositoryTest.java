package com.compalinttracker.claimdb.complaint;

import com.compalinttracker.claimdb.userProfile.UserProfile;
import com.compalinttracker.claimdb.userProfile.UserProfileRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DataJpaTest  // testing JPA Queries
class ComplaintRepositoryTest {

   @Autowired
    private ComplaintRepository underTest;

   @Autowired
   private UserProfileRepository userProfileRepository;


    @Test
    void itShouldFindAllComplaintAnalysis() {
        // Given

        Complaint complaintOne = new Complaint();
        Complaint complaintTwo = new Complaint();
        Complaint complaintThree = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        complaintTwo.setSerialNumber("A0002");
        complaintTwo.setCreatedAt(LocalDateTime.now());
        complaintThree.setSerialNumber("A0003");
        complaintThree.setCreatedAt(LocalDateTime.now());

        underTest.save(complaintOne);
        underTest.save(complaintTwo);
        underTest.save(complaintThree);



        // When
        List<ComplaintAnalysisDto> complaints = underTest.findAllComplaintAnalysis();

        // Then
        assertThat(complaints).size().isEqualTo(3);
    }



    @Test
    void itShouldFindComplaintAnalysisById() {
        // Given

        Complaint complaintOne = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        underTest.save(complaintOne);

        // When
        Optional<ComplaintAnalysisDto> complaintOptional = underTest.findComplaintAnalysisById(1L);
        // Then
        assertThat(complaintOptional).isPresent();
    }

    @Test
    void itShouldFindComplaintById() {
        // Given
        Complaint complaintOne = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        underTest.save(complaintOne);

        // When
        Optional<Complaint> complaintOptional = underTest.findComplaintById(2l);
        // Then
        assertTrue(complaintOptional.isPresent());
    }

    @Test
    void itShouldFindComplaintBySerNo() {
        // Given
        Complaint complaintOne = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        underTest.save(complaintOne);

        // When
        Optional<Complaint> complaintOptional = underTest.findComplaintBySerNo("A0001");
        // Then
        assertThat(complaintOptional).isPresent();
    }

    @Test
    void itShouldFindComplaintByQmsNo() {
        // Given
        Complaint complaintOne = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setQmsNumber("Q0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        underTest.save(complaintOne);

        // When
        Optional<Complaint> complaintOptional = underTest.findComplaintByQmsNo("Q0001");
        // Then
        assertThat(complaintOptional).isPresent();

    }

    @Test
    void itShouldDeleteComplaintById() {
        // Given
        Complaint complaintOne = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        underTest.save(complaintOne);

        // When
        int deletedRow = underTest.deleteComplaintById(10l);

        // Then
        assertThat(deletedRow).isEqualTo(1);
        //underTest.findComplaintById(1l).isEmpty();
    }


    @Test
    void itShouldRemoveResponsible() {
        // Given
        UUID userId = UUID.randomUUID();
        UserProfile user = new UserProfile(userId, "TestFirstName","TestLastName", "test.email@gmail.com");
        userProfileRepository.save(user);
        //given(userProfileRepository.findUserProfileById(userId)).willReturn((Optional.of(user)));

        Complaint complaintOne = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        complaintOne.setResponsible(userProfileRepository.findUserProfileById(userId).get());
        underTest.save(complaintOne);
        Long complaintId = underTest.findComplaintBySerNo("A0001").get().getId();

        // When
        underTest.removeResponsible(complaintId);

        // Then
        assertThat(underTest.findUserProfileID("A0001")).isNotPresent();
    }

    @Test
    void itShouldFindUserProfileID(){
        // Given
        UUID userId = UUID.randomUUID();
        UserProfile user = new UserProfile(userId, "TestFirstName","TestLastName", "test.email@gmail.com");
        userProfileRepository.save(user);
        //given(userProfileRepository.findUserProfileById(userId)).willReturn((Optional.of(user)));

        Complaint complaintOne = new Complaint();
        complaintOne.setSerialNumber("A0001");
        complaintOne.setCreatedAt(LocalDateTime.now());
        complaintOne.setResponsible(userProfileRepository.findUserProfileById(userId).get());
        underTest.save(complaintOne);

        // When
        Optional<UUID> uuid =underTest.findUserProfileID("A0001");

        // Then
        assertThat(uuid).isPresent();
    }
}