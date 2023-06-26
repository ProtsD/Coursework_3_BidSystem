package ru.coursework_3_bidsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.coursework_3_bidsystem.enumeration.LotStatus;

import java.util.List;


@Entity
@Table(name = "lot")
@Getter
@Setter
@Valid
public class Lot implements Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LotStatus status;

    @Column(name = "title")
    @Size(min = 3, max = 64)
    private String title;

    @Column(name = "description")
    @Size(min = 1, max = 4096)
    private String description;

    @Column(name = "startprice")
    @DecimalMin(value = "1")
    private Integer startPrice;

    @Column(name = "bidprice")
    @DecimalMin(value = "1")
    private Integer bidPrice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lot_id")
    private List<Bid> bids;

    @Override
    public int compareTo(Object o) {
        Lot lot = (Lot) o;
        return this.id.compareTo(lot.id);
    }
}
