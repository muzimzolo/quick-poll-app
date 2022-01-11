package com.debugger.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.debugger.quickpoll.domain.Poll;


@Repository
public interface PollRepository extends CrudRepository<Poll, Long>{

}