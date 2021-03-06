package com.compalinttracker.claimdb.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

//  Projection interface to retrieve a subset of attributes
//  Info is from here https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections


public interface ComplaintAnalysisDto {

    Optional<Long> getId();

    Optional<String> getSerialNumber();

    Optional<String> getQmsNumber();

    Optional<String> getCustomerRefNumber();

    Optional<String> getClaimedFault();

    Optional<String> getResponsible();

    Optional<String> getResponsibleId();

    Optional<Boolean> getIsPrio();

    Optional<String> getAnalyzedBy();

    Optional<String> getAnalyzedById();

    Optional<String> getProductInfo();

    //Optional<String> getType();
    //Optional<String> getPartDescription();
    Optional<String> getArrivedAt();

    Optional<String> getAnalysisStartedAt();

    Optional<String> getAnalysisEndedAt();

    Optional<String> getBarcodes();

    Optional<String> getLifecycleInfo();

    Optional<Boolean> getFaultVerification();

    Optional<String> getVisualAnalysis();

    Optional<String> getElectricalAnalysis();

    Optional<String> getConclusion();


}
