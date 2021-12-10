package com.compalinttracker.claimdb.analysis;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
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
    private long analyzedBy;

}
