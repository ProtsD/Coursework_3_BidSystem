package ru.coursework_3_bidsystem.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@Setter
@ToString
public class BidDto {
    private LocalDateTime date;
    private String name;
}
