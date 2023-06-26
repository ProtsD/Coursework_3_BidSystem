package ru.coursework_3_bidsystem.dto;

import ru.coursework_3_bidsystem.enumeration.LotStatus;

public interface LotFullInfoView {
    Integer getId();

    LotStatus getStatus();

    String getTitle();

    String getDescription();

    Integer getStartPrice();

    Integer getBidPrice();
}
