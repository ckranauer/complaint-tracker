package com.compalinttracker.claimdb.complaint;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDto {

    //@NotBlank(message = "Serial number is mandatory")
    private String serialNumber;
    private String qmsNumber;
    private String customerRefNumber;
    private String claimedFault;
    private String productInfo;

    private String arrivedAt;
    private UUID responsible;
    private Boolean isPrio;
}
