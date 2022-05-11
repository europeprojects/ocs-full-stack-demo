package com.mak.demo.poll.manager.app.controller;

import com.mak.demo.poll.manager.app.auth.PollOAuth2;
import com.mak.demo.poll.manager.app.auth.PollUser;
import com.mak.demo.poll.manager.app.controller.impl.IPollResource;
import com.mak.demo.poll.manager.app.model.request.PollRequest;
import com.mak.demo.poll.manager.app.model.response.PagedResponse;
import com.mak.demo.poll.manager.app.model.response.PollResponse;
import com.mak.demo.poll.manager.app.model.response.PollSummaryResponse;
import com.mak.demo.poll.manager.app.service.impl.PollService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Poll Manager", description = "Poll Api")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/polls")
public class PollResource implements IPollResource {

    private final PollService pollService;

    public static PollUser getPollUser(){
        PollOAuth2 auth = (PollOAuth2) SecurityContextHolder.getContext().getAuthentication();
        return PollUser.builder().username(auth.getUsername()).email(auth.getEmail()).build();
    }


   // @PreAuthorize("hasRole('POLL_ADMIN')")
    @PreAuthorize("hasAuthority('POLL_ADMIN')")
    @Override
    public ResponseEntity<PagedResponse<PollSummaryResponse>> getPolls(int page, int size) {


        return ResponseEntity.ok().body(pollService.getPolls(getPollUser(),page,size));
    }

    @PreAuthorize("hasAuthority('POLL_ADMIN')")
    @Override
    public ResponseEntity<PagedResponse<PollSummaryResponse>> getProposalPolls(int page, int size) {
        return  ResponseEntity.ok().body(pollService.getProposalPolls(getPollUser(),page,size));
    }

    @PreAuthorize("hasAuthority('POLL_USER')")
    @Override
    public ResponseEntity<PagedResponse<PollSummaryResponse>> getEndUserPolls(int page, int size) {

        return ResponseEntity.ok().body(pollService.getPollsForEndUser(getPollUser(),page,size));
    }

    @PreAuthorize("hasAuthority('POLL_ADMIN')")
    @Override
    public ResponseEntity<PollResponse> createPoll(@Valid @RequestBody PollRequest pollRequest) {
        return  ResponseEntity.ok().body(pollService.createPoll(getPollUser(),pollRequest));
    }

    @PreAuthorize("hasAuthority('POLL_USER')")
    @Override
    public ResponseEntity<PollResponse> proposePoll(PollRequest pollRequest) {

        return  ResponseEntity.ok().body(pollService.proposePoll(getPollUser(),pollRequest));
    }

    @PreAuthorize("hasAuthority('POLL_ADMIN')")
    @Override
    public PollSummaryResponse getPollById(Long pollId) {
        return  pollService.getPollById(pollId, getPollUser());
    }

    @Override
    public ResponseEntity deletePoll(Long pollId) {
        pollService.deletePoll(pollId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('POLL_ADMIN')")
    @Override
    public ResponseEntity doPassive(Long pollId) {
        pollService.doPassivePoll(pollId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('POLL_ADMIN')")
    @Override
    public ResponseEntity doConfirm(Long pollId) {
        pollService.confirmPoll(pollId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('POLL_USER')")
    @Override
    public ResponseEntity voted(Long pollId, Long choiceId) {
        pollService.voted(pollId,choiceId, getPollUser());
        return ResponseEntity.ok().build();
    }


    @GetMapping("sendNotificationEmail/{username}/{email}")
    public ResponseEntity sendNotificationEmail(@PathVariable String username,@PathVariable String email) {
        pollService.sendNotificationEmail(username,email);
        return ResponseEntity.ok().build();
    }

}
