package com.example.java_fx_hex_game;

import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HexGameGUI extends Application {

    private GameModel gameModel;
    private int boardSize = 11; // Varsayılan tahta boyutu
    private int turnNumber = 1; // Tur sayısı
    private int tileInGame = 0;

    private Pane boardPane = new Pane();
    private Label turnLabel = new Label("Turn: " + turnNumber);
    private Label tileLabel = new Label("Tile in game: " + tileInGame);
    private Label currentPlayerLabel = new Label("Current player: ");
    private VBox vBoxOuter = new VBox();
    private VBox vBoxInner = new VBox();
    private SplitPane splitPane = new SplitPane();
    private ToggleGroup group = new ToggleGroup();
    private RadioButton rb5 = new RadioButton("5x5");
    private RadioButton rb11 = new RadioButton("11x11");
    private RadioButton rb17 = new RadioButton("17x17");
    private Button startButton = new Button("Start");
    private Label notValidLabel = new Label("Geçerli bir hamle yapınız!");

    @Override
    public void start(Stage primaryStage) {
        initializeGameGUI();
        splitPane.setDividerPositions(0.78);
        vBoxOuter.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.22));
        vBoxOuter.minWidthProperty().bind(splitPane.widthProperty().multiply(0.22));
        splitPane.getItems().addAll(boardPane, vBoxOuter);
        Scene scene = new Scene(splitPane, 950, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("HexGame");
        primaryStage.show();
        rb5.setToggleGroup(group);
        rb5.setOnAction(e -> {
            boardSize = 5;
            updateLabels();
        });
        rb11.setToggleGroup(group);
        rb11.setSelected(true);
        rb11.setOnAction(e -> {
            boardSize = 11;
            updateLabels();
        });
        rb17.setToggleGroup(group);
        rb17.setOnAction(e -> {
            boardSize = 17;
            updateLabels();
        });
        rb5.setFont(Font.font("Franklin Gothic Medium", 16));
        rb11.setFont(Font.font("Franklin Gothic Medium", 16));
        rb17.setFont(Font.font("Franklin Gothic Medium", 16));
        currentPlayerLabel.setFont(Font.font("Bernard MT Condensed", 24));
        turnLabel.setFont(Font.font("Bernard MT Condensed", 34));
        tileLabel.setFont(Font.font("Bernard MT Condensed", 24));
        notValidLabel.setFont(Font.font("Bernard MT Condensed", 18));
        startButton.setFont(Font.font("Franklin Gothic Medium", 18));
        notValidLabel.setTextFill(Color.RED);
        notValidLabel.setVisible(false);
        startButton.setOnAction(e -> startGame());
    }

    private void startGame() {
        gameModel = new GameModel(boardSize);
        currentPlayerLabel.setText("Current player: " + (gameModel.isRedTurn() ? "Red" : "Blue"));
        resetTurnNumber();
        resetTileInGame();
        drawBoard();
        updateLabels();
        notValidLabel.setVisible(false); // Yeni oyun başladığında uyarı mesajının gizlenmesi
    }

    private void drawBoard() {
        boardPane.getChildren().clear(); // Önceki tahtanın temizlenmesi
        HexBoard board = gameModel.getBoard();
        HexagonCell[][] cells = board.getArray();
        // Tahtayı arayüze ekle
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                HexagonCell cell = cells[i][j];
                boardPane.getChildren().add(cell);
                // Hücrelere tıklama olayının eklenmesi
                cell.setOnMouseClicked(event -> handleMouseClick(cell));
            }
        }
        int size = board.getSize();
        Line leftRedLine = new Line(cells[0][0].getCenterX() - 335 / size, cells[0][0].getCenterY(), cells[board.getSize() - 1][0].getCenterX() - 335 / size, cells[board.getSize() - 1][0].getCenterY());
        Line rightRedLine = new Line(cells[0][board.getSize() - 1].getCenterX() + 335 / size, cells[0][board.getSize() - 1].getCenterY(), cells[board.getSize() - 1][board.getSize() - 1].getCenterX() + 335 / size, cells[board.getSize() - 1][board.getSize() - 1].getCenterY());
        Line topBlueLine = new Line(cells[0][0].getCenterX(), cells[0][0].getCenterY() - 295 / size, cells[0][board.getSize() - 1].getCenterX(), cells[0][board.getSize() - 1].getCenterY() - 295 / size);
        Line bottomBlueLine = new Line(cells[board.getSize() - 1][0].getCenterX(), cells[board.getSize() - 1][0].getCenterY() + 295 / size, cells[board.getSize() - 1][board.getSize() - 1].getCenterX(), cells[board.getSize() - 1][board.getSize() - 1].getCenterY() + 295 / size);
        leftRedLine.setStrokeWidth(75 / size);
        leftRedLine.setStroke(Color.rgb(255, 59, 48));
        rightRedLine.setStrokeWidth(75 / size);
        rightRedLine.setStroke(Color.rgb(255, 59, 48));
        topBlueLine.setStrokeWidth(75 / size);
        topBlueLine.setStroke(Color.rgb(88, 86, 214));
        bottomBlueLine.setStrokeWidth(75 / size);
        bottomBlueLine.setStroke(Color.rgb(88, 86, 214));
        boardPane.getChildren().addAll(leftRedLine, rightRedLine, topBlueLine, bottomBlueLine);
    }

    public void initializeGameGUI() {
        vBoxInner.setSpacing(5);
        vBoxInner.setAlignment(Pos.BASELINE_LEFT);
        vBoxInner.setPadding(new Insets(10, 10, 10, 10));
        group.getToggles().addAll(rb5, rb11, rb17);
        group.selectToggle(rb11);
        vBoxInner.getChildren().addAll(rb5, rb11, rb17, currentPlayerLabel, turnLabel, tileLabel, notValidLabel, startButton); // currentPlayerLabel burada ekleniyor
        vBoxOuter.getChildren().addAll(vBoxInner);
    }

    private void handleMouseClick(HexagonCell hexcell) {
        if (gameModel.isGameEnd()) {
            HexagonCell.CellState winner = gameModel.defineWinner();
            displayWinner(winner);
            Platform.exit();
        } else {
            if (gameModel.makeMove(hexcell.getCenterX(), hexcell.getCenterY())) {
                notValidLabel.setVisible(false);
                Color playerColor = gameModel.isRedTurn() ? new Player.BluePlayer().getColor() : new Player.RedPlayer().getColor();
                FillTransition fillTransition = new FillTransition(Duration.millis(600), hexcell);
                fillTransition.setFromValue((Color) hexcell.getFill());
                fillTransition.setToValue(playerColor);
                fillTransition.play();
                ScaleTransition st = new ScaleTransition(Duration.millis(200), hexcell);
                st.setByX(0.25);
                st.setByY(0.25);
                st.setAutoReverse(true);
                st.setCycleCount(2);
                st.play();
                hexcell.setFill(playerColor); // Hücrenin oyuncu rengiyle doldurulması
                updateNumbers();
                updateLabels();
                // Oyunun bitip bitmediğinib tekrar kontrolü
                if (gameModel.isGameEnd()) {
                    HexagonCell.CellState winner = gameModel.defineWinner();
                    displayWinner(winner);
                    Platform.exit();
                }
            } else {
                notValidLabel.setVisible(true);
            }
        }
    }

    private void updateLabels() {
        turnLabel.setText("Turn: " + turnNumber);
        currentPlayerLabel.setText("Current player: " + (gameModel.isRedTurn() ? "Red" : "Blue"));
        tileLabel.setText("Tile in game: " + tileInGame);
    }

    private void updateNumbers() {
        turnNumber++;
        tileInGame++;
    }

    public static void displayWinner(HexagonCell.CellState winner) {
        String winnerText = "";
        if (winner == HexagonCell.CellState.RED) {
            winnerText = "Winner: Red";
        } else if (winner == HexagonCell.CellState.BLUE) {
            winnerText = "Winner: Blue";
        } else {
            winnerText = "No winner";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(winnerText);
        alert.showAndWait();
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void resetTurnNumber() {
        turnNumber = 1;
    }

    public int getTileInGame() {
        return tileInGame;
    }

    public void resetTileInGame() {
        tileInGame = 0;
    }

    public static void main(String[] args) {
        launch();
    }
}