package net.robobalasko.dfa.core;

import java.util.List;

public class Automaton {

    private List<StateNode> nodes;

    /**
     * Constructor.
     *
     * @param nodes a linked list of nodes comprising the automaton
     */
    public Automaton(List<StateNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * Checks whether the input can be successfully run through the DFA.
     *
     * @param string the input string to be checked.
     *
     * @return true, if the input string is can be run through this DFA.
     * @throws StartNodeMissingException if there is no start node in the automaton.
     * @throws NodeConnectionMissingException if the following connected node
     *                                        cannot be found and the DFA is not in an accepting state
     */
    public boolean acceptsString(String string) throws StartNodeMissingException, NodeConnectionMissingException {
        char[] chars = string.toCharArray();
        StateNode currentNode = getStartNode();

        for (int i = 0; i < chars.length; i++) {
            if (currentNode.getName() != chars[i]) {
                return false;
            }

            if (currentNode.isAcceptNode() && i == chars.length - 1) {
                return true;
            }

            if (i + 1 >= chars.length) {
                return false;
            }

            if (currentNode.isConnectedTo(chars[i + 1])) {
                currentNode = currentNode.getNodeConnectedVia(chars[i + 1]);
            }
        }

        return false;
    }

    /**
     * Checks whether the automaton contains a start node.
     *
     * @return true, if it contains a start node.
     */
    public boolean hasStartNode() {
        for (StateNode node : nodes) {
            if (node.isStartNode()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the start state node of the automaton.
     *
     * @return the start state node.
     * @throws StartNodeMissingException if there has not yet been a start state node added
     */
    public StateNode getStartNode() throws StartNodeMissingException {
        for (StateNode node : nodes) {
            if (node.isStartNode()) return node;
        }

        throw new StartNodeMissingException();
    }

    /**
     * Adds a new node to the list of nodes of the automaton.
     *
     * @param node the node to be added to the list.
     */
    public void addStateNode(StateNode node) {
        nodes.add(node);
    }

    public List<StateNode> getNodes() {
        return nodes;
    }
}
