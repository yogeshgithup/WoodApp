package com.example.yogeshchawla.woodmeasure;

/**
 * Created by Yogesh Chawla on 6/29/2018.
 */

public class TablePojo {
    int sr;
    double length,height,thickness,cubicfeet;

    public TablePojo(int sr, double length, double height, double thickness, double cubicfeet) {
        this.sr = sr;
        this.length = length;
        this.height = height;
        this.thickness = thickness;
        this.cubicfeet = cubicfeet;
    }

    public TablePojo() {
    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getCubicfeet() {
        return cubicfeet;
    }

    public void setCubicfeet(double cubicfeet) {
        this.cubicfeet = cubicfeet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TablePojo tablePojo = (TablePojo) o;

        return sr == tablePojo.sr;

    }

    @Override
    public int hashCode() {
        return sr;
    }

    @Override
    public String toString() {
        return "TablePojo{" +
                "sr=" + sr +
                ", length=" + length +
                ", height=" + height +
                ", thickness=" + thickness +
                ", cubicfeet=" + cubicfeet +
                '}';
    }
}
