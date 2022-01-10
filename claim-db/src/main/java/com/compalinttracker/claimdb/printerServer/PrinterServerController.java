package com.compalinttracker.claimdb.printerServer;

import com.compalinttracker.claimdb.response.Response;
import com.compalinttracker.claimdb.userProfile.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/printer-server")
@RequiredArgsConstructor
public class PrinterServerController {

    private final PrinterServerImpl printerServerService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid PrinterServer printerServer){

        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("printer", printerServerService.create(printerServer)))
                            .message("Printer server created")
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .build()
            );
        }catch(IllegalStateException exception){
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
    public ResponseEntity<Response> getServer(@PathVariable ("id") long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("server", printerServerService.get(id)))
                            .message("Server retrieved")
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

    @GetMapping("/ping/{id}")
    public ResponseEntity<Response> pingServer(@PathVariable ("id") long id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("server", printerServerService.ping(id)))
                            .message("Server is running")
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


}
