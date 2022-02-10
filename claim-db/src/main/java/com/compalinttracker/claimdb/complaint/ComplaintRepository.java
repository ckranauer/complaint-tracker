package com.compalinttracker.claimdb.complaint;


import org.springframework.data.domain.Pageable;
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
            value = "SELECT complaint.id, " +
                    "serial_number as serialNumber, " +
                    "qms_number as qmsNumber, " +
                    "customer_ref_number as customerRefNumber, " +
                    "claimed_fault as claimedFault, " +
                    "CONCAT(user_profile.first_name, ' ', user_profile.last_name)  as responsible, " +
                    "CAST(complaint.user_profile_id AS TEXT) as responsibleId, " +
                    "is_prio as isPrio, " +
                    "CONCAT(user_profile.first_name, ' ', user_profile.last_name)  as analyzedBy, " +
                    "CAST(analysis.user_profile_id AS TEXT) as analyzedById, " +
                    "product_info as productInfo, " +
                    "arrived_at as arrivedAt, " +
                    "analysis_started_at as analysisStartedAt, " +
                    "analysis_ended_at as analysisEndedAt, " +
                    "barcodes, " +
                    "lifecycle_info as lifecycleInfo, " +
                    "visual_analysis as visualAnalysis, " +
                    "electrical_analysis as electricalAnalysis, " +
                    "conclusion " +
                    "FROM complaint " +
                    "LEFT JOIN analysis ON analysis.complaint_id = complaint.id " +
                    "LEFT JOIN user_profile ON analysis.user_profile_id = user_profile.id " +
                    "ORDER BY arrived_at DESC ",
            nativeQuery = true
    )
    List<ComplaintAnalysisDto> findAllComplaintAnalysis(Pageable pageable);



    @Query(
            value = "SELECT complaint.id, " +
                    "serial_number as serialNumber, " +
                    "qms_number as qmsNumber, " +
                    "customer_ref_number as customerRefNumber, " +
                    "claimed_fault as claimedFault, " +
                    "CONCAT(user_profile.first_name, ' ', user_profile.last_name)  as responsible, " +
                    "CAST(complaint.user_profile_id AS TEXT) as responsibleId, " +
                    "is_prio as isPrio, " +
                    "CONCAT(user_profile.first_name, ' ', user_profile.last_name)  as analyzedBy, " +
                    "CAST(analysis.user_profile_id AS TEXT) as analyzedById, " +
                    "product_info as productInfo, " +
                    "arrived_at as arrivedAt, " +
                    "analysis_started_at as analysisStartedAt, " +
                    "analysis_ended_at as analysisEndedAt, " +
                    "barcodes, " +
                    "lifecycle_info as lifecycleInfo, " +
                    "visual_analysis as visualAnalysis, " +
                    "electrical_analysis as electricalAnalysis, " +
                    "conclusion " +
                    "FROM complaint " +
                    "LEFT JOIN analysis ON analysis.complaint_id = complaint.id " +
                    "LEFT JOIN user_profile ON analysis.user_profile_id = user_profile.id " +
                    "WHERE complaint.id = ?1",
            nativeQuery = true
    )
    Optional<ComplaintAnalysisDto> findComplaintAnalysisById(Long id);

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
