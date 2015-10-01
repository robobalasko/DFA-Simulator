package net.robobalasko.dfa.core;


import net.robobalasko.dfa.core.exceptions.MultipleStartNodesException;
import net.robobalasko.dfa.core.exceptions.NodeConnectionException;

public class NodeConnectionState {

    private boolean connecting;
    private StateNode startNode;
    private StateNode endNode;

    /**
     * Resets the state of user's connecting action to its default state.
     */
    public void resetConnectionState() {
        connecting = false;
        startNode = null;
        endNode = null;
        if (startNode != null) startNode.setActive(false);
        if (endNode != null) endNode.setActive(false);
    }

    /**
     * Connects two nodes to create a relation.
     *
     * @throws MultipleStartNodesException if both of the nodes are marked as start nodes
     * @throws NodeConnectionException if one of the nodes is null
     */
    public void connectNodes()
            throws MultipleStartNodesException, NodeConnectionException {
        if (startNode == null || endNode == null) {
            throw new NodeConnectionException();
        }
        startNode.addConnection(endNode);
    }

    /**
     * @return true, if the user is currently connecting two nodes
     */
    public boolean isConnecting() {
        return connecting;
    }

    /**
     * Sets the state based on whether the user is currently connecting nodes.
     *
     * @param connecting
     */
    public void setConnecting(boolean connecting) {
        this.connecting = connecting;
    }

    /**
     * @return the start node StateNode object.
     */
    public StateNode getStartNode() {
        return startNode;
    }

    /**
     * Sets the start node of the connection
     * and makes it an active node.
     *
     * @param startNode the start node StateNode object.
     */
    public void setStartNode(StateNode startNode) {
        this.startNode = startNode;
        this.startNode.setActive(true);
        this.connecting = true;
    }

    /**
     * @return StateNode object of the end node.c s
     */
    public StateNode getEndNode() {
        return endNode;
    }

    /**
     * Sets the end node of the connection and activates it.
     *
     * @param endNode StateNode object of the end node.
     */
    public void setEndNode(StateNode endNode) {
        this.endNode = endNode;
        this.endNode.setActive(true);
    }
}
