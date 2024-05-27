package com.example.java_fx_hex_game;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class HexagonCell extends Polygon {

    public enum CellState {
        EMPTY, RED, BLUE
    }

    private double centerX;
    private double centerY;
    private double radius;
    private CellState state;

    ObservableList<Double> points = getPoints();

    public HexagonCell(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.state = CellState.EMPTY;
        koseNoktalariHesapla();  // Köşe noktalarının hesaplanması ve eklenmesi
        gorunumuAyarla();
    }

    public void koseNoktalariHesapla() {
        points.clear();  // Mevcut noktaların temizlenmesi
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            points.add(x);
            points.add(y);
        }
    }

    public void gorunumuAyarla() {
        // Altıgenin görünümünün ayarlanması
        this.setFill(Color.LIGHTGRAY);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(3);
        Rotate rotate = new Rotate();
        rotate.setAngle(30);
        rotate.setPivotX(centerX);
        rotate.setPivotY(centerY);
        this.getTransforms().add(rotate);
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }
}