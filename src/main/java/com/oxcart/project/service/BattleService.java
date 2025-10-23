package com.oxcart.project.service;

import com.oxcart.project.entity.Battle;
import com.oxcart.project.repository.BattleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleService {
    private BattleRepository battleRepository;

    public BattleService(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    public List<Battle> listarBattles(){
        return this.battleRepository.findAll();
    }
}