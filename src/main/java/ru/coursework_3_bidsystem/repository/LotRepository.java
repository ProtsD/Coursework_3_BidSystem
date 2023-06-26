package ru.coursework_3_bidsystem.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.coursework_3_bidsystem.dto.LotFullInfoView;
import ru.coursework_3_bidsystem.entity.Lot;

import java.util.List;

public interface LotRepository extends CrudRepository<Lot, Integer>, PagingAndSortingRepository<Lot, Integer> {
    @Query(value = "SELECT id, status, title, description, startprice, bidprice   FROM lot WHERE id = :id", nativeQuery = true)
    LotFullInfoView getLotFullInfo(@Param("id") Integer id);

    @Query(value = "SELECT * FROM lot WHERE status = :status", nativeQuery = true)
    List<LotFullInfoView> getFilteredLots(@Param("status") String status, PageRequest pageRequest);
}
