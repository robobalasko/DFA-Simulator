package net.robobalasko.dfa.gui;

import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeStatus;
import net.robobalasko.dfa.core.Automaton;
import net.robobalasko.dfa.core.StateNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasPanel extends JPanel {

    private static final int GRID_SPACING = 20;

    private final Automaton automaton;

    public CanvasPanel(MainFrame mainFrame, Automaton automaton) {
        this.automaton = automaton;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddStateNodeDialog addStateNodeDialog = new AddStateNodeDialog(mainFrame, true, automaton, e.getPoint());
                addStateNodeDialog.setVisible(true);
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    @Override
    protected void paintComponent(Graphics g) {
        clearCanvas(g);
        drawLinesGrid(g);

        for (StateNode node : automaton.getNodes()) {
            if (node.isStartNode()) {
                drawStartNode(node, g);
            }
        }
    }

    public void clearCanvas(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void drawLinesGrid(Graphics g) {
        g.setColor(new Color(245, 245, 245));

        for (int i = 0; i < getWidth(); i += GRID_SPACING) {
            g.drawLine(i, 0, i, getHeight());
        }

        for (int i = 0; i < getHeight(); i += GRID_SPACING) {
            g.drawLine(0, i, getWidth(), i);
        }
    }

    public void drawStartNode(StateNode node, Graphics g) {
        g.setColor(Color.BLUE);
        g.drawOval(node.getPosition().x, node.getPosition().y, StateNode.NODE_SIZE, StateNode.NODE_SIZE);
        g.drawOval(node.getPosition().x - StateNode.SPEC_NODE_OFFSET,
                node.getPosition().y - StateNode.SPEC_NODE_OFFSET,
                StateNode.NODE_SIZE + StateNode.SPEC_NODE_OFFSET * 2,
                StateNode.NODE_SIZE + StateNode.SPEC_NODE_OFFSET * 2);
        g.drawString(String.valueOf(node.getName()), node.getPosition().x, node.getPosition().y);
    }
}
