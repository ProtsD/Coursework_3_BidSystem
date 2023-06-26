package ru.coursework_3_bidsystem.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class LotDto {
    @Size(max = 64, min = 3)
    private String title;
    @Size(max = 4096, min = 1)
    private String description;
    @Size(min = 1)
    private Integer startPrice;
    @Size(min = 1)
    private Integer bidPrice;
}
