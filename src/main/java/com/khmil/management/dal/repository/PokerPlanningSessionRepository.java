package com.khmil.management.dal.repository;


import com.khmil.management.dal.entity.PokerPlanningSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokerPlanningSessionRepository extends JpaRepository<PokerPlanningSessionEntity, Long> {

    Optional<PokerPlanningSessionEntity> findByInviteLink(String inviteLink);

}
