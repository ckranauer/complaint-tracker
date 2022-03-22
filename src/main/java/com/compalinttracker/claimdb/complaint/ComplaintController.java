package com.compalinttracker.claimdb.complaint;

import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;
import com.compalinttracker.claimdb.paginationParam.PaginationObj;
import com.compalinttracker.claimdb.response.Response;
import com.compalinttracker.claimdb.userProfile.UserProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintServiceImpl complaintService;
    private final UserProfileServiceImpl userProfileService;

    //TODO: solve the exception handling in the service layer
    // TODO: refactor methods, for example create complaint should be void and gives back only what needed
    // TODO: practice git branches


    @PostMapping("/save")
    public ResponseEntity<Response> saveComplaint(@RequestBody @Valid ComplaintDto complaintDto) {
        complaintService.create(complaintDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        //.data(Map.of("complaints", complaintService.create(complaintDto), "users", userProfileService.list(10,0)))
                        .message("Complaint created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getComplaint(@PathVariable("id") Long id) {
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

    @GetMapping("/search/{serno}")
    public ResponseEntity<Response> getComplaint(@PathVariable("serno") String serialNumber) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("complaint", complaintService.search(serialNumber), "users", userProfileService.list(10, 0)))
                        .message("Complaint found and retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getComplaints() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("complaints", complaintService.list(),
                                "users", userProfileService.list(10, 0))
                        )
                        .message("Complaints retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteComplaint(@PathVariable("id") Long id) {
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

    @PutMapping("/update")
    public ResponseEntity<Response> updateComplaint(@RequestBody ComplaintUpdateDto complaintDto) {
        complaintService.update(complaintDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        //.data(Map.of("complaints", complaintService.update(complaintDto), "users", userProfileService.list(10, 0)))
                        .message("Complaint updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @GetMapping("/create-report/{id}")
    public ResponseEntity<Response> createAnalysisReport(@PathVariable("id") Long id) throws Exception {
        complaintService.createAnalysisReport(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        //.data(Map.of("report", complaintService.createAnalysisReport(id)))
                        .message("Analysis report is created")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // print label for an already saved claim
    @GetMapping("/print-label-from-saved-complaint/{id}")
    public ResponseEntity<Response> printSavedLabel(@PathVariable("id") Long id) throws IOException {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("print", complaintService.printSavedLabel(id)))
                        .message("Label is printed.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // print label without saving the datas
    @PostMapping("/print-label")
    public ResponseEntity<Response> printLabel(@RequestBody @Valid ComplaintDto complaintDto) throws IOException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("print", complaintService.printLabel(complaintDto)))
                        .message("Label printed")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }
}
