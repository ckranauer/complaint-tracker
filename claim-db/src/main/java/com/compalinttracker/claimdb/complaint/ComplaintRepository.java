package com.compalinttracker.claimdb.complaint;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

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
