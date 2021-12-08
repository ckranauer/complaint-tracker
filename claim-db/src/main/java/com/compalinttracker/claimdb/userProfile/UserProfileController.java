package com.compalinttracker.claimdb.userProfile;

import com.compalinttracker.claimdb.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileServiceImpl userProfileService;

    @GetMapping("list")
    public ResponseEntity<Response> getUsers(){

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("users", userProfileService.list(10)))
                        .message("Users retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserProfile userProfile){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userProfileService.create(userProfile)))
                        .message("User created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }
}
