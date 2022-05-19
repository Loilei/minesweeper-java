package display;

import game.Board;

import java.util.Arrays;
import java.util.List;

public class BoardDisplay {
    private final List<Character> alphabet = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    public void printBoard(Board board) {
        final var sb = new StringBuilder();
        printFirstRow(board, sb);
        printTiles(board, sb);
        System.out.println(sb);
    }

    private void printTiles(Board board, StringBuilder sb) {
        for (int i = 0; i < board.getHeight(); i++) {
            sb.append(System.lineSeparator());
            sb.append(Integer.valueOf(i+1)).append(" ");
            sb.append("[_]".repeat(board.getWidth()));
        }
    }

    private void printFirstRow(Board board, StringBuilder sb) {
        sb.append("  ");
        alphabet.stream()
                .limit(board.getWidth())
                .forEach(letter -> sb.append(" ").append(letter).append(" "));
    }
}
