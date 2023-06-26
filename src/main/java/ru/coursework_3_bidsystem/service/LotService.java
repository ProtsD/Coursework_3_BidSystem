package ru.coursework_3_bidsystem.service;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import ru.coursework_3_bidsystem.dto.BidDto;
import ru.coursework_3_bidsystem.dto.LotDto;
import ru.coursework_3_bidsystem.dto.LotFullInfo;
import ru.coursework_3_bidsystem.dto.LotFullInfoView;
import ru.coursework_3_bidsystem.enumeration.LotStatus;

import java.util.List;

public interface LotService {
    LotFullInfoView createLot(LotDto lotDto);

    void startBidding(Integer id);

    void placeBid(Integer id, String name);

    void stopBidding(Integer id);

    LotFullInfo getLotFullInfo(Integer id);

    BidDto getFirstBid(Integer id);

    List<BidDto> getBidderWithMostBids(Integer lot);

    List<LotFullInfoView> getFilteredLots(LotStatus lotStatus, PageRequest pageRequest);

    Resource exportAllLotsToFile();
}
