package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.userProfile.UserProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Complaint")
@Data
@NoArgsConstructor
@Table(
        name = "complaint"
)
public class Complaint  {       //implements Serializable

    @Id
    @SequenceGenerator(                                 // BIGSERIAL data type
            name = "complaint_sequence",
            sequenceName =  "complaint_sequence",
            allocationSize = 1                          // how much a sequence increase from
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "complaint_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "serial_number",
            nullable = false,
            unique = true
    )
    private String serialNumber;

    @Column(
            name = "qms_number",
            unique = true
    )
    private String qmsNumber;

    @Column(
            name = "customer_ref_number"
    )
    private String customerRefNumber;

    @Column(
            name = "claimed_fault",
            columnDefinition = "TEXT"
    )
    private String claimedFault;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;


    @Column(
            name = "arrived_at",
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDate arrivedAt;


    @ManyToOne
    @JoinColumn(
            name = "user_profile_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_profile_complaint_fk"
            )
    )
    private UserProfile responsible;

    // private String partDescription;

    private String productInfo;

    @Column(
            name = "is_prio",
            columnDefinition = "boolean default false"
    )
    private boolean isPrio;

   // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference(value="complaint-analysis")
    @OneToOne(
            mappedBy = "complaint",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY      // Default is eager

    )
    @PrimaryKeyJoinColumn
    private Analysis analysis;

    public Complaint(String serialNumber,
                     String qmsNumber,
                     String customerRefNumber,
                     String claimedFault,
                     LocalDate arrivedAt,
                     UserProfile responsible,
                     boolean isPrio) {
        this.serialNumber = serialNumber;
        this.qmsNumber = qmsNumber;
        this.customerRefNumber = customerRefNumber;
        this.claimedFault = claimedFault;
        this.createdAt = LocalDateTime.now();
        this.arrivedAt = arrivedAt;
        this.responsible = responsible;
        this.isPrio = isPrio;
    }

    public Complaint(String serialNumber,
                     String qmsNumber,
                     String customerRefNumber,
                     String claimedFault,
                     LocalDate arrivedAt,
                     boolean isPrio) {
        this.serialNumber = serialNumber;
        this.qmsNumber = qmsNumber;
        this.customerRefNumber = customerRefNumber;
        this.claimedFault = claimedFault;
        this.createdAt = LocalDateTime.now();
        this.arrivedAt = arrivedAt;
        this.isPrio = isPrio;
    }
}
