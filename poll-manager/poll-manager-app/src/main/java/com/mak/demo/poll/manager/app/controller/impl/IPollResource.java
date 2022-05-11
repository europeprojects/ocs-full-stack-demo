package com.mak.demo.poll.manager.app.controller.impl;

import com.mak.demo.poll.manager.app.model.request.PollRequest;
import com.mak.demo.poll.manager.app.model.response.PagedResponse;
import com.mak.demo.poll.manager.app.model.response.PollResponse;
import com.mak.demo.poll.manager.app.model.response.PollSummaryResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


public interface IPollResource {

    @GetMapping
    ResponseEntity<PagedResponse<PollSummaryResponse>> getPolls(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size);

    @GetMapping("/proposal")
    ResponseEntity<PagedResponse<PollSummaryResponse>> getProposalPolls(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size);

    @GetMapping("/enduser")
    ResponseEntity<PagedResponse<PollSummaryResponse>> getEndUserPolls(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size);

    @PostMapping(value="/propose",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PollResponse> proposePoll(@Valid @RequestBody PollRequest pollRequest);

    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PollResponse> createPoll(@Valid @RequestBody PollRequest pollRequest);

    @GetMapping("/{pollId}")
    PollSummaryResponse getPollById(@PathVariable Long pollId);

    @PostMapping("/{pollId}/delete")
    ResponseEntity deletePoll(@PathVariable Long pollId);

    @PostMapping("/{pollId}/doPassive")
    ResponseEntity doPassive(@PathVariable Long pollId);

    @PostMapping("/{pollId}/confirm")
    ResponseEntity doConfirm(@PathVariable Long pollId);

    @PostMapping("/{pollId}/{choiceId}/voted")
    ResponseEntity voted(@PathVariable Long pollId,@PathVariable Long choiceId);

}
