package com.compalinttracker.claimdb.userRole;

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
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleServiceImpl userRoleService;


    @PostMapping("/save")
    public ResponseEntity<Response> saveRole(@RequestBody @Valid UserRole userRole) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("role", userRoleService.create(userRole)))
                            .message("Role created")
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .build()
            );
        } catch (Exception exception) {
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

    // Endpoint for the admin page, admin can see roles
    @GetMapping("list")
    public ResponseEntity<Response> getRoles() {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("roles", userRoleService.list(1, 10)))
                            .message("Roles retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        } catch (Exception exception) {
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
    public ResponseEntity<Response> getRole(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("role", userRoleService.get(id)))
                            .message("Role retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        } catch (Exception exception) {
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
    public ResponseEntity<Response> deleteRole(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("role", userRoleService.delete(id)))
                            .message("Role deleted")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        } catch (Exception exception) {
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
    public ResponseEntity<Response> updateRole(@RequestBody @Valid UserRole userRole,
                                               @PathVariable("id") Long id) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(Map.of("role", userRoleService.update(id, userRole)))
                            .message("Role updated")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        } catch (IllegalStateException exception) {
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
