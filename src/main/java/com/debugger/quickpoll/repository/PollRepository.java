package com.debugger.quickpoll.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.debugger.quickpoll.domain.Poll;


@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Long>{

}