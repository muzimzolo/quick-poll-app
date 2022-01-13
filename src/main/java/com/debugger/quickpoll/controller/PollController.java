package com.debugger.quickpoll.controller;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.debugger.quickpoll.domain.Poll;
import com.debugger.quickpoll.exception.ResourceNotFoundException;
import com.debugger.quickpoll.repository.PollRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "polls", description = "Poll API")
public class PollController {
	@Inject
	private PollRepository pollRepository;

	// Get all polls
	@GetMapping("/polls")
	@ApiOperation(value = "Retrieves all the polls", response=Poll.class, responseContainer="List")
	public ResponseEntity<Iterable<Poll>> getAllPolls() {
		Iterable<Poll> allPolls = pollRepository.findAll();
		return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);

	}

    //  create a new poll
	@PostMapping("/polls")
	@ApiOperation(value = "Creates a new Poll", 
	notes="The newly created poll Id will be sent in the location response header", response = Void.class)
	public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
	        poll = pollRepository.save(poll);
	        // Set the location header for the newly created resource
	        HttpHeaders responseHeaders = new HttpHeaders();
	        URI newPollUri = ServletUriComponentsBuilder
	                                .fromCurrentRequest()
	                                .path("/{id}")
	                                .buildAndExpand(poll.getId())
	                                .toUri();
	        responseHeaders.setLocation(newPollUri);
	        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	// Get poll request by Id
	@GetMapping("/polls/{pollId}")
	@ApiOperation(value = "Retrieves a Poll associated with the pollId", response=Poll.class)
	public ResponseEntity<?> getPoll(@PathVariable Long pollId) throws Exception {
	        Optional<Poll> poll = pollRepository.findById(pollId);
	        if(!poll.isPresent()) {
                throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
                
        }
	       // NEED CUSTOM ERROR MESSAGE System.out.println("No poll matching poll id " + pollId + " found");
	        return new ResponseEntity<>(poll.get(), HttpStatus.OK);
	}
	// update a poll
	@PutMapping("/polls/{pollId}")
	public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
		verifyPoll(pollId);
		// save the entity
		pollRepository.save(poll);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/polls/{pollId}")
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
		pollRepository.deleteById(pollId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// Verify if a poll exists
	protected Poll verifyPoll(Long pollId) throws ResourceNotFoundException {
		Optional<Poll> poll = pollRepository.findById(pollId);
		if (!poll.isPresent()) {
			throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
		}
		return poll.get();
	}

}