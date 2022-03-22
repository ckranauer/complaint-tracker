package com.compalinttracker.claimdb.userRole;


import java.util.Collection;

public interface UserRoleService {

    UserRole create(UserRole userRole);

    Collection<UserRole> list(int limit, int page);

    UserRole get(Long id);

    UserRole update(Long id, UserRole userRole);

    Boolean delete(Long id);
}
