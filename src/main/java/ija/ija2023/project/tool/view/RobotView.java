package ija.ija2023.project.tool.view;

import ija.ija2023.project.tool.common.Observable;
import ija.ija2023.project.tool.common.Position;
import ija.ija2023.project.tool.common.ToolRobot;

public class RobotView implements ComponentView, Observable.Observer {

    public Position position;
    public int angle;

    public RobotView() {
        this.position = new Position(0, 0);
        this.angle = 0;
    }
    public void update(Observable o) {
        // Zpracovává notifikaci o změně v objektu Observable
        //Position oldPos = this.position;
        this.position = ((ToolRobot) o).getPosition();
        this.angle = ((ToolRobot) o).angle();
        //EnvPresenter.repaint();
    }
}