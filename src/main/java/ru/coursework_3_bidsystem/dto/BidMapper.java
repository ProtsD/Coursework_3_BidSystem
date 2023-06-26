package ru.coursework_3_bidsystem.dto;

import org.springframework.stereotype.Component;
import ru.coursework_3_bidsystem.entity.Bid;

import java.time.LocalDateTime;
@Component
public class BidMapper {
    public BidDto toDto(Bid bid) {
        BidDto bidDto = new BidDto();
        bidDto.setDate(bid.getDate());
        bidDto.setName(bid.getName());
        return bidDto;
    }

    public Bid fromDto(BidDto bidDto) {
        Bid bid = new Bid();
        bid.setDate(LocalDateTime.now());
        bid.setName(bidDto.getName());
        return bid;
    }
    public Bid fromDto(String name) {
        Bid bid = new Bid();
        bid.setDate(LocalDateTime.now());
        bid.setName(name);
        return bid;
    }
}
