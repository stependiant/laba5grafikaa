package step;

import javax.swing.*;
import java.awt.*;

/**
 * Панель для рисования 3D объектов с использованием проекций.
 */
public class RenderPanel extends JPanel {
    public enum ProjectionType { CENTRAL, ORTHO }
    private ProjectionType projectionType = ProjectionType.CENTRAL;
    private Solid solid;

    // Параметры камеры и проекции
    private double d = 2.0;
    private double eyeX = 3, eyeY = 3, eyeZ = 3;
    private double centerX = 0, centerY = 0, centerZ = 0;

    public RenderPanel() {
        this.solid = SolidFactory.createTetrahedron();
    }

    public void setSolid(Solid s) {
        this.solid = s;
    }

    public void setProjectionType(ProjectionType pt) {
        this.projectionType = pt;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (solid == null) return;

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        int w = getWidth();
        int h = getHeight();

        double[][] V = createViewMatrix(eyeX, eyeY, eyeZ, centerX, centerY, centerZ);

        for (int[] edge : solid.getEdges()) {
            double[] v1 = solid.getVertices()[edge[0]];
            double[] v2 = solid.getVertices()[edge[1]];

            double[] v1e = multiplyMatrixVector(V, new double[]{v1[0], v1[1], v1[2], 1});
            double[] v2e = multiplyMatrixVector(V, new double[]{v2[0], v2[1], v2[2], 1});

            double[] v1p, v2p;
            if (projectionType == ProjectionType.CENTRAL) {
                v1p = centralProjection(v1e, d);
                v2p = centralProjection(v2e, d);
            } else {
                v1p = orthographicProjection(v1e);
                v2p = orthographicProjection(v2e);
            }

            int x1s = (int)(w/2 + v1p[0]*w/4);
            int y1s = (int)(h/2 - v1p[1]*h/4);
            int x2s = (int)(w/2 + v2p[0]*w/4);
            int y2s = (int)(h/2 - v2p[1]*h/4);

            g2.drawLine(x1s, y1s, x2s, y2s);
        }
    }

    private double[] centralProjection(double[] ve, double d) {
        double x = ve[0];
        double y = ve[1];
        double z = ve[2];

        double[] vp = new double[3];
        if (Math.abs(z) < 1e-9) z = 1e-9;
        vp[0] = (d * x) / z;
        vp[1] = (d * y) / z;
        vp[2] = 1;
        return vp;
    }

    private double[] orthographicProjection(double[] ve) {
        return new double[]{ve[0], ve[1], 1};
    }

    private double[][] createViewMatrix(double ex, double ey, double ez, double cx, double cy, double cz) {
        double nx = ex - cx;
        double ny = ey - cy;
        double nz = ez - cz;

        double len = Math.sqrt(nx*nx + ny*ny + nz*nz);
        nx /= len; ny /= len; nz /= len;

        double upx = 0, upy = 1, upz = 0;

        double ux = upy*nz - upz*ny;
        double uy = upz*nx - upx*nz;
        double uz = upx*ny - upy*nx;
        double lenU = Math.sqrt(ux*ux + uy*uy + uz*uz);
        ux /= lenU; uy /= lenU; uz /= lenU;

        double vx = ny*uz - nz*uy;
        double vy = nz*ux - nx*uz;
        double vz = nx*uy - ny*ux;

        double[][] M = {
                {ux, uy, uz, 0},
                {vx, vy, vz, 0},
                {nx, ny, nz, 0},
                {0,  0,  0,  1}
        };

        double[][] T = {
                {1,0,0,-ex},
                {0,1,0,-ey},
                {0,0,1,-ez},
                {0,0,0, 1}
        };

        return multiplyMatrices(M, T);
    }

    private double[] multiplyMatrixVector(double[][] M, double[] v) {
        double[] res = new double[4];
        for (int i = 0; i < 4; i++) {
            res[i] = M[i][0]*v[0] + M[i][1]*v[1] + M[i][2]*v[2] + M[i][3]*v[3];
        }

        if (Math.abs(res[3]) > 1e-9) {
            res[0] /= res[3];
            res[1] /= res[3];
            res[2] /= res[3];
            res[3] = 1;
        }
        return res;
    }

    private double[][] multiplyMatrices(double[][] A, double[][] B) {
        double[][] R = new double[4][4];
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                R[i][j] = A[i][0]*B[0][j] + A[i][1]*B[1][j] + A[i][2]*B[2][j] + A[i][3]*B[3][j];
            }
        }
        return R;
    }
}