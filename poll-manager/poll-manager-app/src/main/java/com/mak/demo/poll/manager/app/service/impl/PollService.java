package com.mak.demo.poll.manager.app.service.impl;

import com.mak.demo.poll.manager.app.auth.PollUser;
import com.mak.demo.poll.manager.app.exception.ResourceNotFoundException;
import com.mak.demo.poll.manager.app.mapper.PollDTOMapper;
import com.mak.demo.poll.manager.app.model.request.PollRequest;
import com.mak.demo.poll.manager.app.model.response.PagedResponse;
import com.mak.demo.poll.manager.app.model.response.PollResponse;
import com.mak.demo.poll.manager.app.model.response.PollSummaryResponse;
import com.mak.demo.poll.manager.app.service.BaseService;
import com.mak.demo.poll.manager.app.service.IPollService;
import com.mak.demo.poll.manager.app.service.PollServiceHelper;
import com.mak.demo.poll.manager.port.adapter.BaseAdapter;
import com.mak.demo.poll.manager.port.adapter.PollAdapter;
import com.mak.demo.poll.manager.port.adapter.VoteAdapter;
import com.mak.demo.poll.manager.port.domain.Choice;
import com.mak.demo.poll.manager.port.domain.Poll;
import com.mak.demo.poll.manager.port.domain.Vote;
import com.mak.demo.poll.manager.port.domain.projection.ChoiceVotes;
import com.mak.demo.poll.manager.port.filter.PollFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PollService extends BaseService<Poll, PollFilter> implements IPollService {

    private final PollAdapter pollAdapter;

    private final VoteAdapter voteAdapter;

    private final PollDTOMapper pollDTOMapper;

    private final PollServiceHelper pollServiceHelper;

    private final EmailNotificationProviderService emailNotificationProviderService;

    @Override
    public BaseAdapter<Poll, PollFilter> getRepository() {return pollAdapter;}


    @Override
    public PagedResponse<PollSummaryResponse> getAllActivePolls(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateTime");
        Page<Poll> polls = pollAdapter.findByIsActiveTrue(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getUsername(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(),
                polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollSummaryResponse> getPolls(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");
        Page<Poll> polls = pollAdapter.findByIsApprovedTrue(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getUsername(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(),
                polls.getTotalPages(), polls.isLast());
    }

    private List<PollSummaryResponse> createPollResponses(String username, Page<Poll> polls) {
        List<Long> pollIds = polls.map(Poll::getId).getContent();
        Map<Long, Long> choiceVotes = getChoiceVoteCountMap(pollIds);
        Map<Long, Long> pollUserVotes = getPollUserVoteMap(username, pollIds);

        List<PollSummaryResponse> pollResponses = polls.map(poll ->
                pollServiceHelper.buildPollResponse(poll,choiceVotes,pollUserVotes.get(poll.getId()))).getContent();

        return pollResponses;
    }


    private Map<Long, Long> getChoiceVoteCountMap(List<Long> pollIds) {

        return voteAdapter.countByPollIdInGroupByChoiceId(pollIds).stream()
                .collect(Collectors.toMap(ChoiceVotes::getChoiceId, ChoiceVotes::getVoteCount));
    }

    private Map<Long, Long> getPollUserVoteMap(String username, List<Long> pollIds) {
        return voteAdapter.findByUserIdAndPollIdIn(username, pollIds).stream()
                .collect(Collectors.toMap(vote -> vote.getPoll().getId(), vote -> vote.getChoice().getId()));
    }

    @Override
    public PagedResponse<PollSummaryResponse> getProposalPolls(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");
        Page<Poll> polls = pollAdapter.findByIsApprovedFalse(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getUsername(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(),
                polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollSummaryResponse> getAllPassivePolls(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");
        Page<Poll> polls = pollAdapter.findByIsActiveFalse(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getUsername(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(),
                polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PagedResponse<PollSummaryResponse> getPollsForEndUser(PollUser currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");
        Page<Poll> polls = pollAdapter.findByIsApprovedTrueAndIsActiveTrue(pageable);

        List<PollSummaryResponse> pollResponses = createPollResponses(currentUser.getUsername(),polls);

        return new PagedResponse<>(pollResponses, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(),
                polls.getTotalPages(), polls.isLast());
    }

    @Override
    public PollResponse createPoll(PollUser currentUser, PollRequest pollRequest) {
        Poll poll = Poll.builder().question(pollRequest.getQuestion())
                .isApproved(true).isActive(true).email(currentUser.getEmail()).username(currentUser.getUsername())
                .choices( pollRequest.getChoices().stream()
                        .map(item-> Choice.builder().text(item.getText()).build())
                        .collect(Collectors.toList()))
                .build();

        return PollResponse.builder()
                .pollDto(pollDTOMapper.toDTO(pollAdapter.save(poll)))
                .success(true).build();
    }

    @Override
    public PollResponse proposePoll(PollUser currentUser, PollRequest pollRequest) {
        Poll poll = Poll.builder().question(pollRequest.getQuestion())
                .isApproved(false).username(currentUser.getUsername()).email(currentUser.getEmail())
                .choices( pollRequest.getChoices().stream()
                        .map(item-> Choice.builder().text(item.getText()).build())
                        .collect(Collectors.toList()))
                .build();

        PollResponse response = PollResponse.builder()
                .pollDto(pollDTOMapper.toDTO(pollAdapter.save(poll)))
                .success(true).build();

        emailNotificationProviderService.sendNotificationIfProposedPoll(poll);

        return response;
    }

    @Override
    public PollSummaryResponse getPollById(Long pollId, PollUser currentUser) {
        Poll poll = pollAdapter.findByIsActiveTrueAndId(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));
        return createPollResponse(currentUser.getUsername(),poll);
    }

    private PollSummaryResponse createPollResponse(String username, Poll poll) {
        Long pollId = poll.getId();
        Map<Long, Long> choiceVotes = getChoiceVoteCountMap(Collections.singletonList(pollId));
        Map<Long, Long> pollUserVotes = getPollUserVoteMap(username, Collections.singletonList(pollId));
        return pollServiceHelper.buildPollResponse(poll,choiceVotes,pollUserVotes.get(pollId));
    }

    @Override
    public void voted(Long pollId, Long choiceId, PollUser currentUser) {
        Poll poll = pollAdapter.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

        Choice selectedChoice = poll.getChoices().stream()
                .filter(choice -> choice.getId().equals(choiceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Choice", "id", choiceId));

        Vote vote = Vote.builder().poll(poll)
                .username(currentUser.getUsername())
                .choice(selectedChoice).build();

        voteAdapter.save(vote);
    }

    @Override
    public void deletePoll(Long id) {
        try{
            pollAdapter.deleteById(id);
        }catch(Throwable ex){
            throw new RuntimeException("Silme sırasında hata oluştu");
        }

    }

    @Override
    public void doPassivePoll(Long id) {
        Poll poll = pollAdapter.findById(id).orElseThrow(()->new RuntimeException("Pasife alma sırasında hata oluştu"));
        poll.setIsActive(false);
        pollAdapter.save(poll);
    }

    @Override
    public void confirmPoll(Long id) {
        Poll poll = pollAdapter.findById(id).orElseThrow(()->new RuntimeException("Onaylanma  sırasında hata oluştu"));
        poll.setIsActive(true);
        poll.setIsApproved(true);
        pollAdapter.save(poll);
        emailNotificationProviderService.sendNotificationIfConfirmedPoll(poll);
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    public void sendNotificationEmail(String username,String email) {
        emailNotificationProviderService.sendNotificationIfConfirmedPoll(Poll.builder().username(username).email(email).build());
    }


}
