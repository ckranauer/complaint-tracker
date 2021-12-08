package com.compalinttracker.claimdb.analysis;

import com.compalinttracker.claimdb.complaint.Complaint;
import com.compalinttracker.claimdb.userProfile.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Analysis")
@Data
@NoArgsConstructor
@Table(
        name = "analysis"
)
public class Analysis {

    @Id
    private Long id;

    /*
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "complaint_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "complaint_id_fk"
            )
    )*/
    @OneToOne
    @MapsId
    private Complaint complaint;

    @Column(
            name = "barcodes",
            columnDefinition = "TEXT"
    )
    private String barcodes;

    @Column(
            name = "lifecycle_info",
            columnDefinition = "TEXT"
    )
    private String lifecycleInfo;

    @Column(
            name = "fault_verification"
    )
    private boolean faultVerification;

    @Column(
            name = "visual_analysis",
            columnDefinition = "TEXT"
    )
    private String visualAnalysis;

    @Column(
            name = "electrical_analysis",
            columnDefinition = "TEXT"
    )
    private String electricalAnalysis;

    @Column(
            name = "conclusion",
            columnDefinition = "TEXT"
    )
    private String conclusion;

    @Column(
            name = "analysis_started_at",
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime analysisStartedAt;

    @Column(
            name = "analysis_ended_at",
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime analysisEndedAt;


    @ManyToOne
    @JoinColumn(
            name = "user_profile_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_profile_analysis_fk"
            )
    )
    private UserProfile analyzedBy;


    public Analysis(
            String barcodes,
            String lifecycleInfo,
            boolean faultVerification,
            String visualAnalysis,
            String electricalAnalysis,
            String conclusion,
            LocalDateTime analysisStartedAt,
            LocalDateTime analysisEndedAt,
            UserProfile analyzedBy) {
        this.barcodes = barcodes;
        this.lifecycleInfo = lifecycleInfo;
        this.faultVerification = faultVerification;
        this.visualAnalysis = visualAnalysis;
        this.electricalAnalysis = electricalAnalysis;
        this.conclusion = conclusion;
        this.analysisStartedAt = analysisStartedAt;
        this.analysisEndedAt = analysisEndedAt;
        this.analyzedBy = analyzedBy;
    }


    public Analysis(Complaint complaint,
                    String barcodes,
                    String lifecycleInfo,
                    boolean faultVerification,
                    String visualAnalysis,
                    String electricalAnalysis,
                    String conclusion,
                    LocalDateTime analysisStartedAt,
                    LocalDateTime analysisEndedAt,
                    UserProfile analyzedBy) {
        this.complaint = complaint;
        this.barcodes = barcodes;
        this.lifecycleInfo = lifecycleInfo;
        this.faultVerification = faultVerification;
        this.visualAnalysis = visualAnalysis;
        this.electricalAnalysis = electricalAnalysis;
        this.conclusion = conclusion;
        this.analysisStartedAt = analysisStartedAt;
        this.analysisEndedAt = analysisEndedAt;
        this.analyzedBy = analyzedBy;
    }
}
