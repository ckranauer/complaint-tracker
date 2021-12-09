package com.compalinttracker.claimdb.userRole;

import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkRepository;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserRoleServiceImpl implements  UserRoleService{

    private final UserRoleRepository userRoleRepository;
    private final UserRoleLinkRepository userRoleLinkRepository;
    private final UserRoleLinkServiceImpl userRoleLinkService;

    @Override
    public UserRole create(UserRole userRole) {
        log.info("Saving new role: {}", userRole.getRole());
        return userRoleRepository.save(userRole);
    }

    @Override
    public Collection<UserRole> list(int limit) {
        return null;
    }

    @Override
    public UserRole get(Long id) {
        log.info("Fetching user role by id: {}", id);
        return userRoleRepository.findRoleById(id).get();
    }

    @Override
    public UserRole update(UserRole userRole) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting user role by id: {}", id);
        // first must to delete user role link
        // TODO: solve it with UserRoleLinkServiceImpl
        userRoleLinkService.delete(id);
        //userRoleLinkRepository.deleteUserRoleLinkById(id);
        userRoleRepository.deleteUserRoleById(id);
        return Boolean.TRUE;
    }
}
