package com.example.java_fx_hex_game;

import javafx.scene.paint.Color;

public abstract class Player {
    protected Color color;

    public Color getColor() {
        return color;
    }

    static class RedPlayer extends Player {
        public RedPlayer() {
            this.color = Color.rgb(255, 59, 48);
        }
    }

    static class BluePlayer extends Player {
        public BluePlayer() {
            this.color = Color.rgb(88, 86, 214);
        }
    }
}