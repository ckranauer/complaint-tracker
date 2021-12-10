package com.compalinttracker.claimdb.analysis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Analysis a WHERE a.id = ?1")
    int deleteAnalysisByComplaintId(Long id);
}
