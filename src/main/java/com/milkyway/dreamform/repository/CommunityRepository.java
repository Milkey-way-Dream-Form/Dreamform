package com.milkyway.dreamform.repository;

import com.milkyway.dreamform.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

//
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Modifying
    @Query("update Community set viewCounts = viewCounts + 1 where community_id = :community_id")
    Integer updateViewCounts(Long community_id);


}

