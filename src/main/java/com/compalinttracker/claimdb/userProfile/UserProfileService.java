package com.compalinttracker.claimdb.userProfile;


import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkDto;
import org.apache.catalina.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


public interface UserProfileService {

    Collection<UserProfile> create(UserProfile userProfile);

    Collection<UserProfile> list(int limit, int page);

    UserProfile get(UUID id);

    Collection<UserProfile> update(UUID id, UserProfile userProfile);

    Boolean delete(UUID id);

    //Actually we add userRoleLink to User and to Role
    Boolean addUserRoleToUserProfile(UserRoleLinkDto userRoleLinkDto);

    Boolean removeRoleFromUser(UserRoleLinkDto userRoleLinkDto);


}
