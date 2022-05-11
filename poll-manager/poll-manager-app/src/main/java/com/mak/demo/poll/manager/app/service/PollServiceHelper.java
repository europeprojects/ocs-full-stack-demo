package com.mak.demo.poll.manager.app.service;

import com.mak.demo.poll.manager.app.model.email.domain.EmailRequest;
import com.mak.demo.poll.manager.app.model.response.ChoiceResponse;
import com.mak.demo.poll.manager.app.model.response.PollSummaryResponse;
import com.mak.demo.poll.manager.port.domain.Poll;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PollServiceHelper {


    public  PollSummaryResponse buildPollResponse(Poll poll, Map<Long, Long> choiceVotes, Long userVote) {
        PollSummaryResponse response = PollSummaryResponse
                .builder().id(poll.getId())
                .question(poll.getQuestion())
                .username(poll.getUsername())
                .email(poll.getEmail())
                .selectedChoice(userVote)
                .isActive(poll.getIsActive())
                .updateTime(poll.getUpdatedAt())
                .build();


        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = ChoiceResponse.builder()
                    .id(choice.getId())
                    .voteCount(choiceVotes.containsKey(choice.getId()) ? choiceVotes.get(choice.getId()) : 0L)
                    .text(choice.getText()).build();

            return choiceResponse;
        }).collect(Collectors.toList());

        response.setChoices(choiceResponses);
        response.setTotalVotes(choiceResponses
                .stream().mapToLong(ChoiceResponse::getVoteCount).sum());

        return response;
    }




}
