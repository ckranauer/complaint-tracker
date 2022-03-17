package com.compalinttracker.claimdb.userProfile;

import com.compalinttracker.claimdb.paginationParam.PaginationObj;
import com.compalinttracker.claimdb.response.Response;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLink;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileServiceImpl userProfileService;

    @PostMapping("/list")
    public ResponseEntity<Response> getUsers(@RequestBody PaginationObj paginationObj){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("users", userProfileService.list(1,10)))
                            .message("Users retrieved")
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

    @PostMapping("/save")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserProfile userProfile){

        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("users", userProfileService.create(userProfile)))
                            .message("User created")
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

    @PostMapping("/add-role-to-user")
    public ResponseEntity<Response> addRoleToUser(@RequestBody @Valid UserRoleLinkDto userRoleLinkDto){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("userRoleLink", userProfileService.addUserRoleToUserProfile(userRoleLinkDto)))
                            .message("Role is added to user.")
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


    @PostMapping("/remove-role-from-user")
    public ResponseEntity<Response> removeRoleFromUser(@RequestBody @Valid UserRoleLinkDto userRoleLinkDto){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("userRoleLink", userProfileService.removeRoleFromUser(userRoleLinkDto)))
                            .message("Role is removed from user")
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

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getUser(@PathVariable ("id") UUID id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("user", userProfileService.get(id)))
                            .message("User retrieved")
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


    @PutMapping("/update")
    public ResponseEntity<Response> updateUser(@RequestBody @Valid UserProfile userProfile){

        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("users", userProfileService.update(userProfile.getId(), userProfile)))
                            .message("User updated")
                            .status(OK)
                            .statusCode(OK.value())
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable ("id") UUID id){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("user", userProfileService.delete(id)))
                            .message("User deleted")
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
}
