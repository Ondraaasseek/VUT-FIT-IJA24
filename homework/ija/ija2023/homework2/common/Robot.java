package ija.ija2023.homework2.common;

import ija.ija2023.homework2.tool.common.Position;
import ija.ija2023.homework2.tool.common.ToolRobot;

public interface Robot extends ToolRobot {
    void turn();
    int angle();
    boolean canMove();
    boolean move();
    Position getPosition();
}
