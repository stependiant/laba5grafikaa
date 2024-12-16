package step;

/**
 * Абстрактный класс, описывающий тело в трехмерном пространстве.
 */
public abstract class Solid {
    abstract double[][] getVertices();
    abstract int[][] getEdges();
}
