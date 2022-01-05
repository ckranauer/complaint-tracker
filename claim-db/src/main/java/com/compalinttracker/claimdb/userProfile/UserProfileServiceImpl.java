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
    private final UserRoleRepository userRoleRepository;
    private final UserRoleLinkRepository userRoleLinkRepository;


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
    public UserProfile update(UUID id, UserProfile userProfile) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(id);
        UserProfile actualUserProfile = userProfileOptional.get();
        actualUserProfile.setCreatedAt(userProfile.getCreatedAt());
        actualUserProfile.setEmail(userProfile.getEmail());
        actualUserProfile.setFirstName(userProfile.getFirstName());
        actualUserProfile.setLastName(userProfile.getLastName());
        return actualUserProfile;
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

        // Check if UserProfile exists
        Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(userRoleLinkDto.getUserId());

        if(userProfileOptional.isEmpty()){
            throw new IllegalStateException("User with "+ userRoleLinkDto.getUserId() + " id does not exists.");
        }
        UserProfile user = userProfileOptional.get();


        // Check if Role is exists
        Optional<UserRole> userRoleOptional = userRoleRepository.findRoleByIdOpt(userRoleLinkDto.getRoleId());

        if(userRoleOptional.isEmpty()){
            throw new IllegalStateException("Role with "+ userRoleLinkDto.getRoleId() + " id does not exists.");
        }
        UserRole role = userRoleOptional.get();

        // Check if the role is already added to the user
        Optional<UserRoleLink> userRoleLinkOptional = userRoleLinkRepository.findUserRoleLinkById(userRoleLinkDto.getUserId(),userRoleLinkDto.getRoleId());

        if(userRoleLinkOptional.isPresent()){
            throw new IllegalStateException( role.getRole() +" role is already added to "+ user.getEmail() + " user ...");
        }

        // Create User Role Link
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

    @Override
    public Boolean removeRoleFromUser(UserRoleLinkDto userRoleLinkDto) {

        // Check if user has the role
        Optional<UserRoleLink> userRoleLinkOptional = userRoleLinkRepository.findUserRoleLinkById(userRoleLinkDto.getUserId(),userRoleLinkDto.getRoleId());

        if(userRoleLinkOptional.isEmpty()){
            throw new IllegalStateException("User does not have this role");
        }

        userRoleLinkRepository.deleteUserRoleLinkById(userRoleLinkDto.getUserId(), userRoleLinkDto.getRoleId());
        return Boolean.TRUE;
    }
}
