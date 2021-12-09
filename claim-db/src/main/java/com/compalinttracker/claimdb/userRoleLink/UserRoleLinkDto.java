package com.compalinttracker.claimdb.userRoleLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserRoleLinkDto {

    private Long userId;
    private Long roleId;

}
