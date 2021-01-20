package com.radello.constructioncompanyorganizer.controller.IncomeControllers;

import com.radello.constructioncompanyorganizer.commands.ConstructionOrderCommand;
import com.radello.constructioncompanyorganizer.commands.IncomeCommand;
import com.radello.constructioncompanyorganizer.domain.DateFormatter;
import com.radello.constructioncompanyorganizer.domain.Income;
import com.radello.constructioncompanyorganizer.services.constructionOrderServices.ConstructionOrderService;
import com.radello.constructioncompanyorganizer.services.incomesServices.IncomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller that handles inquiries revenue incomes during creating a construction order
@Controller
public class IncomeControllerToConstructionOrderFormCreate {

    IncomeService incomeService;
    ConstructionOrderService constructionOrderService;

    public IncomeControllerToConstructionOrderFormCreate(IncomeService incomeService,
                                                         ConstructionOrderService constructionOrderService) {

        this.incomeService = incomeService;
        this.constructionOrderService = constructionOrderService;
    }


    @GetMapping("/newIncomeToConsOrder/{id}")
    public String addingIncomestoConstructionOrder(@PathVariable String id, Model model) {

        ConstructionOrderCommand command1 = constructionOrderService.findCommandByID(Long.valueOf(id));
        List list = incomeService.sortSet(command1.getIncomeCommands());

        model.addAttribute("income", new IncomeCommand());
        model.addAttribute("ConsOrd", command1);
        model.addAttribute("ConsOrd2", list);
        model.addAttribute("SumAmount", incomeService.sumValues(list));

        return "IncomeTemplates/newIncomesToConsOrder";

    }

    @PostMapping("/newIncomeToConsOrder/{id}/create/")
    public String addingIncomeToConstructionOrder(@ModelAttribute("income") IncomeCommand incomeCommand,
                                                  @PathVariable String id,
                                                  @RequestParam("date") String date) {

        incomeCommand.setDatesByString(date);
        IncomeCommand savedIncomeCommand = incomeService.saveIncomeCommand(incomeCommand);
        ConstructionOrderCommand command = constructionOrderService.findCommandByID(Long.valueOf(id));
        command.addIncomes(savedIncomeCommand);

        constructionOrderService.saveConstructionOrderCommand(command);

        return "redirect:/newIncomeToConsOrder/" + id;
    }

    @GetMapping("incomefromConsOrd/{id}/delete")
    public String deleteIncomefromConstructionOrderById(@PathVariable String id) {

        Long ID = incomeService.findByID(Long.valueOf(id)).getConstructionOrder().getID();
        String string = String.valueOf(ID);
        incomeService.deleteById(Long.valueOf(id));

        return "redirect:/newIncomeToConsOrder/" + string;
    }

    @GetMapping("/constructionOrder/{id1}/income/{id2}/edit/")
    public String editIndicativeCostById(@PathVariable String id1,
                                         @PathVariable String id2,
                                         Model model) {

        ConstructionOrderCommand command1 = constructionOrderService.findCommandByID(Long.valueOf(id1));
        List list = incomeService.sortSet(command1.getIncomeCommands());
        IncomeCommand incomeCommand = incomeService.findCommandByID(Long.valueOf(id2));
        String date = DateFormatter.formatDateToProperlyString(incomeCommand.getScheduledTimeToGet());

        model.addAttribute("income", incomeCommand);
        model.addAttribute("ConsOrd", command1);
        model.addAttribute("ConsOrd2", list);
        model.addAttribute("SumAmount", incomeService.sumValues(list));
        model.addAttribute("date", date);

        return "IncomeTemplates/editIncomesToConsOrder";
    }

    @PostMapping("/income/{id}/edit")
    public String editIndicativeCost(@PathVariable String id,
                                     @ModelAttribute("cost") IncomeCommand incomeCommand,
                                     @RequestParam (value = "date2") String date) {

        incomeCommand.setDatesByString(date);
        Income income = incomeService.findByID(incomeCommand.getID());
        income.setAmount(incomeCommand.getAmount());
        income.setForWhat(incomeCommand.getForWhat());
        income.setScheduledTimeToGet(incomeCommand.getScheduledTimeToGet());
        incomeService.saveIncome(income);

        return "redirect:/newIncomeToConsOrder/" + id;
    }
}
