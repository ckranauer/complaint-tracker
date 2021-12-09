package com.compalinttracker.claimdb.userProfile;


import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkDto;
import org.apache.catalina.User;

import java.util.Collection;
import java.util.List;


public interface UserProfileService {

    UserProfile create(UserProfile userProfile);
    Collection<UserProfile> list(int limit);
    UserProfile get(Long id);
    UserProfile update(UserProfile userProfile);
    Boolean delete(Long id);

    //Actually we add userRoleLink to User and to Role
    Boolean addUserRoleToUserProfile(UserRoleLinkDto userRoleLinkDto);


}
