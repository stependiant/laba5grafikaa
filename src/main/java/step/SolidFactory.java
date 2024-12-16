package step;

public class SolidFactory {
    public static Solid createTetrahedron() {
        return new Solid() {
            private double[][] vertices = {
                    { 1,  1,  1},
                    { 1, -1, -1},
                    {-1,  1, -1},
                    {-1, -1,  1}
            };
            private int[][] edges = {
                    {0,1},{0,2},{0,3},{1,2},{2,3},{3,1}
            };
            @Override
            double[][] getVertices() { return vertices; }
            @Override
            int[][] getEdges() { return edges; }
        };
    }

    public static Solid createOctahedron() {
        return new Solid() {
            private double[][] vertices = {
                    { 0,  0,  1},
                    { 1,  0,  0},
                    { 0,  1,  0},
                    {-1,  0,  0},
                    { 0, -1,  0},
                    { 0,  0, -1}
            };
            private int[][] edges = {
                    {0,1},{0,2},{0,3},{0,4},
                    {5,1},{5,2},{5,3},{5,4},
                    {1,2},{2,3},{3,4},{4,1}
            };
            @Override
            double[][] getVertices() { return vertices; }
            @Override
            int[][] getEdges() { return edges; }
        };
    }
}
