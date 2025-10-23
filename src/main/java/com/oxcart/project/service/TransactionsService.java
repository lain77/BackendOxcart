package com.oxcart.project.service;

import com.oxcart.project.dto.request.TransactionDTORequest;
import com.oxcart.project.entity.Transactions;
import com.oxcart.project.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsService {

    private TransactionsRepository transactionsRepository;

    public TransactionsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public List<Transactions> listarTransacoes(){
        return this.transactionsRepository.findAll();
    }

    public Transactions obterPorId(Integer id) { return  transactionsRepository.findById(id).orElse(null);}

    public Transactions criarTransaction(TransactionDTORequest request) {
        Transactions transactions = new Transactions();
        transactions.setId(request.getId());
        transactions.setType(request.getType());
        transactions.setTimestamp(request.getTimestamp());

        Transactions saved = transactionsRepository.save(transactions);
        return toResponse(saved);
    }

    private Transactions toResponse(Transactions transactions) {
        Transactions dto = new Transactions();
        dto.setId(transactions.getId());
        dto.setType(transactions.getType());
        dto.setCard(transactions.getCard());
        dto.setTimestamp(transactions.getTimestamp());
        dto.setUser(transactions.getUser());
        return dto;
    }
}
