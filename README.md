# minesweeper-java
Minesweeper is a single player game. In this game we have a field (grid with squares) with hidden bombs and the goal is to open/clean all the squares without a bomb and if the player hits a bomb, then s/he loses the game. If the player can open all the squares without a bomb, then s/he wins the game!

# Installation

The game can be played both in IDE or console.
Please note that both Java and Maven must be using **java version 17** for the build to work.

**IDE**

Here is what you need to do to set up the project:
1. Clone or download .zip file from https://github.com/Loilei/minesweeper-java.git
2. Open the project using any Java-friendly IDE
3. Go to src/main/java/com.marcela/Main.java
4. Run main method in Main.java class

**CONSOLE**

1. Open command and navigate to the main folder /minesweeper-java
2. Type 'mvn install' to build the project
3. Go to 'target' folder (cd target)
4. Type java -jar minesweeper-java-1.0-SNAPSHOT-jar-with-dependencies.jar

# How to play
The win condition is to reveal all tiles that do not contain bomb.

The tile types are as follows:
- '[X]' - tile with exploded bomb
- '[_]' - unrevealed tile
- '[F]' - flagged tile (marked bomb)
- '[3]' - safe tile with the amount of neighbour bombs 

The board size can be chosen by the user from range 5 to 10.
Board bigger or smaller than the given range will not be generated.

To play the game simply follow the instructions displayed in terminal.
Until you hit the bomb or reveal the whole board, the game will keep asking for coordinates and action on chosen tile.

If you hit the bomb the game will restart using the same bomb placement.

If you win the game will ask you if you wish to play again, but the board will be generated randomly again.

At any time when inputting coordinates or action you can type '**quit**' and the game will be terminated.