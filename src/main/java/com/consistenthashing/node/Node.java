package com.consistenthashing.node;

import java.util.Objects;

/**
 * This class will represent the Server-Node on the token ring.
 * This is the simplest class having just the identifier of the Node
 * and remaining equals, hashCode and toString
 */
public class Node {
    private final String identifier;

    public Node(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(identifier, node.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier);
    }

    @Override
    public String toString() {
        return "Node{" + "identifier='" + identifier + '\'' + '}';
    }
}
