package com.oxcart.project.service;

import com.oxcart.project.entity.Card;
import com.oxcart.project.entity.Card;
import com.oxcart.project.repository.BattleRepository;
import com.oxcart.project.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private CardRepository cardRepository;

    public CardService(CardRepository battleRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> listarCartas(){
        return this.cardRepository.findAll();
    }
}
