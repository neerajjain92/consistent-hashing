package com.consistenthashing.hashing;

import com.consistenthashing.node.Node;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConsistentHashRingTest {

    @Test
    void testAddAndGetNode() {
        HashFunction hashFunction = new Murmur3HashFunction();
        ConsistentHashRing hashRing = new ConsistentHashRing(hashFunction, 10);

        // Add those 10 nodes
        for (int i = 0; i < 3; i++) {
            hashRing.addNode(new Node("Node_" + i));
        }

        Map<String, List<String>> serverHoldingValues = addItemsToTheNodes(hashRing);

        printServerHoldings(serverHoldingValues);

        // Removing
        hashRing.removeNode(new Node("Node_1"));
        serverHoldingValues = addItemsToTheNodes(hashRing);
        printServerHoldings(serverHoldingValues);
    }

    private static Map<String, List<String>> addItemsToTheNodes(ConsistentHashRing hashRing) {
        Map<String, List<String>> serverHoldingValues = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            String key = "testKey_" + i;
            Node responsibleNode = hashRing.getServerForKey(key);
//            System.out.println("Node Responsible for key : " + key + " is "+ responsibleNode);
            assertNotNull(responsibleNode, "Responsible node should not be null");
            serverHoldingValues.putIfAbsent(responsibleNode.getIdentifier(), new ArrayList<>());
            serverHoldingValues.get(responsibleNode.getIdentifier()).add(key);
        }
        return serverHoldingValues;
    }

    private static void printServerHoldings(Map<String, List<String>> serverHoldingValues) {
        for (String key : serverHoldingValues.keySet()) {
            System.out.println(key + " : holding (" + serverHoldingValues.get(key).size() + " ) values");
            System.out.println(serverHoldingValues.get(key));
            System.out.println("=============================================");
        }
    }
}
