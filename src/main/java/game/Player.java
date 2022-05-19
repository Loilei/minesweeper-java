package game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private String name;
    private boolean isAlive;

    public Player(String name) {
        this.name = name;
        this.isAlive = true;
    }
}
