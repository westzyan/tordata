package com.zyan.tordata.domain;

public class Node {
    private int id;
    private String name;
    private double symbolSize;
    private double x;
    private double y;
    private double value;
    private int category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(double symbolSize) {
        this.symbolSize = symbolSize;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Nodes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbolSize=" + symbolSize +
                ", x=" + x +
                ", y=" + y +
                ", value=" + value +
                ", category=" + category +
                '}';
    }
}
