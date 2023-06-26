package ru.coursework_3_bidsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.coursework_3_bidsystem.entity.Bid;

import java.util.List;

public interface BidRepository extends CrudRepository<Bid, Integer> {
    @Query(value = "SELECT * FROM bid WHERE lot_id = :id ORDER BY date LIMIT 1", nativeQuery = true)
    Bid getFirstBid(@Param("id") Integer id);

    @Query(value = "SELECT * FROM bid WHERE lot_id = :lotId", nativeQuery = true)
    List<Bid> getLotBids(@Param("lotId") Integer lotId);
}
