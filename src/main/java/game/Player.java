package game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private String name;
    private boolean isAlive;

    public Player() {
        this.isAlive = true;
    }
}
