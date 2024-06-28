/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : C
 * Group    : 6
 * Members  :
 * 1. 5026231033 - Ayu Alfia Putri
 * 2. 5026231143 - Muhammad Ridho Utomo
 * 3. 5026231208 - Alfan Ghofari Prasanna Firmansyah
 * ------------------------------------------------------
 */

import java.util.Scanner;


public class Player {
    private String userName;
    private int position;


    public Player(String userName) {
        this.userName = userName;
        this.position = 1; // Starting position

    }

    public String getUserName() {
        return userName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //rolldice method
    public int rollDice(int numDice) {
        int total = 0;
        if (numDice == 1) {
            total = (int) (Math.random() * 6) + 1;
        } else if (numDice == 2) {
            total = (int) (Math.random() * 6) + 1 + (int) (Math.random() * 6) + 1;
        }
        return total;
    }

    //move around method
    public void moveAround(int x, int boardSize) {
        if (this.position + x > boardSize)
            this.position = boardSize - ((this.position + x) % boardSize);
        else this.position += x;
    }
}
