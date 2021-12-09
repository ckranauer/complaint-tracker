package com.compalinttracker.claimdb.complaint;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findComplaintById(Long id);
}
