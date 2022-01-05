package com.compalinttracker.claimdb.userRole;

import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkRepository;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

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

        // Check if role exists
        Optional<UserRole> userRoleOptional = userRoleRepository.findRoleById(userRole.getId());
        if(userRoleOptional.isPresent()){
            throw new IllegalStateException(String.format("User role already exists"));
        }
        if(userRole.getRole() == null ){
            throw new IllegalStateException(String.format("User role name can't be null."));
        }
        if(userRole.getDescription() == null){
            throw new IllegalStateException(String.format("User role description can't be null."));
        }
        return userRoleRepository.save(userRole);
    }

    @Override
    public Collection<UserRole> list(int limit, int page) {

        return null;
    }

    @Override
    public UserRole get(Long id) {
        log.info("Fetching user role by id: {}", id);
        return userRoleRepository.findRoleById(id).get();
    }

    @Override
    public UserRole update(Long id, UserRole userRole) {
        // Check if role exists
        Optional<UserRole> userRoleOptional = userRoleRepository.findRoleById(userRole.getId());
        if(userRoleOptional.isEmpty()){
            throw new IllegalStateException(String.format("User role does not exist."));
        }
        UserRole actualRole = userRoleOptional.get();
        actualRole.setRole(userRole.getRole());
        actualRole.setDescription((userRole.getDescription()));
        return actualRole;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting user role by id: {}", id);
        Optional<UserRole> userRoleOptional = userRoleRepository.findRoleById(id);
        if(userRoleOptional.isEmpty()){
            throw new IllegalStateException(String.format("Role with "+id +" id does not exist."));
        }
        // first must to delete user role link
        // TODO: solve it with UserRoleLinkServiceImpl
        userRoleLinkRepository.deleteUserRoleLinkById(id);
        //userRoleLinkService.delete(id);
        //userRoleLinkRepository.deleteUserRoleLinkById(id);
        userRoleRepository.deleteUserRoleById(id);
        return Boolean.TRUE;
    }
}
