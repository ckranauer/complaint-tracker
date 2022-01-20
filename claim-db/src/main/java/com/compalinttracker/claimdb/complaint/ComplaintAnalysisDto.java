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
        Optional<String> getSerial_number();
        Optional<String> getQms_number();
        Optional<String> getCustomer_ref_number();
        Optional<String> getClaimed_fault();
        Optional<String> getAnalyzed_by();
        Optional<String> getArrived_at();
        Optional<String> getAnalysis_started_at();
        Optional<String> getAnalysis_ended_at();




}
