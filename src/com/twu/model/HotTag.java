package com.twu.model;

public class HotTag {

    private String name;

    private int tickets;

    private Boolean isSuperHot;

    private int payMoney;

    private int payPosition;

    public HotTag(String name) {
        this.name = name;
        this.tickets = 0;
        this.isSuperHot = false;
        this.payMoney = -1;
        this.payPosition = -1;
    }

    public int getPayPosition() {
        return payPosition;
    }

    public void setPayPosition(int payPosition) {
        this.payPosition = payPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTickets() {
        return tickets;
    }

    public void addTickets(int tickets) {
        this.tickets += tickets;
    }

    public Boolean getSuperHot() {
        return this.isSuperHot;
    }

    public void setSuperHot(Boolean superHot) {
        this.isSuperHot = superHot;
    }

    public int getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(int payMoney) {
        this.payMoney = payMoney;
    }
}
