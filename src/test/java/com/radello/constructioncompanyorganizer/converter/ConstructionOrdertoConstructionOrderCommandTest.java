package com.radello.constructioncompanyorganizer.converter;

import com.radello.constructioncompanyorganizer.commands.ConstructionOrderCommand;

import com.radello.constructioncompanyorganizer.domain.ConstructionOrder;
import com.radello.constructioncompanyorganizer.domain.Income;
import com.radello.constructioncompanyorganizer.domain.IndicativeCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConstructionOrdertoConstructionOrderCommandTest {

    private final Long ID_VALUE_1 = 1L;
    private final Long ID_VALUE_2 = 2L;
    private final String FOR_WHAT_VALUE_1 = "flat";
    private final String FOR_WHAT_VALUE_3 = "flat2";
    private final String FOR_WHAT_VALUE_2 = "flat1";
    private final LocalDate LOCAL_DATE_VALUE = LocalDate.now();
    private final LocalDate LOCAL_DATE_VALUE2 = LocalDate.now().plusDays(2);
    private final LocalDate LOCAL_DATE_VALUE3 = LocalDate.now().minusDays(3);
    private final int AMOUNT_VALUE = 123;
    private final String TITLE_VALUE = "Title";
    private final String ADDRES_VALUE = "Addres Value";

    ConstructionOrdertoConstructionOrderCommand constructionOrdertoConstructionOrderCommand;

    @BeforeEach
    void setUp() {
        constructionOrdertoConstructionOrderCommand = new ConstructionOrdertoConstructionOrderCommand
                (new IncomeToIncomeCommand(), new IndicativeCostToIndicativeCostCommand());
    }

    @Test
    void testNullObject() {
        assertNull(constructionOrdertoConstructionOrderCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(constructionOrdertoConstructionOrderCommand.convert(new ConstructionOrder()));
    }

    @Test
    void convert() {
        ConstructionOrder constructionOrder1 = new ConstructionOrder();

        constructionOrder1.setID(ID_VALUE_1);
        constructionOrder1.setTitle(TITLE_VALUE);
        constructionOrder1.setStartDate(LOCAL_DATE_VALUE3);
        constructionOrder1.setScheduledEndDate(LOCAL_DATE_VALUE2);
        constructionOrder1.setAddres(ADDRES_VALUE);

        Income income = new Income();
        income.setID(ID_VALUE_1);
        income.setForWhat(FOR_WHAT_VALUE_3);
        income.setAmount(AMOUNT_VALUE);


        IndicativeCost indicativeCost1 = new IndicativeCost();
        indicativeCost1.setID(ID_VALUE_1);
        indicativeCost1.setForWhat(FOR_WHAT_VALUE_1);

        IndicativeCost indicativeCost2 = new IndicativeCost();
        indicativeCost2.setID(ID_VALUE_2);
        indicativeCost2.setForWhat(FOR_WHAT_VALUE_2);

        constructionOrder1.getIndicativeCosts().add(indicativeCost1);
        constructionOrder1.getIndicativeCosts().add(indicativeCost2);

        ConstructionOrderCommand constructionOrderCommand = constructionOrdertoConstructionOrderCommand
                .convert(constructionOrder1);

        assertNotNull(constructionOrderCommand);
        assertEquals(ID_VALUE_1, constructionOrderCommand.getID());
        assertEquals(TITLE_VALUE, constructionOrderCommand.getTitle());
        assertEquals(LOCAL_DATE_VALUE3, constructionOrderCommand.getStartDate());
        assertEquals(LOCAL_DATE_VALUE2, constructionOrderCommand.getScheduledEndDate());
        assertEquals(ADDRES_VALUE, constructionOrderCommand.getAddres());
        assertEquals(2, constructionOrderCommand.getIndicativeCostCommands().size());
    }
}
