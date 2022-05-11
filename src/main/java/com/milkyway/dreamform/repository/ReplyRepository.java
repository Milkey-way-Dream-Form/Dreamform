package com.milkyway.dreamform.repository;

import com.milkyway.dreamform.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommunityId(Long communityId);
    List<Reply> findAllByCommunityId(Long id);
}
