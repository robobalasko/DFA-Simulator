package net.robobalasko.dfa.gui;

import net.robobalasko.dfa.core.Automaton;
import net.robobalasko.dfa.core.StateNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasPanel extends JPanel {

    private static final int GRID_SPACING = 20;

    private final Automaton automaton;

    private boolean connecting;
    private StateNode firstSelected = null;
    private StateNode secondSelected = null;

    public CanvasPanel(MainFrame mainFrame, Automaton automaton) {
        this.automaton = automaton;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!automaton.hasHoveredNode()) {
                    firstSelected = null;
                    secondSelected = null;
                    connecting = false;
                    AddStateNodeDialog addStateNodeDialog = new AddStateNodeDialog(mainFrame, true, automaton, e.getPoint());
                    addStateNodeDialog.setVisible(true);
                } else {
                    StateNode node = automaton.getNodeByPosition(e.getPoint());

                    if (node != null && !connecting) {
                        node.setActive(true);
                        firstSelected = node;
                        connecting = true;
                    } else {
                        node.setActive(true);
                        secondSelected = node;
                        firstSelected.addConnection(secondSelected);
                        firstSelected.setActive(false);
                        secondSelected.setActive(false);
                        firstSelected = null;
                        secondSelected = null;
                        connecting = false;
                    }
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                automaton.markHoveredNode(e.getPoint());
                repaint();
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
                g.setColor(Color.BLUE);
            }

            if (node.isHovered()) {
                g.setColor(Color.RED);
            }

            if (node.isAcceptNode()) {
                if (node.isStartNode() && !node.isHovered()) {
                    g.setColor(Color.MAGENTA);
                }

                if (node.isActive()) {
                    g.setColor(Color.ORANGE);
                }

                g.drawOval(node.getPosition().x, node.getPosition().y, StateNode.NODE_SIZE, StateNode.NODE_SIZE);
                g.drawOval(node.getPosition().x - StateNode.SPEC_NODE_OFFSET,
                        node.getPosition().y - StateNode.SPEC_NODE_OFFSET,
                        StateNode.NODE_SIZE + StateNode.SPEC_NODE_OFFSET * 2,
                        StateNode.NODE_SIZE + StateNode.SPEC_NODE_OFFSET * 2);
                g.drawString(String.valueOf(node.getName()), node.getPosition().x, node.getPosition().y);
            } else {
                if (!node.isHovered() && !node.isAcceptNode() || node.isStartNode()) {
                    g.setColor(Color.BLACK);
                }

                if (node.isActive()) {
                    g.setColor(Color.ORANGE);
                }

                g.drawOval(node.getPosition().x, node.getPosition().y, StateNode.NODE_SIZE, StateNode.NODE_SIZE);
                g.drawString(String.valueOf(node.getName()), node.getPosition().x, node.getPosition().y);
            }
        }

        for (StateNode node : automaton.getNodes()) {
            java.util.List<StateNode> nodesConnections = node.getConnections();

            for (StateNode connection : nodesConnections) {
                g.drawLine(node.getPosition().x + StateNode.NODE_SIZE / 2,
                        node.getPosition().y + StateNode.NODE_SIZE / 2,
                        connection.getPosition().x + StateNode.NODE_SIZE / 2,
                        connection.getPosition().y + StateNode.NODE_SIZE / 2);
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

    public void drawAcceptingNode(StateNode node, Graphics g) {
        //
    }

    public void drawRegularNode(StateNode node, Graphics g) {
        //
    }
}
