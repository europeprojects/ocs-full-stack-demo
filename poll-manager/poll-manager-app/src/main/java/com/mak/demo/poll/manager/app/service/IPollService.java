package com.mak.demo.poll.manager.app.service;



import com.mak.demo.poll.manager.app.auth.PollUser;
import com.mak.demo.poll.manager.app.model.request.PollRequest;
import com.mak.demo.poll.manager.app.model.response.PagedResponse;
import com.mak.demo.poll.manager.app.model.response.PollResponse;
import com.mak.demo.poll.manager.app.model.response.PollSummaryResponse;
import org.springframework.stereotype.Component;



public interface IPollService {

    PagedResponse<PollSummaryResponse> getAllActivePolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getPolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getProposalPolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getAllPassivePolls(PollUser currentUser, int page, int size);
    PagedResponse<PollSummaryResponse> getPollsForEndUser(PollUser currentUser, int page, int size);
    PollResponse createPoll(PollUser currentUser, PollRequest pollRequest);
    PollResponse proposePoll(PollUser currentUser, PollRequest pollRequest);
    PollSummaryResponse getPollById(Long pollId, PollUser currentUser);
    void voted(Long pollId, Long choiceId, PollUser currentUser);
    void deletePoll(Long id);
    void doPassivePoll(Long id);
    void confirmPoll(Long id);

}

