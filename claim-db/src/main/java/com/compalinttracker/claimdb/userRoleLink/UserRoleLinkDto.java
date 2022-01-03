package com.compalinttracker.claimdb.userRoleLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserRoleLinkDto {

    private UUID userId;
    private Long roleId;

}
