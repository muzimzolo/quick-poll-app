package com.debugger.quickpoll.controller.v2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.debugger.quickpoll.domain.Vote;
import com.debugger.quickpoll.dto.OptionCount;
import com.debugger.quickpoll.dto.VoteResult;
import com.debugger.quickpoll.repository.VoteRepository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@RestController("computeResultControllerV2")
@RequestMapping("/v2/")
@Api(value = "computeresult", description = "Compute Result Api")
public class ComputeResultController {

    @Inject
    private VoteRepository voteRepository;

    @RequestMapping(value = "/computeresult", method = RequestMethod.GET)
    @ApiOperation(value = "Computes the results of a given Poll", response = VoteResult.class)
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);
        int totalVotes = 0;
        Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount>();
        for (Vote v: allVotes) {
            totalVotes++;
            OptionCount optionCount = tempMap.get(v.getOption().getId());
            if (optionCount == null) {
                optionCount = new OptionCount();
                optionCount.setOptionId(v.getOption().getId());
                tempMap.put(v.getOption().getId(), optionCount);
            }
            optionCount.setCount(optionCount.getCount()+1);
        }
        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(tempMap.values());
        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
    }
}