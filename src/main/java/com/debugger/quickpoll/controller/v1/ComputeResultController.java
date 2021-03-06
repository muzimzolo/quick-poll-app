package com.debugger.quickpoll.controller.v1;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.debugger.quickpoll.domain.Vote;
import com.debugger.quickpoll.dto.OptionCount;
import com.debugger.quickpoll.dto.VoteResult;
import com.debugger.quickpoll.repository.VoteRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/v1/")
@Api(value = "computeresult", description = "Compute Result Api")
@RestController("computeResultControllerV1")
public class ComputeResultController {

	@Inject
	private VoteRepository voteRepository;

	@GetMapping("/computeresult")
	@ApiOperation(value = "Computes the results of a given Poll", response = VoteResult.class)
	public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
		VoteResult voteResult = new VoteResult();
		Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);
		int totalVotes = 0;
		Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount>();
		// Algorithm to count votes
		for (Vote v : allVotes) {
			totalVotes++;
			// Get the option count corresponding to this result
			OptionCount optionCount = tempMap.get(v.getOption().getId());
			if (optionCount == null) {
				optionCount = new OptionCount();
				optionCount.setOptionId(v.getOption().getId());
				tempMap.put(v.getOption().getId(), optionCount);
			}
			optionCount.setCount(optionCount.getCount() + 1);
		}
		voteResult.setTotalVotes(totalVotes);
		voteResult.setResults(tempMap.values());
		return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);

	}
}