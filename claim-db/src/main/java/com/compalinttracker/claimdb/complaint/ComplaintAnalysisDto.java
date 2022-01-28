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
        Optional<String> getResponsible();
        Optional<String> getResponsibleId();
        Optional<String> getAnalyzedBy();
        Optional<String> getAnalyzedById();
        //Optional<String> getProductGroup();
        //Optional<String> getType();
        //Optional<String> getPartDescription();
        Optional<String> getArrivedAt();
        Optional<String> getAnalysisStartedAt();
        Optional<String> getAnalysisEndedAt();

        Optional<String> getBarcodes();
        Optional<String> getLifecycleInfo();
        Optional<String> getFaultVerification();
        Optional<String> getVisualAnalysis();
        Optional<String> getElectricalAnalysis();
        Optional<String> getConclusion();








}
