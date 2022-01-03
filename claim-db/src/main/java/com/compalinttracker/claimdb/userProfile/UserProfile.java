package com.compalinttracker.claimdb.userProfile;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.complaint.Complaint;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLink;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    /*
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
    )*/
    @Id
    private UUID id;

    @NotBlank
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @NotBlank
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @NotBlank
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

    public UserProfile(UUID id, String firstName, String lastName, String email) {
        this.id = id;
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

    public void addUserRoleLink(UserRoleLink userRoleLink){
        if(!userRoleLinks.contains(userRoleLink)){
            userRoleLinks.add(userRoleLink);
        }
    }

    public void addComplaint(Complaint complaint){
        if(!this.complaints.contains(complaint)){
            this.complaints.add(complaint);
            complaint.setResponsible(this);   // it keeps both way sync
        }
    }

    public void removeComplaint(Complaint complaint){
        if(!this.complaints.contains(complaint)){
            this.complaints.remove(complaint);
            complaint.setResponsible(null);
        }
    }

    public void addAnalysis(Analysis analysis){
        System.out.println("Add analysis method");
        if(!this.analysises.contains(analysis)){
            System.out.println("inside the if");
            this.analysises.add(analysis);
            System.out.println("analysis added to analysises");
            analysis.setAnalyzedBy(this);
            System.out.println("analysis set analyzed by this");
        }
    }

    public void removeAnalysis(Analysis analysis){
        if(!this.analysises.contains(analysis)){
            this.analysises.remove(analysis);
            analysis.setAnalyzedBy(null);
        }
    }

    public void removeUserRoleLink(UserRoleLink userRoleLink){
        userRoleLinks.remove(userRoleLink);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(createdAt, that.createdAt) && Objects.equals(userRoleLinks, that.userRoleLinks) && Objects.equals(complaints, that.complaints) && Objects.equals(analysises, that.analysises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, createdAt, userRoleLinks, complaints, analysises);
    }
}
