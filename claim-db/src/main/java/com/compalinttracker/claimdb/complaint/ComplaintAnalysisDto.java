package com.compalinttracker.claimdb.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import java.util.Optional;
import java.util.UUID;


public interface ComplaintAnalysisDto {

        Optional<Long> getId();
        Optional<String> getSerialNumber();
        Optional<String> getQmsNumber();
        Optional<String> getCustomerRefNumber();
        Optional<String> getClaimedFault();
        Optional<String> getAnalyzedBy();
        Optional<String> getArrivedAt();
        Optional<String> getAnalysisStartedAt();
        Optional<String> getAnalysisEndedAt();






}
