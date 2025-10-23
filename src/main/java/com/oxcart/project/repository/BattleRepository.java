package com.oxcart.project.repository;

import com.oxcart.project.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Integer> {
}
