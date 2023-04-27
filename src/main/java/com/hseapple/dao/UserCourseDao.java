package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCourseDao extends JpaRepository<UserCourseEntity, Long> {
    Optional<UserCourseEntity> findByUserIDAndCourseID(Long userID, Integer courseID);
}