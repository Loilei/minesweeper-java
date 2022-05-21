package game.display;

import game.model.Board;
import game.model.Location;
import game.model.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BoardDisplayTest {
    private Board board;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void testsSetup() {
        int height = 4;
        int width = 4;
        this.board = new Board(height, width);
        setTilesInBoard();
        setBombsInFirstRow();
        revealFourthRow();
        flagThirdRow();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void reset() {
        System.setOut(standardOut);
    }

    @Test
    void whenPrintBoardIsCalled_correctBoardIsPrinted() {
        //GIVEN
        var boardDisplay = new BoardDisplay();
        var sb = new StringBuilder();
        sb.append("    A  B  C  D ").append(System.lineSeparator());
        sb.append("1  ").append("[X]".repeat(board.getWidth())).append(System.lineSeparator());
        sb.append("2  ").append("[_]".repeat(board.getWidth())).append(System.lineSeparator());
        sb.append("3  ").append("[F]".repeat(board.getWidth())).append(System.lineSeparator());
        sb.append("4  ").append("[0]".repeat(board.getWidth())).append(System.lineSeparator());
        var expectedString = sb.toString();
        //WHEN
        boardDisplay.printBoard(board);
        //THEN
        assertEquals(expectedString, outputStreamCaptor.toString());
    }

    private void setTilesInBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                final var location = new Location(i, j);
                final var tile = new Tile(location);
                board.getPlayArea()[i][j] = tile;
                board.getListOfTiles().add(tile);
            }
        }
    }

    private void setBombsInFirstRow() {
        Tile[] firstRowTiles = board.getPlayArea()[0];
        Stream.of(firstRowTiles)
                .forEach(tile -> {
                    tile.setHasBomb(true);
                    tile.setRevealed(true);
                });
    }

    private void flagThirdRow() {
        Tile[] thirdRowTiles = board.getPlayArea()[2];
        Stream.of(thirdRowTiles)
                .forEach(tile -> tile.setFlagged(true));
    }

    private void revealFourthRow() {
        Tile[] secondRowTiles = board.getPlayArea()[3];
        Stream.of(secondRowTiles)
                .forEach(tile -> tile.setRevealed(true));
    }
}