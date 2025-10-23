package com.oxcart.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="battle_id")
    private int id;

}
