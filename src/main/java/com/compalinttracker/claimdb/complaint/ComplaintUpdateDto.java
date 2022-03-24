package com.compalinttracker.claimdb.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintUpdateDto {

    private Long id;
    private String serialNumber;
    private String qmsNumber;
    private String customerRefNumber;
    private String claimedFault;
    private String productInfo;

    private LocalDate arrivedAt;   // String

    // UUID
    private String responsible; // TODO: modify it to string, in this case when the frontend send null (default -> to deleteResponsibleFromComplaint),then it won't throw JSON parse error
    private Boolean isPrio;

    private String barcodes;
    private String lifecycleInfo;
    private Boolean faultVerification;
    private String visualAnalysis;
    private String electricalAnalysis;
    private String conclusion;
    private LocalDate analysisStartedAt;
    private LocalDate analysisEndedAt;
    private String analyzedBy;

}
