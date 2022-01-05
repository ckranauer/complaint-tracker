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
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;
    private final UserRoleServiceImpl userRoleService;


    public UserProfile create(UserProfile userProfile) {

        // Check if the user profile with this email is exists
        Optional<UserProfile> userProfileOptional = userProfileRepository.selectUserProfileByEmail(userProfile.getEmail());

        // If taken, check if it belongs to same user
        if(userProfileOptional.isPresent()){
            UserProfile existingUserProfile = userProfileOptional.get();

            if(existingUserProfile.getFirstName().equals(userProfile.getFirstName()) && existingUserProfile.getLastName().equals(userProfile.getLastName())){
                throw new IllegalStateException(String.format("User profile is already exists."));
            }
            throw new IllegalStateException(String.format("email "+ userProfile.getEmail() + " is taken"));
        }

        if(userProfile.getId() == null){
            userProfile.setId(UUID.randomUUID());
        }

        userProfile.setCreatedAt(LocalDateTime.now());
        log.info("Saving new user: {}", userProfile.getEmail());
        return userProfileRepository.save(userProfile);
    }

    public Collection<UserProfile> list(int limit, int page) {
        log.info("Fetching all users");
        return userProfileRepository.findAll();
        //return userProfileRepository.findAll(PageRequest.of(page, limit)).toList();
    }

    @Override
    public UserProfile get(UUID id) {

        if(id == null){
            throw new IllegalStateException(String.format("id is null"));
        }
        log.info("Fetching user by id: {}", id);
        return userProfileRepository.findUserProfileById(id).get();
    }

    @Override
    public UserProfile update(UserProfile userProfile) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        log.info("Deleting user: {}", id);
        // Check if user profile is exists
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findUserProfileById(id);

        if(optionalUserProfile.isEmpty()){
            throw new IllegalStateException("User with "+ id + " does not exists.");
        }

        if(userProfileRepository.deleteUserProfileById(id) == 0){
            throw new IllegalStateException("User with "+ id + " cannot be deleted.");
        }
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
