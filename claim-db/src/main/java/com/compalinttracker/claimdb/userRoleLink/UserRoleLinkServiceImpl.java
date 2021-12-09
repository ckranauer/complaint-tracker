package com.compalinttracker.claimdb.userRoleLink;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserRoleLinkServiceImpl implements  UserRoleLinkService{

    private final UserRoleLinkRepository userRoleLinkRepository;

    @Override
    public Boolean delete(Long id) {
        userRoleLinkRepository.deleteUserRoleLinkById(id);
        return Boolean.TRUE;
    }
}
