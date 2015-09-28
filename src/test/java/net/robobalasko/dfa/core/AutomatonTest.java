package net.robobalasko.dfa.core;

import static org.junit.Assert.*;

import net.robobalasko.dfa.core.exceptions.MultipleStartNodesException;
import net.robobalasko.dfa.core.exceptions.NodeConnectionMissingException;
import net.robobalasko.dfa.core.exceptions.StartNodeMissingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;

public class AutomatonTest {

    private Automaton automaton;

    @Before
    public void setUp() {
        automaton = new Automaton(new LinkedList<>());
    }

    @Test(expected = StartNodeMissingException.class)
    public void testAcceptStringStartNodeMissing()
            throws NodeConnectionMissingException, StartNodeMissingException {
        String input = "abcdef";
        automaton.acceptsString(input);
    }

    @Test
    public void testAcceptStringNodeConnectionMissing()
            throws NodeConnectionMissingException, StartNodeMissingException, MultipleStartNodesException {
        StateNode startNode = new StateNode('a', true, true, new LinkedList<>(), null);
        automaton.addStateNode(startNode);
        String input = "abcdef";
        assertFalse(automaton.acceptsString(input));
    }

    @Test
    public void testHasStartNodeFails() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', false, true, new LinkedList<>(), null);
        automaton.addStateNode(node);
        assertFalse(automaton.hasStartNode());
    }

    @Test
    public void testHasStartNodeSucceeds() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', true, false, new LinkedList<>(), null);
        automaton.addStateNode(node);
        assertTrue(automaton.hasStartNode());
    }

    @Test(expected = StartNodeMissingException.class)
    public void testGetStartNodeFails()
            throws StartNodeMissingException, MultipleStartNodesException {
        StateNode node = new StateNode('a', false, false, new LinkedList<>(), null);
        automaton.addStateNode(node);
        automaton.getStartNode();
    }

    @Test
    public void testGetStartNodeSucceeds()
            throws StartNodeMissingException, MultipleStartNodesException {
        StateNode node = new StateNode('a', true, false, new LinkedList<>(), null);
        automaton.addStateNode(node);
        StateNode foundNode = automaton.getStartNode();
        assertNotNull(foundNode);
    }

    @Test
    public void testGetNodeByPositionFails() throws MultipleStartNodesException {
        Point position = new Point(0, 0);
        StateNode node = new StateNode('a', true, false, null, new Point(200, 200));
        automaton.addStateNode(node);
        assertNull(automaton.getNodeByPosition(position));
    }

    @Test
    public void testGetNodeByPositionSucceeds() throws MultipleStartNodesException {
        Point position = new Point(54, 74);
        StateNode node = new StateNode('a', true, false, null, new Point(0, 0));
        StateNode otherNode = new StateNode('a', false, false, null, new Point(50, 70));
        automaton.addStateNode(node);
        automaton.addStateNode(otherNode);
        StateNode foundNode = automaton.getNodeByPosition(position);
        assertNotNull(foundNode);
        assertEquals(50, foundNode.getPosition().x);
        assertEquals(70, foundNode.getPosition().y);
    }

    @Test
    public void testIsNodeHoveredFails() {
        Point position = new Point(50, 50);
        StateNode node = new StateNode('a', false, false, null, new Point(0, 0));
        assertFalse(automaton.isNodeHovered(node, position));
    }

    @Test
    public void testIsNodeHoveredSucceeds() {
        Point position = new Point(4, 4);
        StateNode node = new StateNode('a', false, false, null, new Point(0, 0));
        assertTrue(automaton.isNodeHovered(node, position));
    }

    @Test
    public void testHasHoveredNodeFails() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', false, false, null, null);
        automaton.addStateNode(node);
        assertFalse(automaton.hasHoveredNode());
    }

    @Test
    public void testHasHoveredNodeSucceeds() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', false, false, null, null);
        node.setHovered(true);
        automaton.addStateNode(node);
        assertTrue(automaton.hasHoveredNode());
    }

    @Test
    public void testUnhoverAllNodes() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', false, false, null, null);
        node.setHovered(true);
        automaton.addStateNode(node);
        automaton.unhoverAllNodes();
        assertFalse(automaton.hasHoveredNode());
    }

    @Test
    public void testMarkHoveredNode() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', false, false, null, new Point(10, 20));
        StateNode otherNode = new StateNode('b', false, false, null, new Point(70, 80));
        automaton.addStateNode(node);
        automaton.addStateNode(otherNode);

        Point mousePosition = new Point(84, 93);
        automaton.markHoveredNode(mousePosition);

        StateNode foundNode = automaton.getNodeByPosition(mousePosition);

        assertEquals(70, foundNode.getPosition().x);
        assertEquals(80, foundNode.getPosition().y);
    }

    @Test(expected = MultipleStartNodesException.class)
    public void testAddNodeFails() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', true, false, null, new Point(10, 20));
        StateNode otherNode = new StateNode('b', true, false, null, new Point(70, 80));
        automaton.addStateNode(node);
        automaton.addStateNode(otherNode);
    }

    @Test
    public void testAddNodeSucceeds() throws MultipleStartNodesException {
        StateNode node = new StateNode('a', false, false, null, new Point(10, 20));
        StateNode otherNode = new StateNode('b', false, false, null, new Point(70, 80));
        automaton.addStateNode(node);
        automaton.addStateNode(otherNode);

        assertEquals(2, automaton.getNodes().size());
    }

    @After
    public void tearDown() {
        automaton = null;
    }

}

