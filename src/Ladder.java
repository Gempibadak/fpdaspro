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
public class Ladder {
    private int fromPosition;
    private int toPosition;

    public Ladder(int x, int y){
        this.fromPosition = x;
        this.toPosition = y;
    }

    public void setFromPosition(int x){
        this.fromPosition = x;
    }

    public void setToPosition(int y){
        this.toPosition = y;
    }

    public int getFromPosition(){
        return fromPosition;
    }

    public int getToPosition(){
        return toPosition;
    }

    public int getStart() {
        return 0;
    }

    public int getEnd() {
        return 0;
    }
}
