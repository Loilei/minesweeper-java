package com.marcela.game.model;

import com.marcela.game.model.enums.RevealStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RevealResult {
    private RevealStatus status;
    private int surroundingBombs;
}
