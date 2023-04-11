package com.khmil.management.dal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStoryEntity, String> {
    List<UserStoryEntity> findAllBySessionId(String sessionId);
}
