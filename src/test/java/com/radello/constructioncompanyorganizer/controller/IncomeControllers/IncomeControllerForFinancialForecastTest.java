package com.radello.constructioncompanyorganizer.controller.IncomeControllers;

import com.radello.constructioncompanyorganizer.commands.IncomeCommand;
import com.radello.constructioncompanyorganizer.controller.IncomeControllers.IncomeControllerForFinancialForecast;
import com.radello.constructioncompanyorganizer.services.incomesServices.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class IncomeControllerForFinancialForecastTest {

    IncomeControllerForFinancialForecast incomeControllerforFinancialForecast;

    @Mock
    IncomeService incomeService;

    @Mock
    IncomeCommand command;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        incomeControllerforFinancialForecast = new IncomeControllerForFinancialForecast(incomeService);
        mockMvc = MockMvcBuilders.standaloneSetup(incomeControllerforFinancialForecast).build();
    }

    @Test
    void shownewIncomeTemplate() throws Exception {

        mockMvc.perform(get("/newIncome/"))
                .andExpect(status().isOk())
                .andExpect(view().name("IncomeTemplates/newIncome"));

        ArgumentCaptor<IncomeCommand> argumentCaptor = ArgumentCaptor.forClass(IncomeCommand.class);
        String viewName = incomeControllerforFinancialForecast.shownewIncomeTemplate(model);
        assertEquals("IncomeTemplates/newIncome", viewName);

        verify(model, times(1)).addAttribute(eq("income"), argumentCaptor.capture());
        IncomeCommand setInController = argumentCaptor.getValue();
        assertNotNull(setInController);
        assertEquals(0, setInController.getAmount());
        assertNull(setInController.getForWhat());
    }

    @Test
    void saveOrUpdate() {

        command.setAmount(1000);
        command.setForWhat("For Flat");
        command.setID(1L);
        when(incomeService.saveIncomeCommand(any())).thenReturn(command);

        String viewName = incomeControllerforFinancialForecast.saveOrUpdate(command,bindingResult, new String("01/01/2020"),  model);

        assertEquals("redirect:/financialForecast", viewName);
        verify(incomeService, times(1)).saveIncomeCommand(any());
        verify(incomeService, never()).findCommandByID(any());
    }


    @Test
    void deleteById() throws Exception{
        mockMvc.perform(get("/income/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/financialForecast"));

       verify(incomeService, times(1)).deleteById(anyLong());
    }
}