package com.example.demo.DTO;

import com.example.demo.entities.inputEntity;

import java.util.ArrayList;
import java.util.List;

public class CostResponse {
    private int totalCost;

    public CostResponse(int totalCost, List<BreakDown> breakDownList) {
        this.totalCost = totalCost;
        this.breakDownList = breakDownList;
    }

    List<BreakDown> breakDownList;
    public static class  BreakDown{
        private final int cost;
        private final inputEntity input;
        private final int price;

        public BreakDown(int cost, inputEntity input,int price) {
            this.cost = cost;
            this.input = input;
            this.price=price;
        }

        public int getPrice() { return price; }

        public int getCost() { return cost; }

        public inputEntity getInput() { return input; }
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<BreakDown> getBreakDownList() {
        return breakDownList;
    }
}
