package com.compalinttracker.claimdb.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    //private String prodGroup;

    private String arrivedAt;
    private UUID responsible;
    //private Boolean isPrio;

    //private long complaintId;
    private String barcodes;
    private String lifecycleInfo;
    private boolean faultVerification;
    private String visualAnalysis;
    private String electricalAnalysis;
    private String conclusion;
    private String analysisStartedAt;
    private String analysisEndedAt;
    private UUID analyzedBy;

}
