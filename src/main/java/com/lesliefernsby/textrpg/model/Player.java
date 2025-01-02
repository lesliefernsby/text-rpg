package com.lesliefernsby.textrpg.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer exp;
    private Integer lvl;

    private Integer str;
    private Integer dex;
    private Integer con;
    private Integer intel; 
    private Integer wis;
    private Integer cha;
}
