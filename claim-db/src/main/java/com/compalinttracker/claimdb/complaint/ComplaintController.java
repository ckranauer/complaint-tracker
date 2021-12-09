package com.compalinttracker.claimdb.complaint;

import com.compalinttracker.claimdb.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintServiceImpl complaintService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveComplaint(@RequestBody @Valid Complaint complaint){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("complaint", complaintService.create(complaint)))
                        .message("Complaint created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }
}
