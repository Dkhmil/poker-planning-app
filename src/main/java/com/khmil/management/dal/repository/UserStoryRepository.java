package com.khmil.management.dal.repository;

import com.khmil.management.dal.entity.UserStoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStoryEntity, String> {
    List<UserStoryEntity> findAllBySessionId(String sessionId);
}
