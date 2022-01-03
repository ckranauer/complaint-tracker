package com.compalinttracker.claimdb.analysis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisDto {

    private long complaintId;
    private String barcodes;
    private String lifecycleInfo;
    private boolean faultVerification;
    private String visualAnalysis;
    private String electricalAnalysis;
    private String conclusion;
    private LocalDateTime analysisStartedAt;
    private LocalDateTime analysisEndedAt;
    private UUID analyzedBy;

}
