package ru.coursework_3_bidsystem.dto;

import org.springframework.stereotype.Component;
import ru.coursework_3_bidsystem.entity.Lot;
import ru.coursework_3_bidsystem.enumeration.LotStatus;

@Component
public class LotMapper {
    public LotDto toDto(Lot lot) {
        LotDto lotDto = new LotDto();
        lotDto.setTitle(lot.getTitle());
        lotDto.setDescription(lot.getDescription());
        lotDto.setStartPrice(lot.getStartPrice());
        lotDto.setBidPrice(lot.getBidPrice());
        return lotDto;
    }

    public Lot fromDto(LotDto lotDto, LotStatus status) {
        Lot lot = new Lot();
        lot.setStatus(status);
        lot.setTitle(lotDto.getTitle());
        lot.setDescription(lotDto.getDescription());
        lot.setStartPrice(lotDto.getStartPrice());
        lot.setBidPrice(lotDto.getBidPrice());
        return lot;
    }
}
