package step.test;

import javax.swing.*;
import java.awt.*;

public class ProjectionExample extends JPanel {
    private double[][] vertices = {
            {-1,-1,-1}, {1,-1,-1}, {1,1,-1}, {-1,1,-1}, // Задняя грань
            {-1,-1, 1}, {1,-1, 1}, {1,1, 1}, {-1,1, 1}  // Передняя грань
    };

    private int[][] edges = {
            {0,1},{1,2},{2,3},{3,0},  // задняя грань
            {4,5},{5,6},{6,7},{7,4},  // передняя грань
            {0,4},{1,5},{2,6},{3,7}   // боковые ребра
    };

    // Параметры проекции
    private double settings = 5.0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Центральная проекция: x' = (d*x)/(z+d), y' = (d*y)/(z+d)
        // Предположим наблюдатель смотрит вдоль оси Z, d > 0

        for (int[] edge : edges) {
            double[] v1 = vertices[edge[0]];
            double[] v2 = vertices[edge[1]];

            double x1p = (settings * v1[0]) / (v1[2] + settings);
            double y1p = (settings * v1[1]) / (v1[2] + settings);
            double x2p = (settings * v2[0]) / (v2[2] + settings);
            double y2p = (settings * v2[1]) / (v2[2] + settings);

            int x1s = (int)(width/2 + x1p*width/4);
            int y1s = (int)(height/2 - y1p*height/4);
            int x2s = (int)(width/2 + x2p*width/4);
            int y2s = (int)(height/2 - y2p*height/4);

            g.drawLine(x1s, y1s, x2s, y2s);
        }
    }
}
