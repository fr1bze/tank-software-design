package ru.mipt.bit.platformer.abstractions.models;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.abstractions.interfaces.Obstacleble;

import javax.swing.tree.TreeModel;
import java.util.HashSet;
import java.util.Set;

public class MapModel {
    private Set<Tree> trees = new HashSet<>();
    private Set<Tank> tanks = new HashSet<>();
    private Tank player;

    private int rowCount;
    private int columnCount;

    public MapModel(Set<Tree> trees, Set<Tank> tanks, Tank player, int rows, int columns) { 
        this.trees = trees;
        this.tanks = tanks;
        this.player = player;
        this.rowCount = rows;
        this.columnCount = columns;
    }

    public MapModel() {

    }

    public MapModel(Set<TreeModel> trees, Set<Tank> tanks, Tank player, int rows, int columns) {
    }

    public Set<Tree> getTrees() {
        return trees;
    }

    public void setTrees(Set<Tree> trees) {
        this.trees.clear();
        this.trees.addAll(trees);
    }

    public Set<Tank> getTanks() {
        return tanks;
    }

    public void setTanks(Set<Tank> tanks) {
        this.tanks.clear();
        this.tanks.addAll(tanks);
    }

    public Set<Obstacleble> getObstacles() {
        Set<Obstacleble> obstacles = new HashSet<>(trees);
        obstacles.addAll(tanks);
        obstacles.add(player);
        return obstacles;
    }


    public void removeTank(Tank tank) {
        tanks.remove(tank);
    }

    public Tank getPlayer() {
        return player;
    }

    public void setPlayer(Tank player) {
        this.player = player;
    }

    public void setMapSize(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public boolean isOutOfBounds(GridPoint2 coordinates) {
        return coordinates.x < 0 || coordinates.y < 0 ||
                coordinates.x >= rowCount || coordinates.y >= columnCount;
    }
}
