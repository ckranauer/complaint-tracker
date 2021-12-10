package com.compalinttracker.claimdb.complaint;

import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;
import com.compalinttracker.claimdb.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add-analysis")
    public ResponseEntity<Response> addAnalysis(@RequestBody @Valid AnalysisDto analysisDto){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("analysis", complaintService.addAnalysis(analysisDto)))
                        .message("Analysis added to complaint")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getComplaint(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("complaint", complaintService.get(id)))
                        .message("Complaint retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getComplaints(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("complaints", complaintService.list(50)))
                        .message("Complaints retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteComplaint(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("complaint", complaintService.delete(id)))
                        .message("Complaint deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()

        );
    }
}
