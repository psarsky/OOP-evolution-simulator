package proj.presenter;

import proj.Simulation;
import proj.SimulationEngine;
import proj.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Arrays;
import java.util.List;

import static proj.OptionsParser.parse;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label moveDescription;
    @FXML
    private TextField moveList;
    private WorldMap map;


    @FXML
    private void startSimulation() {
        String[] args = moveList.getText().split(" ");
        List<MoveDirection> directions = parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        AbstractWorldMap map = new GrassField(10);
        map.addObserver(this);
        Simulation simulation = new Simulation(positions, directions, map);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        moveDescription.setText("Simulation started with moves: " + Arrays.toString(args));
        engine.runAsyncInThreadPool();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        setWorldMap(worldMap);
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
            moveDescription.setText(message);
        });
    }

    public void drawMap() {
        int boundTop = map.getCurrentBounds().upperRight().getY();
        int boundBottom = map.getCurrentBounds().lowerLeft().getY();
        int boundRight = map.getCurrentBounds().upperRight().getX();
        int boundLeft = map.getCurrentBounds().lowerLeft().getX();
        int mapWidth = boundRight - boundLeft + 1;
        int mapHeight = boundTop - boundBottom + 1;
        int gridWidth = 400;
        int gridHeight = 400;
        int cellSize = Math.min(gridWidth / mapWidth, gridHeight / mapHeight);

        mapGrid.getColumnConstraints().add(new ColumnConstraints(cellSize));
        mapGrid.getRowConstraints().add(new RowConstraints(cellSize));

        Label yx = new Label("y\\x");
        mapGrid.add(yx, 0, 0);
        GridPane.setHalignment(yx, HPos.CENTER);

        for (int i = 0; i < mapWidth; i++) {
            Label label = new Label(Integer.toString(i + boundLeft));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellSize));
            mapGrid.add(label, i + 1, 0);
        }

        for (int i = 0; i < mapHeight; i++) {
            Label label = new Label(Integer.toString(boundTop - i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(cellSize));
            mapGrid.add(label, 0, i + 1);
        }

        for (int i = boundLeft; i <= boundRight; i++) {
            for (int j = boundTop; j >= boundBottom; j--) {
                Vector2d pos = new Vector2d(i, j);
                if (map.objectAt(pos) == null) {
                    mapGrid.add(new Label(" "), i - boundLeft + 1, boundTop - j + 1);
                }
                else {
                    mapGrid.add(new Label(map.objectAt(pos).toString()), i - boundLeft + 1, boundTop - j + 1);
                }
                GridPane.setHalignment(mapGrid.getChildren().getLast(), HPos.CENTER);
            }
        }

        mapGrid.setGridLinesVisible(true);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void setWorldMap(WorldMap worldMap) {
        this.map = worldMap;
    }
}
