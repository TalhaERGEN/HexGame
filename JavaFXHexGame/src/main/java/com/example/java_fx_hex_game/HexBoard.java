package com.example.java_fx_hex_game;

public class HexBoard {

    private int size;
    private HexagonCell[][] board;

    public HexBoard(int size) {
        this.size = size;
        createBoard();
    }

    public HexagonCell[][] getArray() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public HexagonCell getHexagonCell(double centerX, double centerY) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Hatalı karşılaştırmaların düzeltilmesi
                if (Math.abs(board[i][j].getCenterX() - centerX) < 0.01 && Math.abs(board[i][j].getCenterY() - centerY) < 0.01) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    public void createBoard() {
        board = new HexagonCell[size][size];
        double radius = 270.0 / size; // Altıgenin yarıçapı
        double startX = 400.0 / size; // İlk altıgenin başlangıç X koordinatı
        double startY = 400.0 / size; // İlk altıgenin başlangıç Y koordinatı
        // Altıgen ızgaranın oluşturulması
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Altıgenin merkez koordinatlarının hesaplanması
                double offsetX = col * (radius * Math.sqrt(3)) + row * (radius * Math.sqrt(3)) / 2;
                double offsetY = row * (radius * 1.5);
                double centerX = startX + offsetX;
                double centerY = startY + offsetY;
                HexagonCell hexCell = new HexagonCell(centerX, centerY, radius);
                board[row][col] = hexCell;
            }
        }
    }
}