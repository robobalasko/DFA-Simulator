package net.robobalasko.dfa.core;

import net.robobalasko.dfa.core.exceptions.MultipleStartNodesException;
import net.robobalasko.dfa.core.exceptions.NodeConnectionMissingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class StateNodeTest {

    private StateNode node;

    @Before
    public void setUp() {
        node = new StateNode('a', true, true, new LinkedList<StateNode>(), new Point(0, 0));
    }

    @Test(expected = MultipleStartNodesException.class)
    public void testAddConnectionFails() throws MultipleStartNodesException {
        StateNode connection = new StateNode('a', true, false, null, null);
        node.addConnection(connection);
    }

    @Test
    public void testAddConnectionSucceeds() throws MultipleStartNodesException {
        StateNode connection = new StateNode('a', false, false, null, null);
        node.addConnection(connection);
    }

    @Test
    public void testIsConnectedToFails() {
        assertFalse(node.isConnectedTo('b'));
    }

    @Test
    public void testIsConnectedToSucceeds() throws MultipleStartNodesException {
        StateNode connection = new StateNode('b', false, false, null, null);
        node.addConnection(connection);
        assertTrue(node.isConnectedTo('b'));
    }

    @Test(expected = NodeConnectionMissingException.class)
    public void testGetConnectedViaFails() throws NodeConnectionMissingException {
        node.getNodeConnectedVia('b');
    }

    @Test
    public void testGetConnectedViaSucceeds() throws NodeConnectionMissingException, MultipleStartNodesException {
        StateNode connection = new StateNode('b', false, false, null, null);
        node.addConnection(connection);
        StateNode foundConnection = node.getNodeConnectedVia('b');
        assertEquals('b', foundConnection.getName());
    }

    @After
    public void tearDown() {
        node = null;
    }

}
