package com.radello.constructioncompanyorganizer.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BudgetCommand {


    private int ID;
    // Represent amount of money in budget
    private int amount;
}