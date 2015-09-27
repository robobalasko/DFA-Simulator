package net.robobalasko.dfa.core;

import java.awt.*;
import java.util.List;

public class StateNode {

    public final static int NODE_SIZE = 40;
    public final static int SPEC_NODE_OFFSET = 4;

    private final char name;
    private final boolean startNode;
    private final boolean acceptNode;
    private final List<StateNode> connections;
    private final Point position;

    /**
     * Constructor.
     *  @param name the name of the node
     * @param startNode specifies whether this node is the starting node
     * @param acceptNode specified whether this node is an accepting node
     * @param connections a linked list of nodes connected to this one
     * @param position
     */
    public StateNode(char name, boolean startNode, boolean acceptNode, List<StateNode> connections, Point position) {
        this.name = name;
        this.startNode = startNode;
        this.acceptNode = acceptNode;
        this.connections = connections;
        this.position = position;
    }

    /**
     * Adds a new node to the connections of the current one.
     *
     * @param node node to be connected to the current one.
     */
    public void addConnection(StateNode node) {
        connections.add(node);
    }

    /**
     * Checks whether this node is connected to another via the specified character.
     *
     * @param c the character which is used to check for connection.
     *
     * @return true, if a connection through the character exists.
     */
    public boolean isConnectedTo(char c) {
        for (StateNode connection : connections) {
            if (connection.getName() == c) return true;
        }

        return false;
    }

    /**
     * Gets the state node that is connected and accessible via the specified character.
     *
     * @param c the character through which the node should be accessible.
     *
     * @return the node that is connected via the specified character.
     * @throws NodeConnectionMissingException if the connected node has not been found.
     */
    public StateNode getNodeConnectedVia(char c) throws NodeConnectionMissingException {
        for (StateNode connection : connections) {
            if (connection.getName() == c) {
                return connection;
            }
        }

        throw new NodeConnectionMissingException();
    }

    /**
     * Returns the name of the state node.
     *
     * @return the character this state node represents.
     */
    public char getName() {
        return name;
    }

    /**
     * Checks whether this node is a start node.
     *
     * @return true, if it is a start node.
     */
    public boolean isStartNode() {
        return startNode;
    }

    /**
     * Checks whether this node is an accepting state node.
     *
     * @return true, if it is an accepting state node.
     */
    public boolean isAcceptNode() {
        return acceptNode;
    }

    /**
     * Returns the nodes that this node connects to.
     *
     * @return a list of state nodes.
     */
    public List<StateNode> getConnections() {
        return connections;
    }

    public Point getPosition() {
        return position;
    }
}
