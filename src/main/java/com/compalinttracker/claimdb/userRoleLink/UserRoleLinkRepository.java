package com.compalinttracker.claimdb.userRoleLink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleLinkRepository extends JpaRepository<UserRoleLink, Long> {

    @Query(
            value = "SELECT *" +
                    " FROM user_role_link " +
                    " WHERE user_role_link.user_role_id = ?2 " +
                    " AND user_role_link.user_profile_id = ?1",
            nativeQuery = true
    )
    Optional<UserRoleLink> findUserRoleLinkById(UUID userId, Long roleId);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE " +
                    " FROM user_role_link " +
                    " WHERE user_role_link.user_role_id = ?2 " +
                    " AND user_role_link.user_profile_id = ?1",
            nativeQuery = true
    )
    int deleteUserRoleLinkById(UUID userId, Long longId);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE " +
                    " FROM user_role_link " +
                    " WHERE user_role_link.user_role_id = ?1 ",
            nativeQuery = true
    )
    int deleteUserRoleLinkById(Long roleId);

}
