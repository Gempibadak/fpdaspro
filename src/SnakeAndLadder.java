/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : C
 * Group    : XX
 * Members  :
 * 1. Student ID - Full Name
 * 2. Student ID - Full Name
 * 3. Student ID - Full Name
 * ------------------------------------------------------
 */

import java.util.*;

public class SnakeAndLadder {
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private ArrayList<Integer> bombs;
    private int boardSize;
    private int status;
    private int playerInTurn;
    private int numRounds;
    private int numDice;
    private int sizeOfBoard;
    private int[] consecutiveRolls;
    private int[] scores;
    private Sound backgroundMusic;


    public SnakeAndLadder(int boardSize) {
        this.boardSize = boardSize;
        this.players = new ArrayList<Player>();
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();
        this.bombs = new ArrayList<Integer>();
        this.status = 0;
        this.numRounds = 0;
        this.numDice = 1;
        this.consecutiveRolls = new int[4];
        this.scores = new int[4];
        this.backgroundMusic = new Sound();
    }

    // Initializes the game by setting up default snakes and ladders.
    public void initiateGame() {
        int[][] ladders = {
                {2, 23},
                {8, 34},
                {20, 77},
                {32, 68},
                {41, 79},
                {74, 88},
                {82, 100},
                {85, 95}
        };
        addLadders(ladders);

        int[][] snakes = {
                {29, 9},
                {38, 15},
                {47, 5},
                {53, 33},
                {62, 37},
                {86, 54},
                {92, 70},
                {97, 25}
        };
        addSnakes(snakes);

        generateBombs();
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPlayerInTurn(int p) {
        this.playerInTurn = p;
    }
    public void addPlayer(Player p) {
        this.players.add(p);
    }
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }
    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }
    public void setSizeOfBoard () {
        this.sizeOfBoard = sizeOfBoard;
    }
    private void playBackgroundMusic() {
        backgroundMusic.playSoundLoop("backgroundMusic.wav");
    }
    private void stopBackgroundMusic() {
        backgroundMusic.stopSound();
    }
    public void addSnake(Snake s) {
        this.snakes.add(s);
    }
    public void addSnakes(int[][] s) {
        for (int i = 0; i < s.length; i++) {
            Snake snake = new Snake(s[i][0], s[i][1]);
            this.snakes.add(snake);
        }
    }
    public void addLadder(Ladder l) {
        this.ladders.add(l);
    }
    public void addLadders(int[][] l) {
        for (int m = 0; m < l.length; m++) {
            Ladder ladder = new Ladder(l[m][0], l[m][1]);
            this.ladders.add(ladder);
        }
    }
    private void generateBombs() {
        Random rand = new Random();
        int numBombs = rand.nextInt(5) + 3;  // Generate between 3 and 7 bombs
        while (bombs.size() < numBombs) {
            int bombPosition = rand.nextInt(boardSize - 1) + 1;  // Bomb positions between 1 and boardSize-1
            if (!bombs.contains(bombPosition)) {
                bombs.add(bombPosition);
            }
        }
        System.out.println("Bomb positions: " + bombs);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getStatus() {
        return status;
    }

    public int getPlayerInTurn() {
        return playerInTurn;
    }
    public int getNumRounds() {
        return numRounds;
    }
    public int getNumDice() {
        return numDice;
    }
    public int getsizeOfBoard() {
        return sizeOfBoard;
    }
    public int[] getScores() {
        return scores;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public ArrayList<Snake> getSnakes() {
        return snakes;
    }
    public ArrayList<Ladder> getLadders() {
        return ladders;
    }
    public int getTurn() {
        if (this.status == 0) {
            return (int) (Math.random() * players.size());
        } else {
            return (this.playerInTurn + 1) % this.players.size();
        }
    }

    // Moves a player by a specified number of positions and handles the logic for snakes, ladders, and bumping into other players.
    public void movePlayer(Player p, int x) {
        int initialPosition = p.getPosition();
        p.moveAround(x, this.boardSize);
        for (int i = 0; i < this.ladders.size(); i++) {
            Ladder l = this.ladders.get(i);
            if (p.getPosition() == l.getFromPosition()) {
                p.setPosition(l.getToPosition());
                System.out.println(p.getUserName() + " got ladder from " + l.getFromPosition() + " climb to " + l.getToPosition());
                Sound.playSoundWithDelay("Ladder.wav", 500);
            }
        }

        for (int i = 0; i < this.snakes.size(); i++) {
            Snake s = this.snakes.get(i);
            if (p.getPosition() == s.getFromPosition()) {
                p.setPosition(s.getToPosition());
                System.out.println(p.getUserName() + " got snake from " + s.getFromPosition() + " slide down to " + s.getToPosition());
                Sound.playSoundWithDelay("Snake.wav", 500);
            }
        }

        for (Player player : players) {
            if (player != p && player.getPosition() == p.getPosition()) {
                System.out.println(p.getUserName() + " bumped into " + player.getUserName() + "! " + player.getUserName() + " is sent back to " + initialPosition);
                playBumpSound();
                player.setPosition(initialPosition);
            }
        }

        if (bombs.contains(p.getPosition())) {
            System.out.println(p.getUserName() + " hit a bomb at position " + p.getPosition() + " and is sent back to start!");
            playBombSound(p);
        }

        System.out.println(p.getUserName() + " new position is " + p.getPosition());

        if (p.getPosition() == this.boardSize) {
            this.status = 2;
            scores[players.indexOf(p)]++;
            System.out.println();
            System.out.println("=================================");
            System.out.println("The winner is: " + p.getUserName());
            System.out.println("=================================");
            System.out.println();
            playWinnerSound();
        }
    }

    private void playDiceRollSound() {
        Sound.playSound("dice_roll.wav");
    }
    private void playBombSound(Player p) {
        new Thread(() -> {
            Sound.playSound("Bomb.wav");
            try {
                Thread.sleep(1000); // Adjust to match the length of the bomb sound
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Sound.playSound("Backstart.wav");
            p.setPosition(0);
            System.out.println("Player has returned to position 0.");
            System.out.println(p.getUserName() + " new position is 0");
        }).start();
    }

    private void playLadderSound() {
        Sound.playSound("Ladder.wav");
    }

    private void playSnakeSound() {
        Sound.playSound("Snake.wav");
    }

    private void playWinnerSound() {
        Sound.playSound("winner_sound.wav");
    }
    private void playDiceResultSound() {
        Sound.playSoundWithDelay("DiceResult.wav", 500);
    }
    private void playBumpSound() {
        Sound.playSound("Bump.wav");
    }
    private void playFinalScoreSound() {
        Sound.playSound("FinalScore.wav");
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        int numPlayers = 0;
        int totalRounds = 0;
        int boardSize = 0;

        playBackgroundMusic();
        while (true) {
            try {
                System.out.println("Enter the size of the board (25, 36, 49, 64, 81, 100): ");
                boardSize = sc.nextInt();
                if (boardSize == 25 || boardSize == 36 || boardSize == 49 || boardSize == 64 || boardSize == 81 || boardSize == 100) {
                    break;
                } else {
                    System.out.println("Invalid size. Please enter one of the following sizes: 25, 36, 49, 64, 81, 100.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number: 25, 36, 49, 64, 81, or 100.");
                sc.nextLine(); // clear the invalid input
            }
        }

        setBoardSize(boardSize); // Set the board size

        while (true) {
            try {
                System.out.println("Enter the number of players (1, 2, 3, or 4):");
                numPlayers = sc.nextInt();
                sc.nextLine();
                if (numPlayers >= 1 && numPlayers <= 4) {
                    break;
                } else {
                    System.out.println("Invalid number of players. Please enter 1, 2, 3, or 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        while (true) {
            try {
                System.out.println("Enter the number of rounds:");
                totalRounds = sc.nextInt();
                sc.nextLine();
                if (totalRounds > 0) {
                    break;
                } else {
                    System.out.println("Invalid number of rounds. Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        int numDice = 0;
        while (true) {
            try {
                System.out.println("Enter the number of dice (1 or 2):");
                numDice = sc.nextInt();
                sc.nextLine();
                if (numDice == 1 || numDice == 2) {
                    break;
                } else {
                    System.out.println("Invalid number of dice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        setNumRounds(totalRounds);
        setNumDice(numDice);

        if (numPlayers == 1) {
            System.out.println("Enter player name:");
            String playerName = sc.nextLine();
            Player player = new Player(playerName);
            Player computer = new Computer("Computer");
            addPlayer(player);
            addPlayer(computer);
        } else {
            for (int i = 1; i <= numPlayers; i++) {
                System.out.println("Enter player " + i + " name:");
                String playerName = sc.nextLine();
                Player player = new Player(playerName);
                addPlayer(player);
            }
        }

        initiateGame();

        for (int currentRound = 1; currentRound <= totalRounds; currentRound++) {
            System.out.println("Round " + currentRound + " starts!");

            for (Player player : players) {
                player.setPosition(0);
                consecutiveRolls[players.indexOf(player)] = 0;
            }

            do {
                int t = getTurn();
                setStatus(1);
                setPlayerInTurn(t);
                Player playerInTurn = getPlayers().get(t);
                System.out.println("---------------------------------");
                System.out.println("Player in turn is " + playerInTurn.getUserName());

                boolean extraTurn;
                do {
                    extraTurn = false;
                    if (playerInTurn instanceof Computer) {
                        int x = playerInTurn.rollDice(numDice);
                        System.out.println("Computer rolled the dice: " + x);
                        if (x == 6) {
                            consecutiveRolls[t]++;
                            if (consecutiveRolls[t] < 3) {
                                extraTurn = true;
                                System.out.println("Computer gets another turn!");
                            } else {
                                System.out.println("Computer rolled three consecutive 6s and loses its turn.");
                                consecutiveRolls[t] = 0;
                                extraTurn = false;
                            }
                        } else {
                            consecutiveRolls[t] = 0;
                        }
                        movePlayer(playerInTurn, x);
                    } else {
                        System.out.println(playerInTurn.getUserName() + ", press Enter to roll the dice.");
                        sc.nextLine();
                        playDiceRollSound();  // Play dice roll sound
                        int x = playerInTurn.rollDice(numDice);
                        System.out.println(playerInTurn.getUserName() + " rolled the dice: " + x);
                        playDiceResultSound();
                        if (x == 6) {
                            consecutiveRolls[t]++;
                            if (consecutiveRolls[t] < 3) {
                                extraTurn = true;
                                System.out.println(playerInTurn.getUserName() + " gets another turn!");
                            } else {
                                System.out.println(playerInTurn.getUserName() + " rolled three consecutive 6s and loses its turn.");
                                consecutiveRolls[t] = 0;
                                extraTurn = false;
                            }
                        } else {
                            consecutiveRolls[t] = 0;
                        }
                        movePlayer(playerInTurn, x);
                    }

                    if (getStatus() == 2) {
                        break;
                    }
                } while (extraTurn);

                if (getStatus() == 2) {
                    break;
                }
            } while (true);

            if (getStatus() == 2) {
                System.out.println("Round " + currentRound + " ends!");
                System.out.println("=================================");
                setStatus(0);
                for (Player player : players) {
                    player.setPosition(0);
                }

                boolean validInput = false;
                while (!validInput) {
                    System.out.println("Choose an option:");
                    System.out.println("1. Continue to the next round");
                    System.out.println("2. View current scores");
                    System.out.println("3. Exit the game");
                    try {
                        int option = sc.nextInt();
                        switch (option) {
                            case 1:
                                validInput = true; // lanjut ke putaran berikutnya
                                break;
                            case 2:
                                // Tampilkan skor saat ini
                                System.out.println("Current Scores:");
                                for (int i = 0; i < players.size(); i++) {
                                    System.out.println(players.get(i).getUserName() + ": " + scores[i]);
                                }
                                break;
                            case 3:
                                // Keluar dari permainan
                                System.out.println("Exiting the game...");
                                return;
                            default:
                                System.out.println("Invalid option. Please choose 1, 2, or 3.");
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
                        sc.nextLine(); // clear buffer
                    }
                }
            }
        }

        // Display final scores
        System.out.println("Final Scores:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getUserName() + ": " + scores[i]);
        }

        // Determine the final winner
        int maxScore = -1;
        ArrayList<Player> winners = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                winners.clear();
                winners.add(players.get(i));
            } else if (scores[i] == maxScore) {
                winners.add(players.get(i));
            }
        }

        if (winners.size() == 1) {
            System.out.println("The final winner is " + winners.get(0).getUserName() + "!");
        } else {
            System.out.println("It's a tie between:");
            for (Player winner : winners) {
                System.out.println(winner.getUserName());
            }
        }
        playWinnerSound(); // Play winner sound after announcing the winner
        stopBackgroundMusic();
    }
}
