package com.compalinttracker.claimdb.userRole;

import com.compalinttracker.claimdb.userProfile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findAll();

    Optional<UserRole> findRoleById(Long id);

    @Query("Select u From UserRole u WHERE u.id = ?1")
    Optional<UserRole> findRoleByIdOpt(Long id);

    @Query("SELECT u FROM UserRole u WHERE u.role = ?1")
    Optional<UserRole> findUserRoleByRoleName(String roleName);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserRole u WHERE u.id = ?1")
    int deleteUserRoleById(Long id);

    @Query(
            value = "SELECT user_profile_id " +
                    "FROM user_role " +
                    "JOIN user_role_link ON user_role.id = user_role_link.user_role_id "+
                    "WHERE user_role_id = ?1",
            nativeQuery = true
    )
    List<Long> findAllUserIdUsingthisRole(Long roleId);

    @Query(
            value = "SELECT * " +
                    "FROM user_profile " +
                    "JOIN user_role_link ON user_profile.id = user_role_link.user_profile_id "+
                    "JOIN user_role ON user_role.id = user_role_link.user_role_id " +
                    "WHERE user_role_id = ?1",
            nativeQuery = true
    )
    List<UserProfile> findAllUserUsethisRole(Long roleId);
}
