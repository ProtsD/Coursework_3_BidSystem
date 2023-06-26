package ru.coursework_3_bidsystem.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coursework_3_bidsystem.dto.BidDto;
import ru.coursework_3_bidsystem.dto.LotDto;
import ru.coursework_3_bidsystem.dto.LotFullInfo;
import ru.coursework_3_bidsystem.dto.LotFullInfoView;
import ru.coursework_3_bidsystem.enumeration.LotStatus;
import ru.coursework_3_bidsystem.service.LotService;

import java.util.List;

@Api("Customer Controller")

@RestController
@RequestMapping("/lot")
@RequiredArgsConstructor
public class LotController {
    private final LotService lotService;

    @PostMapping()
    public LotFullInfoView createLot(@RequestBody LotDto lotDto) {
        return lotService.createLot(lotDto);
    }

    @PostMapping("/{id}/start")
    public void startBidding(@PathVariable Integer id) {
        lotService.startBidding(id);
    }

    @PostMapping("/{id}/bid")
    public void placeBid(@PathVariable Integer id, @RequestBody String name) {
        lotService.placeBid(id, name);
    }

    @PostMapping("/{id}/stop")
    public void stopBidding(@PathVariable Integer id) {
        lotService.stopBidding(id);
    }

    @GetMapping("/{id}")
    public LotFullInfo getLotDto(@PathVariable("id") Integer id) {
        return lotService.getLotFullInfo(id);
    }

    @GetMapping("/{id}/first")
    public BidDto getFirstBidder(@PathVariable Integer id) {
        return lotService.getFirstBid(id);
    }

    @GetMapping("/{id}/frequent")
    public List<BidDto> getBidderWithMostBids(@PathVariable Integer id) {
        return lotService.getBidderWithMostBids(id);
    }

    @GetMapping
    public List<LotFullInfoView> getFilteredLots(
            @RequestParam(value = "status", required = false) LotStatus lotStatus,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        return lotService.getFilteredLots(lotStatus, PageRequest.of(page, size));
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportAllLotsToFile() {
        String fileName = "lot.csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(lotService.exportAllLotsToFile());
    }
}
