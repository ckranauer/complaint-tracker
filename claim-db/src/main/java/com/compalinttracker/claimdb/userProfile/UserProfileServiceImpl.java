package com.compalinttracker.claimdb.userProfile;

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
        return null;
    }

    @Override
    public UserProfile update(UserProfile userProfile) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
