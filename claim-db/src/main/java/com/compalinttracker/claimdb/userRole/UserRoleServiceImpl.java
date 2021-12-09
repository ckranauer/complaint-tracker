package com.compalinttracker.claimdb.userRole;

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
        return null;
    }

    @Override
    public UserRole update(UserRole userRole) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
