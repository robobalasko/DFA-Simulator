package net.robobalasko.dfa.gui;

import net.robobalasko.dfa.core.Automaton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Automaton automaton;

    public MainFrame(Automaton automaton) throws HeadlessException {
        super("DFA Simulator");
        this.automaton = automaton;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
        containerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(containerPanel);

        CanvasPanel canvasPanel = new CanvasPanel(this, automaton);
        containerPanel.add(canvasPanel);

        setResizable(false);
        setVisible(true);
        pack();
    }
}
