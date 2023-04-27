package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileDao extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> getByUserID(Long id);
}