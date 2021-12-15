package com.compalinttracker.claimdb.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {

    private String serialNumber;
    private String qmsNumber;
    private String customerRefNumber;
    private String claimedFault;
    private LocalDateTime arrivedAt;
    private long responsible;
    private Boolean isPrio;
}
