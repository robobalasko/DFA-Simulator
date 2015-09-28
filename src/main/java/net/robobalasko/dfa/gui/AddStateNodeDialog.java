package net.robobalasko.dfa.gui;

import net.robobalasko.dfa.core.Automaton;
import net.robobalasko.dfa.core.exceptions.MultipleStartNodesException;
import net.robobalasko.dfa.core.StateNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public AddStateNodeDialog(Frame owner, boolean modal, final Automaton automaton, final Point mousePosition) {
        super(owner, modal);
        this.automaton = automaton;

        setTitle("Add a new state node");

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
        containerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(containerPanel);

        final JComboBox charactersCombo = new JComboBox(allowedChars);
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

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StateNode node = new StateNode(((String) charactersCombo.getSelectedItem()).charAt(0),
                        automaton.hasStartNode() ? false : startStateCheck.isSelected(),
                        acceptStateCheck.isSelected(),
                        new LinkedList<StateNode>(),
                        mousePosition);
                try {
                    automaton.addStateNode(node);
                    setVisible(false);
                } catch (MultipleStartNodesException e1) {
                    JOptionPane.showMessageDialog(AddStateNodeDialog.this, "You cannot have multiple start nodes.");
                }
            }
        });

        buttonsPanel.add(buttonAdd);

        JButton buttonCancel = new JButton("Cancel");

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        buttonsPanel.add(buttonCancel);

        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    }

}
