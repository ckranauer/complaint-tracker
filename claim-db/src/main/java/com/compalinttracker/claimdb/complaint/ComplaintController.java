package com.compalinttracker.claimdb.complaint;

import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;
import com.compalinttracker.claimdb.paginationParam.PaginationObj;
import com.compalinttracker.claimdb.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @PostMapping("/save")
    public ResponseEntity<Response> saveComplaint(@RequestBody @Valid ComplaintDto complaintDto){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("complaint", complaintService.create(complaintDto)))
                            .message("Complaint created")
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    @PostMapping("/add-analysis")
    public ResponseEntity<Response> addAnalysis(@RequestBody @Valid AnalysisDto analysisDto){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("analysis", complaintService.addAnalysis(analysisDto)))
                            .message("Analysis added to complaint")
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getComplaint(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("complaint", complaintService.get(id)))
                            .message("Complaint retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    @GetMapping("/get-analysis/{id}")
    public ResponseEntity<Response> getAnalysis(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("analysis", complaintService.get(id).getAnalysis()))
                            .message("Analysis retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    //@GetMapping("/list")
    @PostMapping("/list")
    public ResponseEntity<Response> getComplaints(@RequestBody PaginationObj paginationObj){
        // TODO: send page and limit via the request instead of hardcoded -> Post request, request body contains the limit and page
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("complaints", complaintService.list(paginationObj.getLimit(),paginationObj.getPage())))
                            .message("Complaints retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteComplaint(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("complaint", complaintService.delete(id)))
                            .message("Complaint deleted")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()

            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateComplaint(@RequestBody @Valid ComplaintDto complaintDto,
                                                    @PathVariable ("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("complaint", complaintService.update(id, complaintDto)))
                            .message("Complaint updated")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    @PutMapping("/update-analysis/{id}")
    public ResponseEntity<Response> updateAnalysis(@RequestBody @Valid AnalysisDto analysisDto,
                                                    @PathVariable ("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("analysis", complaintService.update(id, analysisDto)))
                            .message("Analysis updated")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    @DeleteMapping("/delete-analysis/{id}")
    public ResponseEntity<Response> deleteAnalysis(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("complaint", complaintService.deleteAnalysis(id)))
                            .message("Analysis deleted")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()

            );
        }catch (Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }



    @GetMapping("/create-report/{id}")
    public ResponseEntity<Response> createAnalysisReport(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("report", complaintService.createAnalysisReport(id)))
                            .message("Analysis report is created")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    // print label for an already saved claim
    @GetMapping("/print-label-from-saved-complaint/{id}")
    public ResponseEntity<Response> printSavedLabel(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("print", complaintService.printSavedLabel(id)))
                            .message("Label is printed.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

    // print label without saving the datas
    @PostMapping("/print-label")
    public ResponseEntity<Response> printLabel(@RequestBody @Valid ComplaintDto complaintDto){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("print", complaintService.printLabel(complaintDto)))
                            .message("Label printed")
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .build()
            );
        }catch(Exception exception){
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .message(exception.getMessage())
                            .status(FORBIDDEN)
                            .statusCode(FORBIDDEN.value())
                            .build()
            );
        }
    }

}
