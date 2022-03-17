package com.compalinttracker.claimdb.userRole;


import com.compalinttracker.claimdb.userRoleLink.UserRoleLink;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "UserRole")
@Data
@NoArgsConstructor
@Table(name = "user_role")
public class UserRole {

    @Id
    @SequenceGenerator(
            name = "user_role_sequence",
            sequenceName = "user_role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_role_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "role",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String role;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;


    @JsonBackReference(value="role-user")
    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "userRole"  // in the enrolment class this is this property : private Student student;
    )
    private List<UserRoleLink> userRoleLinks = new ArrayList<>();


    public UserRole(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public void addUserRoleLink(UserRoleLink userRoleLink){
        if(!userRoleLinks.contains(userRoleLink)){
            userRoleLinks.add(userRoleLink);
        }
    }

    public void removeUserRoleLink(UserRoleLink userRoleLink){
        userRoleLinks.remove(userRoleLink);
    }

}
