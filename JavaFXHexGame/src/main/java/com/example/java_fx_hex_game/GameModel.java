package com.example.java_fx_hex_game;

public class GameModel {

    private HexBoard board;
    private boolean isRedTurn;

    public GameModel(int size) {
        board = new HexBoard(size);
        isRedTurn = true; // Oyuna kırmızı başlasın
    }

    public HexBoard getBoard() {
        return board;
    }

    public boolean isRedTurn() {
        return isRedTurn;
    }

    public boolean isValidMove(double x, double y) {
        return board.getHexagonCell(x, y).getState() == HexagonCell.CellState.EMPTY;
    }

    public boolean makeMove(double x, double y) {
        if (isValidMove(x, y)) {
            board.getHexagonCell(x, y).setState(isRedTurn ? HexagonCell.CellState.RED : HexagonCell.CellState.BLUE);
            isRedTurn = !isRedTurn;
            return true;
        } else {
            return false;
        }
    }

    public boolean isGameEnd() {
        return hasPlayerWon(HexagonCell.CellState.RED) || hasPlayerWon(HexagonCell.CellState.BLUE);
    }

    private boolean hasPlayerWon(HexagonCell.CellState player) {
        boolean[][] visited = new boolean[board.getSize()][board.getSize()];
        if (player == HexagonCell.CellState.RED) {
            for (int row = 0; row < board.getSize(); row++) {
                if (board.getArray()[row][0].getState() == player && !visited[row][0]) {
                    if (dfs(row, 0, player, visited)) {
                        return true;
                    }
                }
            }
        } else if (player == HexagonCell.CellState.BLUE) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getArray()[0][col].getState() == player && !visited[0][col]) {
                    if (dfs(0, col, player, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dfs(int row, int col, HexagonCell.CellState player, boolean[][] visited) {
        if (player == HexagonCell.CellState.RED && col == board.getSize() - 1) {
            return true;
        }
        if (player == HexagonCell.CellState.BLUE && row == board.getSize() - 1) {
            return true;
        }
        visited[row][col] = true;
        int[][] directions = {
                {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}
        };
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (isValid(newRow, newCol) && !visited[newRow][newCol] && board.getArray()[newRow][newCol].getState() == player) {
                if (dfs(newRow, newCol, player, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
    }

    public HexagonCell.CellState defineWinner() {
        if (hasPlayerWon(HexagonCell.CellState.RED)) {
            return HexagonCell.CellState.RED;
        } else if (hasPlayerWon(HexagonCell.CellState.BLUE)) {
            return HexagonCell.CellState.BLUE;
        } else {
            return HexagonCell.CellState.EMPTY; // Oyun bitmemişse veya kazanan yoksa
        }
    }
}