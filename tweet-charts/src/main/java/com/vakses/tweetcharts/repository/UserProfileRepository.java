package com.vakses.tweetcharts.repository;

import com.vakses.tweetcharts.model.UserProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by veraxmedax on 09/10/2018.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findTop10ByUsername(final String username, Pageable pageable);
}
