package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.ReplyRequestDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.Reply;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.CommunityRepository;
import com.milkyway.dreamform.repository.ReplyRepository;
import com.milkyway.dreamform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public void saveReply(Community community, ReplyRequestDto replyRequestDto, User user) {
        Reply reply = Reply.builder().comment(replyRequestDto.getComment()).build();
        Reply replyCo = replyRepository.save(reply);
        community.addReply(replyCo);
        user.addReply(replyCo);
    }

    @Transactional
    public void updateReply(Long replyId, String comment) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        reply.updateReply(comment);
    }

    @Transactional
    public void deleteReply(Long replyId, Long communityId, Long userId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        replyRepository.deleteById(reply.getId());
        community.deleteReply(reply);
        user.deleteReply(reply);
    }
}