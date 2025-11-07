package com.oxcart.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transactions_id")
    private int id;
    @Column(name="transactions_timestamp")
    private String timestamp;
    @Column(name="transactions_type")
    private int type;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="card_id", nullable=false)
    private Card card;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
