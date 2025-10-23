package com.oxcart.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private int id;
    @Column(name="card_name")
    private String name;
    @Column(name="card_type")
    private String type;
    @Column(name = "card_rarity")
    private String rarity;
    @Column(name = "card_attack")
    private int attack;
    @Column(name = "card_defense")
    private int defense;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="aircraft_id", nullable=false)
    private Aircraft aircraft;
}
