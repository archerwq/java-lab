package com.weibo.keeplooking.amazon;

import java.util.Scanner;

/**
 * Find the first common ansestor for two nodes in a endless complete trinary
 * tree with the node ID of back-and-forth order.
 * 
 * @author Johnny
 */
public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int node1 = in.nextInt();
        int node2 = in.nextInt();
        in.close();
        System.out.println(firstCommonAnsestor(node1, node2));
    }

    /**
     * Return the first common ansestor for the two nodes.
     */
    private static int firstCommonAnsestor(int node1, int node2) {
        int height1 = height(node1);
        int height2 = height(node2);
        int minNode = node1;
        int maxNode = node2;
        if (height1 > height2) {
            minNode = node2;
            maxNode = node1;
        }
        int gap = Math.abs(height1 - height2);
        for (int i = 0; i < gap; i++) {
            maxNode = parent(maxNode);
        }

        while (true) {
            if (minNode == maxNode) {
                return minNode;
            }
            minNode = parent(minNode);
            maxNode = parent(maxNode);
        }
    }

    /**
     * Return the height of the node.
     */
    private static int height(int node) {
        int count = 1;
        int height = 0;
        while (node > count - 1) {
            height++;
            count += countOfNodes(height);
        }
        return height;
    }

    /**
     * Return the parent of the specific node.
     */
    private static int parent(int node) {
        int height = height(node);
        int begin = 0;
        for (int i = 0; i < height; i++) {
            begin += countOfNodes(i);
        }
        int a = 0;
        if (node % 3 == 0) {
            a = node - 2;
        } else {
            a = (node / 3) * 3 + 1;
        }
        int b = (a - begin) / 3;
        int parent = a - b * 4 - 1;
        return parent;
    }

    /**
     * Return the count of nodes in the specific level.
     */
    private static int countOfNodes(int level) {
        int total = 1;
        for (int i = 0; i < level; i++) {
            total *= 3;
        }
        return total;
    }

}
