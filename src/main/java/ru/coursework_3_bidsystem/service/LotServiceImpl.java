package ru.coursework_3_bidsystem.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;
import ru.coursework_3_bidsystem.dto.*;
import ru.coursework_3_bidsystem.entity.Bid;
import ru.coursework_3_bidsystem.entity.Lot;
import ru.coursework_3_bidsystem.enumeration.LotStatus;
import ru.coursework_3_bidsystem.exception.BidsNotFoundException;
import ru.coursework_3_bidsystem.repository.BidRepository;
import ru.coursework_3_bidsystem.repository.LotRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {
    private final LotRepository lotRepository;
    private final LotMapper lotMapper;
    private final BidRepository bidRepository;
    private final BidMapper bidMapper;
    private final LotFullInfoMapper lotFullInfoMapper;
    Logger logger = LoggerFactory.getLogger(LotServiceImpl.class);

    @Override
    public LotFullInfoView createLot(LotDto lotDto) {
        logger.info("Invoke createLot() with argument: lotDto={}", lotDto);
        Lot lotDto1 = lotRepository.save(lotMapper.fromDto(lotDto, LotStatus.CREATED));
        logger.info("createLot() is processed ok");
        return lotRepository.getLotFullInfo(lotDto1.getId());
    }

    @Override
    public void startBidding(Integer id) {
        logger.info("Invoke startBidding() with argument: id={}", id);
        Lot lot = lotRepository.findById(id).orElseThrow();
        lot.setStatus(LotStatus.STARTED);
        lotRepository.save(lot);
        logger.info("startBidding() is processed ok");
    }

    @Override
    public void placeBid(Integer id, String name) {
        logger.info("Invoke placeBid() with arguments: id={}, name={}", id, name);
        Bid bid = bidMapper.fromDto(name);
        Lot lot = lotRepository.findById(id).orElseThrow();

        if (lot.getStatus() == LotStatus.CREATED)
            throw new NotAcceptableStatusException(lot.getStatus().toString());

        bid.setLot(lot.getId());
        bidRepository.save(bid);
        logger.info("placeBid() is processed ok");
    }

    @Override
    public void stopBidding(Integer id) {
        logger.info("Invoke stopBidding() with argument: id={}", id);
        Lot lot = lotRepository.findById(id).orElseThrow();
        lot.setStatus(LotStatus.STOPPED);
        lotRepository.save(lot);
        logger.info("stopBidding() is processed ok");
    }

    @Override
    public LotFullInfo getLotFullInfo(Integer id) {
        logger.info("Invoke exportAllLotsToFile() with no arguments");
        Lot lot = lotRepository.findById(id).orElseThrow();
        LotFullInfo lotFullInfo = lotFullInfoMapper.toDto(
                lot,
                getCurrentPrice(lot),
                getLastBid(lot.getBids()));
        logger.info("exportAllLotsToFile() is processed ok");
        return lotFullInfo;
    }

    @Override
    public BidDto getFirstBid(Integer lotId) {
        logger.info("Invoke getFirstBid() with argument: lotId={}", lotId);
        Lot lot = lotRepository.findById(lotId).orElseThrow();
        Bid bid = lot.getBids()
                .stream()
                .min(Comparator.comparing(Bid::getDate)).orElseThrow(BidsNotFoundException::new);
        logger.info("getFirstBid() is processed ok");
        return bidMapper.toDto(bid);
    }

    @Override
    public List<BidDto> getBidderWithMostBids(Integer lotId) {
        logger.info("Invoke getBidderWithMostBids() with argument: lotId={}", lotId);
        List<Bid> bidList = bidRepository.getLotBids(lotId);

        Map<String, Long> biddingFrequencyMap = bidList.stream()
                .collect(Collectors.groupingBy(Bid::getName, Collectors.counting()));

        Long maxFrequency = biddingFrequencyMap
                .entrySet()
                .stream()
                .max(Map.Entry.<String, Long>comparingByValue())
                .orElseThrow(BidsNotFoundException::new)
                .getValue();

        List<String> nameListWithMostBidding = biddingFrequencyMap
                .entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getValue(), maxFrequency))
                .map(Map.Entry::getKey)
                .toList();

        List<BidDto> listDto = bidList.stream()
                .filter(e -> nameListWithMostBidding.contains(e.getName()))
                .sorted(Comparator.comparing(Bid::getDate).reversed())
                .distinct()
                .map(bidMapper::toDto)
                .toList();

        logger.info("getBidderWithMostBids() is processed ok");
        return listDto;
    }

    @Override
    public List<LotFullInfoView> getFilteredLots(LotStatus lotStatus, PageRequest pageRequest) {
        logger.info("Invoke getFilteredLots() with arguments: lotStatus={}, pageRequest={}", lotStatus, pageRequest);
        List<LotFullInfoView> lotList = lotRepository.getFilteredLots(lotStatus.toString(), pageRequest);
        logger.info("getFilteredLots() is processed ok");
        System.out.println(lotStatus.toString());
        return lotList;
    }

    @Override
    public Resource exportAllLotsToFile() {
        logger.info("Invoke exportAllLotsToFile() with no arguments");
        StringWriter sw = new StringWriter();
        String[] HEADERS = {"Id", "Title", "Status", "Last bidder", "Current price"};
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .build();
        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            List<Lot> list = new ArrayList<>();
            lotRepository.findAll().forEach(list::add);
            list.stream().sorted()
                    .forEach(e -> {
                        try {
                            printer.printRecord(
                                    e.getId(),
                                    e.getTitle(),
                                    e.getStatus(),
                                    getLastBid(e.getBids()).getName(),
                                    getCurrentPrice(e));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String result = sw.toString();
        Resource resource = new ByteArrayResource(result.getBytes(StandardCharsets.UTF_8));
        logger.info("exportAllLotsToFile() is processed ok");
        return resource;
    }

    public BidDto getLastBid(List<Bid> list) {
        Optional<Bid> opt = list.stream().max(Comparator.comparing(Bid::getDate));
        if (opt.isPresent()) {
            return bidMapper.toDto(opt.get());
        }
        BidDto bid = new BidDto();
        bid.setName("---");
        return bid;
    }

    public double getCurrentPrice(Lot lot) {
        int bidsNumber = lot.getBids().size();
        double bidPrice = lot.getBidPrice();
        double startPrice = lot.getStartPrice();
        return bidsNumber * bidPrice + startPrice;
    }

    public boolean isPresentInList(List<String> list, String s) {
        list.lastIndexOf(s);
        return true;
    }
}
