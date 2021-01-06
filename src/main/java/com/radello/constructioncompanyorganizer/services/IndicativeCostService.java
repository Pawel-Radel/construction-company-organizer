package com.radello.constructioncompanyorganizer.services;


import com.radello.constructioncompanyorganizer.commands.IndicativeCostCommand;
import com.radello.constructioncompanyorganizer.domain.IndicativeCost;

import java.util.Set;

public interface IndicativeCostService {

    Set<IndicativeCost> getIndicativeCosts();

    IndicativeCost findByID(Long l);

    IndicativeCostCommand findCommandByID(Long l);

    IndicativeCostCommand saveCostCommand (IndicativeCostCommand command);

    void deleteById(Long l);

}