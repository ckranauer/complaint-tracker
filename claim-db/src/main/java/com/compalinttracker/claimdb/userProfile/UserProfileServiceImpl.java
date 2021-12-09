package com.compalinttracker.claimdb.userProfile;

import com.compalinttracker.claimdb.userRole.UserRole;
import com.compalinttracker.claimdb.userRole.UserRoleRepository;
import com.compalinttracker.claimdb.userRole.UserRoleServiceImpl;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLink;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkDto;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkId;
import com.compalinttracker.claimdb.userRoleLink.UserRoleLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;
    private final UserRoleLinkRepository userRoleLinkRepository;
    private final UserRoleServiceImpl userRoleService;


    public UserProfile create(UserProfile userProfile) {
        log.info("Saving new user: {}", userProfile.getEmail());
        userProfile.setCreatedAt(LocalDateTime.now());
        return userProfileRepository.save(userProfile);
    }

    public Collection<UserProfile> list(int limit) {
        log.info("Fetching all users");
        return userProfileRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public UserProfile get(Long id) {
        log.info("Fetching user by id: {}", id);
        return userProfileRepository.findUserProfileById(id).get();
    }

    @Override
    public UserProfile update(UserProfile userProfile) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting user: {}", id);
        userProfileRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean addUserRoleToUserProfile(UserRoleLinkDto userRoleLinkDto) {
        UserProfile user = userProfileRepository.findUserProfileById(userRoleLinkDto.getUserId()).get();
        UserRole role = userRoleService.get(userRoleLinkDto.getRoleId());
        UserRoleLink userRoleLink = new UserRoleLink(
                (new UserRoleLinkId(userRoleLinkDto.getRoleId(), userRoleLinkDto.getUserId())),
                role,
                user,
                LocalDateTime.now()
        );
        user.addUserRoleLink(userRoleLink);
        role.addUserRoleLink(userRoleLink);
        return Boolean.TRUE;
    }
}
