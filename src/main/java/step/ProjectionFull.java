package step;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectionFull extends JFrame {
    private RenderPanel renderPanel;
    private JComboBox<String> solidComboBox;
    private JComboBox<String> projectionComboBox;

    public ProjectionFull() {
        super("Лабораторная работа: Проективные преобразования");

        // Панель для рисования
        renderPanel = new RenderPanel();

        // Выбор тела: тетраэдр или октаэдр
        solidComboBox = new JComboBox<>(new String[]{"Тетраэдр", "Октаэдр"});
        solidComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String)solidComboBox.getSelectedItem();
                if ("Тетраэдр".equals(selected)) {
                    renderPanel.setSolid(SolidFactory.createTetrahedron());
                } else if ("Октаэдр".equals(selected)) {
                    renderPanel.setSolid(SolidFactory.createOctahedron());
                }
                renderPanel.repaint();
            }
        });

        // Выбор проекции: Центральная или Ортографическая
        projectionComboBox = new JComboBox<>(new String[]{"Центральная", "Ортографическая"});
        projectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String)projectionComboBox.getSelectedItem();
                if ("Центральная".equals(selected)) {
                    renderPanel.setProjectionType(RenderPanel.ProjectionType.CENTRAL);
                } else if ("Ортографическая".equals(selected)) {
                    renderPanel.setProjectionType(RenderPanel.ProjectionType.ORTHO);
                }
                renderPanel.repaint();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Выберите тело:"));
        controlPanel.add(solidComboBox);
        controlPanel.add(new JLabel("Тип проекции:"));
        controlPanel.add(projectionComboBox);

        add(controlPanel, BorderLayout.NORTH);
        add(renderPanel, BorderLayout.CENTER);

        // Устанавливаем начальные значения
        solidComboBox.setSelectedIndex(0);
        projectionComboBox.setSelectedIndex(0);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
