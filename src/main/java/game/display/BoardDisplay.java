package game.display;

import game.model.Board;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class BoardDisplay {
    private final char leftBorder = '[';
    private final char rightBorder = ']';
    private final char emptySymbol = '_';
    private final char bombSymbol = 'X';
    private final char flagSymbol = 'F';

    private final List<Character> alphabet = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    public void printBoard(Board board) {
        final var sb = new StringBuilder();
        printFirstRow(board, sb);
        printTiles(board, sb);
        System.out.println(sb);
    }

    private void printTiles(Board board, StringBuilder sb) {
        for (int i = 0; i < board.getHeight(); i++) {
            sb.append(System.lineSeparator());
            if (i < 9) {
                sb.append(Integer.valueOf(i + 1)).append("  ");
            } else {
                sb.append(Integer.valueOf(i + 1)).append(" ");
            }
            for (int j = 0; j < board.getWidth(); j++) {
                final var currentTile = board.getPlayArea()[i][j];
                if (currentTile.isRevealed() && currentTile.hasBomb()) {
                    sb.append(leftBorder).append(bombSymbol).append(rightBorder);
                } else if (currentTile.isRevealed() && !currentTile.hasBomb()) {
                    sb.append(leftBorder).append(currentTile.getNumberOfNeighbourBombs()).append(rightBorder);
                } else if (!currentTile.isRevealed() && currentTile.isFlagged()) {
                    sb.append(leftBorder).append(flagSymbol).append(rightBorder);
                } else {
                    sb.append(leftBorder).append(emptySymbol).append(rightBorder);
                }
            }
        }
    }

    private void printFirstRow(Board board, StringBuilder sb) {
        sb.append("   ");
        alphabet.stream()
                .limit(board.getWidth())
                .forEach(letter -> sb.append(" ").append(letter).append(" "));
    }
}
