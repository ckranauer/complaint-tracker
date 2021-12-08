package com.compalinttracker.claimdb.userProfile;


import com.compalinttracker.claimdb.userRoleLink.UserRoleLink;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "UserProfile")
@Data
@NoArgsConstructor
@Table(
        name = "user_profile",
        uniqueConstraints = {
                @UniqueConstraint(name="user_profile_email_unique", columnNames = "email")
        }
)
public class UserProfile {

    @Id
    @SequenceGenerator(
            name = "user_profile_sequence",
            sequenceName = "user_profile_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_profile_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    public UserProfile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }


    @JsonBackReference(value="user-role")
    @OneToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "userProfile"
    )
    private List<UserRoleLink> userRoleLinks = new ArrayList<>();

    /*
    @JsonBackReference(value="user-complaint")
    @OneToMany(
            mappedBy = "responsible",
            //orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},     //CascadeType.ALL ,      {CascadeType.PERSIST, CascadeType.REMOVE} ->whenever we have a book if it now save we will persist and delete all books if this student is deleted
            fetch = FetchType.LAZY// In OneToMany and ManyToOne the default fetch type is LAZY, if we set it to eager it will returns with the card and with the books also
    )
    private List<Complaint> complaints = new ArrayList<>();


    @JsonBackReference(value="user-analysis")
    @OneToMany(
            mappedBy = "analyzedBy",
            //orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},     //CascadeType.ALL ,      {CascadeType.PERSIST, CascadeType.REMOVE} ->whenever we have a book if it now save we will persist and delete all books if this student is deleted
            fetch = FetchType.LAZY// In OneToMany and ManyToOne the default fetch type is LAZY, if we set it to eager it will returns with the card and with the books also
    )
    private List<Analysis> analysises = new ArrayList<>();
    */

}
