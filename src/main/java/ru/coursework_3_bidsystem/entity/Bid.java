package ru.coursework_3_bidsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bid")
@Getter
@Setter
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "name")
    private String name;
    @Column(name = "lot_id")
    private Integer lot;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return Objects.equals(name, bid.name) && Objects.equals(lot, bid.lot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lot);
    }
}
