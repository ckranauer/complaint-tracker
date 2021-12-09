package com.compalinttracker.claimdb.userRole;

import com.compalinttracker.claimdb.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

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
}
