package com.consistenthashing.hashing;

import com.consistenthashing.node.Node;

import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashRing {

    // Why treeMap to maintain order of hash values on the node.
    private final SortedMap<Integer, Node> ring = new TreeMap<>();
    private final HashFunction hashFunction;
    private final int virtualNodes;

    public ConsistentHashRing(HashFunction hashFunction, int virtualNodes) {
        this.hashFunction = hashFunction;
        this.virtualNodes = virtualNodes;
    }

    /**
     * Whenever we are adding a new node, we should add all the virtual nodes
     * of the respective server in the ring
     */
    public void addNode(Node node) {
        // We will place 0 to virtualNodes on the ring
        for (int i = 0; i < virtualNodes; i++) {
            String virtualNodeId = node.getIdentifier() + "#" + i;
            int hashOfVirtualNode = hashFunction.hash(virtualNodeId);
            ring.put(hashOfVirtualNode, node);
        }
    }

    /**
     * whenever we have to remove the node, all the virtual nodes have to be removed as well.
     */
    public void removeNode(Node node) {
        for (int i = 0; i < virtualNodes; i++) {
            String virtualNodeId = node.getIdentifier() + "#" + i;
            int hashOfVirtualNode = hashFunction.hash(virtualNodeId);
            ring.remove(hashOfVirtualNode);
        }
    }

    public Node getServerForKey(String key) {
        if (ring.isEmpty()) {
            // You need to have at-least one server
            return null;
        }
        int hash = hashFunction.hash(key);

        // Find the next bigger hashValue in the ring, which will be the node's hash responsible
        // for that key
        // TailMap helps find the nearest node in the ring in the clockwise direction, returning
        // all nodes whose hash value is greater than the given hashValue
        SortedMap<Integer, Node> tailMap = ring.tailMap(hash);

        Integer serverNodeHash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        return ring.get(serverNodeHash);
    }
}
