package net.robobalasko.dfa.core;

import net.robobalasko.dfa.gui.MainFrame;

import javax.swing.*;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame(new Automaton(new LinkedList<StateNode>()));
            }
        });
    }

}
