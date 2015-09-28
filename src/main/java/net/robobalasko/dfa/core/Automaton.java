package net.robobalasko.dfa.core;

import javax.swing.plaf.nimbus.State;
import javax.swing.text.Position;
import java.awt.*;
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
            // If the current node doesn't have the right letter, discard the string
            if (currentNode.getName() != chars[i]) {
                return false;
            }

            // If we're at the end and the node is an accept state, it's ok
            if (currentNode.isAcceptNode() && i == chars.length - 1) {
                return true;
            }

            // If we're gonna overflow the array, discard the string
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
     * Searches for the node with the specified position.
     *
     * @param searchedPosition the position of the mouse cursor.
     *
     * @return StateNode object if a node is found; null otherwise.
     */
    public StateNode getNodeByPosition(Point searchedPosition) {
        for (StateNode node : nodes) {
            if (isNodeHovered(node, searchedPosition)) {
                return node;
            }
        }

        return null;
    }

    /**
     * Checks whether the specified node is hovered.
     *
     * @param node the node to be checked.
     * @param searchedPosition the position of the mouse cursor.
     *
     * @return true, if the specified node is hovered.
     */
    public boolean isNodeHovered(StateNode node, Point searchedPosition) {
        Point currentNodePosition = node.getPosition();

        return searchedPosition.x >= currentNodePosition.x
                && searchedPosition.y >= currentNodePosition.y
                && searchedPosition.x <= (currentNodePosition.x + StateNode.NODE_SIZE)
                && searchedPosition.y <= (currentNodePosition.y + StateNode.NODE_SIZE);
    }

    /**
     * @return true, if the automaton has a hovered node.
     */
    public boolean hasHoveredNode() {
        for (StateNode node : nodes) {
            if (node.isHovered()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Clears hovered state on all nodes.
     */
    public void unhoverAllNodes() {
        for (StateNode node : nodes) {
            node.setHovered(false);
        }
    }

    /**
     * Marks the found node as hovered so that it can be displayed
     * with a red color on the canvas.
     *
     * @param mousePosition the position of the cursor.
     */
    public void markHoveredNode(Point mousePosition) {
        unhoverAllNodes();

        StateNode node = getNodeByPosition(mousePosition);

        if (node != null) {
            node.setHovered(true);
        }
    }

    /**
     * Adds a new node to the list of nodes of the automaton.
     *
     * @param node the node to be added to the list.
     */
    public void addStateNode(StateNode node)
            throws MultipleStartNodesException {
        if (node.isStartNode() && this.hasStartNode()) {
            throw new MultipleStartNodesException();
        }

        nodes.add(node);
    }

    public List<StateNode> getNodes() {
        return nodes;
    }
}
