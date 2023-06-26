package ru.coursework_3_bidsystem.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ru.coursework_3_bidsystem.enumeration.LotStatus;

@Component
@Getter
@Setter
@ToString
public class LotFullInfo {
    private Integer id;
    private LotStatus status;
    @Size(max = 64, min = 3)
    private String title;
    @Size(max = 4096, min = 1)
    private String description;
    @Size(min = 1)
    private Integer startPrice;
    @Size(min = 1)
    private Integer bidPrice;
    private Double currentPrice;
    private BidDto lastBid;
}
