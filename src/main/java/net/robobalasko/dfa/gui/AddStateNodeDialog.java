package net.robobalasko.dfa.gui;

import net.robobalasko.dfa.core.Automaton;
import net.robobalasko.dfa.core.StateNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.util.LinkedList;

public class AddStateNodeDialog extends JDialog {

    private final String[] allowedChars = {
            "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9"
    };

    private final Automaton automaton;

    private JCheckBox acceptStateCheck;

    private JCheckBox startStateCheck;

    public AddStateNodeDialog(Frame owner, boolean modal, Automaton automaton, Point mousePosition) {
        super(owner, modal);
        this.automaton = automaton;

        setTitle("Add a new state node");

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
        containerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(containerPanel);

        JComboBox charactersCombo = new JComboBox(allowedChars);
        charactersCombo.setEditable(false);
        containerPanel.add(charactersCombo);

        JPanel checkboxesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        containerPanel.add(checkboxesPanel);

        acceptStateCheck = new JCheckBox("Accept");
        checkboxesPanel.add(acceptStateCheck);

        if (!automaton.hasStartNode()) {
            startStateCheck = new JCheckBox("Start");
            checkboxesPanel.add(startStateCheck);
        }

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        containerPanel.add(buttonsPanel);

        JButton buttonAdd = new JButton("Add");

        buttonAdd.addActionListener(e -> {
            StateNode node = new StateNode(((String) charactersCombo.getSelectedItem()).charAt(0),
                    acceptStateCheck.isSelected(),
                    automaton.hasStartNode() ? false : startStateCheck.isSelected(),
                    new LinkedList<StateNode>(),
                    mousePosition);
            automaton.addStateNode(node);
            setVisible(false);
        });

        buttonsPanel.add(buttonAdd);

        JButton buttonCancel = new JButton("Cancel");

        buttonCancel.addActionListener(e -> {
            setVisible(false);
        });

        buttonsPanel.add(buttonCancel);

        setResizable(false);
        pack();
    }

}
