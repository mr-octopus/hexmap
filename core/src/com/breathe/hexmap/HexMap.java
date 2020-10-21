package com.breathe.hexmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class HexMap {

    public static final float HEX_WIDTH = 32;
    public static final float HEX_HEIGHT = 34;
    public static final float HEX_HALF_HEIGHT = HexMap.HEX_HEIGHT/2;
    public static final float HEX_HALF_WIDTH = HexMap.HEX_WIDTH/2;
    public static final float HEX_QUARTER_HEIGHT = HEX_HEIGHT/4;
    public static final float HEX_QUARTER_WIDTH = HEX_WIDTH/4;

    public static final int GRASS = 1;
    public static final int WATER = 2;
    public static final int MOUNTAINS = 3;
    public static final int FOREST = 4;
    public static final int DESERT = 5;
    public static final int SETTLEMENT = 6;



    //Elements in two-dimensional arrays are commonly referred by x[i][j] where
    // - ‘i’ is the row number
    // - ‘j’ is the column number.

    private HexCell[][] hexes = new HexCell[20][20];

    public HexMap(TiledMapTileLayer objectLayer){
        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < 20; i++) {

                int y = Math.round((j * (HexMap.HEX_QUARTER_HEIGHT*3)) + HexMap.HEX_HALF_HEIGHT);
                if (j % 2 == 0) {
                    TiledMapTileLayer.Cell cell = objectLayer.getCell(i, j);
                    int x = Math.round((i * HexMap.HEX_WIDTH) + HexMap.HEX_HALF_HEIGHT);
                    hexes[j][i] = new HexCell(cell.getTile().getId(), x, y, i, j);
                } else {
                    TiledMapTileLayer.Cell cell = objectLayer.getCell(i, j);
                    int x = Math.round((i * HexMap.HEX_WIDTH)+HexMap.HEX_WIDTH);
                    hexes[j][i] = new HexCell(cell.getTile().getId(), x, y, i, j);
                }
            }
        }

    }

    public HexCell getCell(int i, int j) {
        return hexes[j][i];
    }

    public List<HexCell> getCircleForCell(HexCell cell){
        List<HexCell> circle = new ArrayList<>();
        circle.add(hexes[cell.j-1][cell.i]);
        circle.add(hexes[cell.j-1][cell.i-1]);
        circle.add(hexes[cell.j][cell.i-1]);
        circle.add(hexes[cell.j][cell.i+1]);
        circle.add(hexes[cell.j+1][cell.i]);
        circle.add(hexes[cell.j+1][cell.i-1]);

        return circle;
    }


    public HexCell getHexCellForPos(int x, int y) {

        Vector2 mousePos = new Vector2(x, Gdx.graphics.getHeight() - y);
        HexCell cell = hexes[0][0];
        float dist = 1000;
        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < 20; i++) {
                HexCell tempCell = hexes[j][i];
                float currentDst = mousePos.dst(tempCell.x,tempCell.y);
                if(currentDst < dist){
                    dist = currentDst;
                    cell = tempCell;
                }

            }
        }

        return cell;
    }

}
