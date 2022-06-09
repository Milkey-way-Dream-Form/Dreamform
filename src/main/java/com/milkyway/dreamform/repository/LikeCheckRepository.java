package com.milkyway.dreamform.repository;

import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.LikeCheck;
import com.milkyway.dreamform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeCheckRepository extends JpaRepository<LikeCheck, Long> {
    Optional<LikeCheck> findByCommunityAndUser(Community community, User user);
}
