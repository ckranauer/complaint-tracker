package com.compalinttracker.claimdb.userProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    List<UserProfile> findAll();

    @Query(
            value = "SELECT * " +
                    "FROM user_profile " +
                    "WHERE id = ?1",
            nativeQuery = true
    )
    Optional<UserProfile> findUserProfileByIdNative(Long id);

    @Query(
            value = "SELECT * " +
                    "FROM user_profile " +
                    "JOIN user_role_link ON user_profile.id = user_role_link.user_profile_id " +
                    "WHERE user_role_link.user_role_id = ?1",
            nativeQuery = true
    )
    List<UserProfile> findAllUserProfileUseThisRole(Long roleId);

    UserProfile findUserProfileById(Long id);

    @Query(
            value = "SELECT user_profile.id " +
                    "FROM user_profile JOIN user_role_link ON user_profile.id = user_role_link.user_profile_id " +
                    "JOIN user_role ON user_role.id = user_role_link.user_role_id "+
                    "WHERE user_role_link.user_role_id = ?1",
            nativeQuery = true
    )
    List<Long> findAllUserProfileIdWhatHasThisRoleId(Long id);

    /*
    @Query(
            value = "SELECT u FROM UserProfile u " +
                    "JOIN Analysis" +
                    "JOIN user_role ON user_role.id = user_role_link.user_role_id "+
                    "WHERE user_role_link.user_role_id = ?1",
            nativeQuery = true
    )
    List<Analysis> getUserAnalysisesById(long l);
     */

    Optional<UserProfile> findUserByEmail(String email);
}
