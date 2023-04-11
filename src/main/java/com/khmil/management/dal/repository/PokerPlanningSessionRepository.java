package com.khmil.management.dal.repository;

import com.khmil.management.dal.entity.PokerPlanningSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokerPlanningSessionRepository extends JpaRepository<PokerPlanningSession, Long> {

    Optional<PokerPlanningSession> findPokerPlanningSessionByUserStoriesI(Long aLong);
}