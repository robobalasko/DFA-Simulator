package net.robobalasko.dfa.gui;

import net.robobalasko.dfa.core.Automaton;
import net.robobalasko.dfa.core.exceptions.NodeConnectionMissingException;
import net.robobalasko.dfa.core.exceptions.StartNodeMissingException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Automaton automaton;

    private JButton checkButton;

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

        JPanel checkInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        containerPanel.add(checkInputPanel);

        JTextField inputText = new JTextField(40);
        Document document = inputText.getDocument();
        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkButton.setEnabled(e.getDocument().getLength() > 0);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkButton.setEnabled(e.getDocument().getLength() > 0);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkButton.setEnabled(e.getDocument().getLength() > 0);
            }
        });
        checkInputPanel.add(inputText);

        checkButton = new JButton("Check");
        checkButton.setEnabled(false);
        checkButton.addActionListener(al -> {
            try {
                JOptionPane.showMessageDialog(this,
                        automaton.acceptsString(inputText.getText())
                                ? "Input accepted."
                                : "Input rejected.");
            } catch (StartNodeMissingException e) {
                JOptionPane.showMessageDialog(this, "Missing start node.");
            } catch (NodeConnectionMissingException e) {
                JOptionPane.showMessageDialog(this, "Not a good string. Automat doesn't accept it.");
            }
        });
        checkInputPanel.add(checkButton);

        setResizable(false);
        setVisible(true);
        pack();
    }
}
