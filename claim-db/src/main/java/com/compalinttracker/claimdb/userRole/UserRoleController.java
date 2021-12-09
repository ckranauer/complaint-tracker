package com.compalinttracker.claimdb.userRole;

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
@RequestMapping("/role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleServiceImpl userRoleService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveRole(@RequestBody @Valid UserRole userRole){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("role", userRoleService.create(userRole)))
                        .message("Role created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getRole(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("role", userRoleService.get(id)))
                        .message("Role retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteRole(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("role", userRoleService.delete(id)))
                        .message("Role deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
