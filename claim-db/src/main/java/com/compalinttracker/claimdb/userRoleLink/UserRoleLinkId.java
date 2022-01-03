package com.compalinttracker.claimdb.userRoleLink;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
public class UserRoleLinkId implements Serializable {

    @Column(name = "user_role_id")
    private Long userRoleId;

    @Column(name = "user_profile_id")
    private UUID userProfileId;

    public UserRoleLinkId(Long userRoleId, UUID userProfileId) {
        this.userRoleId = userRoleId;
        this.userProfileId = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleLinkId that = (UserRoleLinkId) o;
        return Objects.equals(userRoleId, that.userRoleId) && Objects.equals(userProfileId, that.userProfileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleId, userProfileId);
    }
}
