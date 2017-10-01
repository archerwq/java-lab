package com.weibo.keeplooking.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Well known Hanoi Tower problem.
 * 
 * @author Johnny
 * 
 */
public class HanoiTowerProblem {

    public static void main(String[] args) {
        System.out.print("Please input disk count: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        int diskCount = 0;
        try {
            diskCount = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new HanoiTowerProblem().hannoi(diskCount, 'A', 'B', 'C');
    }

    /**
     * Caculate the movements of n disk Hanoi Tower problem.
     * 
     * @param diskCount
     *        count of disks
     * @param rod1
     *        name of rod#1
     * @param rod2
     *        name of rod#2
     * @param rod3
     *        name of rod#3
     */
    public void hannoi(int diskCount, char rod1, char rod2, char rod3) {
        if (diskCount == 2) {
            move(rod1, rod2);
            move(rod1, rod3);
            move(rod2, rod3);
        } else {
            hannoi(diskCount - 1, rod1, rod3, rod2);
            move(rod1, rod3);
            hannoi(diskCount - 1, rod2, rod1, rod3);
        }
    }

    private void move(char rodA, char rodB) {
        System.out.println("Moving disk from rod" + rodA + " to rod" + rodB
                + "...");
    }
}
