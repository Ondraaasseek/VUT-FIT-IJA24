package ija.ija2023.homework2.tool.view;

import ija.ija2023.homework2.tool.common.Observable;
import ija.ija2023.homework2.tool.common.Position;
import ija.ija2023.homework2.tool.common.ToolRobot;
import ija.ija2023.homework2.tool.EnvPresenter;

public class RobotView extends Object implements ComponentView, Observable.Observer {

    public Position position;
    public int angle;

    public RobotView() {
        this.position = new Position(0, 0);
        this.angle = 0;
    }
    public void update(Observable o) {
        // Zpracovává notifikaci o změně v objektu Observable
        Position oldPos = this.position;
        this.position = ((ToolRobot) o).getPosition();
        this.angle = ((ToolRobot) o).angle();
        EnvPresenter.repaint();
    }
}