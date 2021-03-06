package com.radello.constructioncompanyorganizer.converter;


import com.radello.constructioncompanyorganizer.commands.IndicativeCostCommand;
import com.radello.constructioncompanyorganizer.domain.IndicativeCost;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IndicativeCostCommandToIndicativeCost implements Converter<IndicativeCostCommand, IndicativeCost> {


    @Synchronized
    @Nullable
    @Override
    public IndicativeCost convert(IndicativeCostCommand indicativeCostCommand) {

        if (indicativeCostCommand == null) return null;

        final IndicativeCost indicativeCost = new IndicativeCost();

        indicativeCost.setID(indicativeCostCommand.getID());
        indicativeCost.setForWhat(indicativeCostCommand.getForWhat());
        indicativeCost.setAmount(indicativeCostCommand.getAmount());


        return indicativeCost;
    }
}
