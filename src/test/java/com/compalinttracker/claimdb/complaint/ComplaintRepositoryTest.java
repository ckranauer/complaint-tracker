package com.compalinttracker.claimdb.complaint;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // testing JPA Queries
class ComplaintRepositoryTest {

    @Autowired
    private ComplaintRepository underTest;



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
        int deletedRow = underTest.deleteComplaintById(8l);

        // Then
        assertThat(deletedRow).isEqualTo(1);
        //underTest.findComplaintById(1l).isEmpty();
    }

    @Test
    void itShouldRemoveResponsible() {
        // Given
        // When
        // Then

    }
}