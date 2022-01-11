package com.debugger.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.debugger.quickpoll.domain.Option;

@Repository
public interface OptionRepository extends CrudRepository<Option, Long> {

}