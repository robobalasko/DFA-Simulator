package net.robobalasko.dfa.core;


import net.robobalasko.dfa.core.exceptions.MultipleStartNodesException;
import net.robobalasko.dfa.core.exceptions.NodeConnectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class NodeConnectionStateTest {

    private NodeConnectionState nodeConnectionState;

    @Before
    public void setUp() {
        nodeConnectionState = new NodeConnectionState();
    }

    @Test
    public void testResetConnectionStateSucceeds() {
        nodeConnectionState.resetConnectionState();
        assertNull(nodeConnectionState.getStartNode());
        assertNull(nodeConnectionState.getEndNode());
        assertFalse(nodeConnectionState.isConnecting());
    }

    @Test(expected = NodeConnectionException.class)
    public void testConnectNodesFails() throws MultipleStartNodesException, NodeConnectionException {
        nodeConnectionState.connectNodes();
    }

    @Test
    public void testConnectNodesSucceeds() throws MultipleStartNodesException, NodeConnectionException {
        StateNode firstNode = new StateNode('a', false, false, new LinkedList<StateNode>(), null);
        StateNode secondNode = new StateNode('b', false, false, new LinkedList<StateNode>(), null);
        nodeConnectionState.setStartNode(firstNode);
        nodeConnectionState.setEndNode(secondNode);
        nodeConnectionState.connectNodes();
        assertEquals('a', nodeConnectionState.getStartNode().getName());
        assertEquals('b', nodeConnectionState.getEndNode().getName());
    }

    @Test
    public void testSetStartNode() {
        StateNode startNode = new StateNode('a', false, false, null, null);
        nodeConnectionState.setStartNode(startNode);
        assertEquals('a', nodeConnectionState.getStartNode().getName());
        assertTrue(nodeConnectionState.getStartNode().isActive());
        assertTrue(nodeConnectionState.isConnecting());
    }

    @Test
    public void testSetEndNode() {
        StateNode endNode = new StateNode('b', false, false, null, null);
        nodeConnectionState.setEndNode(endNode);
        assertEquals('b', nodeConnectionState.getEndNode().getName());
        assertTrue(nodeConnectionState.getEndNode().isActive());
    }

    @After
    public void tearDown() {
        nodeConnectionState = null;
    }

}
