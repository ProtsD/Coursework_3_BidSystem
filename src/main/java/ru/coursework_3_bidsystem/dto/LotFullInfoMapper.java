package ru.coursework_3_bidsystem.dto;

import org.springframework.stereotype.Component;
import ru.coursework_3_bidsystem.entity.Lot;

@Component
public class LotFullInfoMapper {
    public LotFullInfo toDto(Lot lot, Double currentPrice, BidDto bid) {
        LotFullInfo lotFullInfo = new LotFullInfo();

        lotFullInfo.setId(lot.getId());
        lotFullInfo.setStatus(lot.getStatus());
        lotFullInfo.setTitle(lot.getTitle());
        lotFullInfo.setDescription(lot.getDescription());
        lotFullInfo.setStartPrice(lot.getStartPrice());
        lotFullInfo.setBidPrice(lot.getBidPrice());
        lotFullInfo.setCurrentPrice(currentPrice);
        lotFullInfo.setLastBid(bid);
        return lotFullInfo;
    }
}
