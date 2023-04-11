package com.khmil.management.dal.repository;

import com.khmil.management.dal.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByUserStoryId(Long userStoryId);
}
