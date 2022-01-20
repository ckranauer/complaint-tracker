package com.compalinttracker.claimdb.complaint;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {


   //  " qms_number, customer_ref_number, " +
    //         "claimed_fault " +

    @Query(
            value = "SELECT complaint.id, serial_number, " +
                    " qms_number, customer_ref_number, " +
                    "claimed_fault, " +
                    "CONCAT(user_profile.first_name, ' ', user_profile.last_name)  as analyzed_by, " +
                    "arrived_at, " +
                    "analysis_started_at, " +
                    "analysis_ended_at " +
                    "FROM complaint " +
                    "LEFT JOIN analysis ON analysis.complaint_id = complaint.id " +
                    "LEFT JOIN user_profile ON analysis.user_profile_id = user_profile.id",
            nativeQuery = true
    )
    List<ComplaintAnalysisDto> findAllComplaintAnalysis();

    Optional<Complaint> findComplaintById(Long id);

    @Query(
            value = "SELECT * " +
                    "FROM complaint WHERE serial_number = ?1",
            nativeQuery = true
    )
    Optional<Complaint> findComplaintBySerNo(String serialNumber);

    @Query(
            value = "SELECT * " +
                    " FROM complaint WHERE qms_number = ?1 ",
            nativeQuery = true
    )
    Optional<Complaint> findComplaintByQmsNo(String qmsNumber);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE " +
                    " FROM complaint " +
                    " WHERE id = ?1",
            nativeQuery = true
    )
    int deleteComplaintById(Long id);
}
