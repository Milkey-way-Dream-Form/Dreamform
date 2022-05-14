package com.milkyway.dreamform.repository;

import com.milkyway.dreamform.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommunityId(Long communityId);
}
