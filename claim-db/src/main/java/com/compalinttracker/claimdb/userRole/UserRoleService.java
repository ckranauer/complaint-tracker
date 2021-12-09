package com.compalinttracker.claimdb.userRole;



import java.util.Collection;

public interface UserRoleService {

    UserRole create(UserRole userRole);
    Collection<UserRole> list(int limit);
    UserRole get(Long id);
    UserRole update(UserRole userRole);
    Boolean delete(Long id);
}
