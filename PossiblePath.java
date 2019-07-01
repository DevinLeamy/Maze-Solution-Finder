//Depth First Search

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

public class PossiblePath {
    public static ArrayList<String> getMoves(int[][] maze, String pos){
        int currentRow = Integer.parseInt(pos.split(" ")[0]);
        int currentCol = Integer.parseInt(pos.split(" ")[1]);
        int[] moveCol = {0, 1, -1, 0}; //Right to Left or Vice Versa
        int[] moveRow = {1, 0, 0, -1}; //Up
        ArrayList<String> moves = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            if ((currentRow + moveRow[i]) < maze.length && (currentRow + moveRow[i]) >= 0){
                if ((currentCol + moveCol[i]) < maze.length && (currentCol + moveCol[i]) >= 0){
                    if (maze[(currentRow + moveRow[i])][(currentCol + moveCol[i])] != 1) {
                        moves.add((currentRow + moveRow[i]) + " " + (currentCol + moveCol[i]));
                    }
                }
            }
        }
        return moves;
    }
    public static boolean findPath(int[][] maze, String current, String destination, ArrayList<String> visited, int mark){
        if (visited.contains(current)){
            return false;
        }
        visited.add(current);
        if (current.equals(destination)){
            return true;
        }
        for (String pos: getMoves(maze, current)){
            if (findPath(maze, pos, destination, visited, mark)){
                int row = Integer.parseInt(pos.split(" ")[0]);
                int col = Integer.parseInt(pos.split(" ")[1]);
                maze[row][col] = mark;
                return true;
            }
        }
        return false;
    }
    public static String[] findStartAndEnd(int[][] maze, int mark){
        String[] returnValue = new String[2];
        for (int i = 0; i < (maze.length); i++){
            if (maze[0][i] == 0){
                maze[0][i] = mark;
                returnValue[0] = 0 + " " + i;
            }
            if (maze[maze.length - 1][i] == 0){
                returnValue[1] = (maze.length - 1) + " " + i;
            }
        }
        return returnValue;
    }
    public static void main(String[] args){
        int[][] maze = {{1, 1, 1, 1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 1, 1, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 0, 1, 1, 0, 1, 1},
                        {1, 0, 0, 0, 1, 0, 1, 1},
                        {1, 0, 1, 0, 1, 0, 0, 1},
                        {1, 0, 1, 1, 1, 1, 1, 1}};
        int mark = 9;
        String[] SandE = findStartAndEnd(maze, mark);
        String start = SandE[0];
        String end = SandE[1];
        System.out.println(start);
        System.out.println(end);
        ArrayList<String> visited = new ArrayList<>();
        System.out.println(findPath(maze, start, end, visited, mark));
        for (int[] row: maze){
            System.out.println(Arrays.toString(row));
        }

        int imgSize = 400;
        BufferedImage image = null;
        File img = null;


        try {
            img = new File("//Users//Devin//Desktop//Maze.jpg");
            image = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(img);
            System.out.println("Reading Complete");
            Color myPath = new Color(255, 255, 255);
            Color myWall = new Color(0, 0, 0);
            Color otherPath = new Color(255, 0, 255);
            int rgb = myPath.getRGB();
            int wall = myWall.getRGB();
            int path = otherPath.getRGB();
            int sizeOfBlock = (imgSize / (maze.length));

            for (int i = 0; i < maze.length; i++){
                for (int j = 0; j < maze.length;j++){
                    if (maze[i][j] == mark){
                        for (int x = (i*sizeOfBlock); x < ((i+1)*sizeOfBlock);x++){
                            for (int y = (j * sizeOfBlock); y < ((j+1)*sizeOfBlock); y++){
                                image.setRGB(y, x, rgb);
                            }
                        }
                    }
                    if (maze[i][j] == 1){
                        for (int x = (i*sizeOfBlock); x < ((i+1)*sizeOfBlock);x++){
                            for (int y = (j * sizeOfBlock); y < ((j+1)*sizeOfBlock); y++){
                                image.setRGB(y, x, wall);
                            }
                        }
                    }
                    if (maze[i][j] == 0){
                        for (int x = (i*sizeOfBlock); x < ((i+1)*sizeOfBlock);x++){
                            for (int y = (j * sizeOfBlock); y < ((j+1)*sizeOfBlock); y++){
                                image.setRGB(y, x, path);
                            }
                        }
                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error " + e );
        }
        try {
            img = new File("//Users//Devin//Desktop//Output.jpg");
            ImageIO.write(image, "jpg", img);
            System.out.println("Writing Complete");
        } catch (IOException e){
            System.out.println("Error " + e);
        }
    }
}
