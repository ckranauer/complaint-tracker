package com.compalinttracker.claimdb.userRoleLink;


import com.compalinttracker.claimdb.userProfile.UserProfile;
import com.compalinttracker.claimdb.userRole.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "UserRoleLink")
@Data
@NoArgsConstructor
@Table(name = "user_role_link")
public class UserRoleLink  {

    @EmbeddedId
    private UserRoleLinkId id;


    @ManyToOne
    @MapsId("userRoleId")
    @JoinColumn(
            name = "user_role_id",
            foreignKey = @ForeignKey(
                    name = "user_role_link_role_id_fk"
            )
    )
    private UserRole userRole;


    @ManyToOne
    @MapsId("userProfileId")
    @JoinColumn(
            name = "user_profile_id",
            foreignKey = @ForeignKey(
                    name = "user_role_link_profile_id_fk"
            )
    )
    private UserProfile userProfile;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    public UserRoleLink(UserRoleLinkId id, UserRole userRole, UserProfile userProfile, LocalDateTime createdAt) {
        this.id = id;
        this.userRole = userRole;
        this.userProfile = userProfile;
        this.createdAt = createdAt;
    }

    public UserRoleLink(UserRole userRole, UserProfile userProfile, LocalDateTime createdAt) {
        this.userRole = userRole;
        this.userProfile = userProfile;
        this.createdAt = createdAt;
    }
}
