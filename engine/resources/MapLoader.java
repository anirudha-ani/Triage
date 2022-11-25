package engine.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {

    String mapPath;

    ArrayList<String> mapLayout = new ArrayList<>();
    char[][] mapLayoutArray;
    int maxRow = 0;
    int maxColumn = 0;


    public MapLoader(String mapPath) {
        this.mapPath = mapPath;
        setMapLayout();
    }

    public void setMapLayout() {
        try {
            File file = new File(mapPath);
            Scanner sc = new Scanner(file);


            while (sc.hasNextLine()) {
                mapLayout.add(sc.nextLine());
                maxColumn = Math.max(maxColumn, mapLayout.get(maxRow).length());
                maxRow++;
            }
            mapLayoutArray = new char[maxRow][maxColumn];
            for (int i = 0; i < mapLayout.size(); i++) {
                String row = mapLayout.get(i);

                for (int j = 0; j < row.length(); j++) {
                    mapLayoutArray[i][j] = row.charAt(j);
                }
            }

        } catch (Exception e) {
            System.out.println("Map File loading error" + e);
        }
    }

    public ArrayList<String> getMapLayout() {
        return mapLayout;
    }

    public char[][] getMapLayoutArray() {
        return mapLayoutArray;
    }
}
