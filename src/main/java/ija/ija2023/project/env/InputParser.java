package ija.ija2023.project.env;

import ija.ija2023.project.common.Position;
import ija.ija2023.project.common.robot.Robot;
import ija.ija2023.project.common.robot.RobotFactory;
import ija.ija2023.project.room.Room;

public class InputParser {
    
    static int numberOfLineParams = 3;
    static boolean controlledRobotAdded = false;
    static Room room = null;

    public static Room parseInput(String content) throws Exception{
        // Split the content of the file into lines
        String[] lines = content.split("\n");

        // Parse room dimensions
        String[] dimensions = lines[0].trim().split(";");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        // Create the room
        room = Room.create(height, width);
        if (room == null) {
            return null;
        }

        // Parse the rest of the file
        for (int i = 1; i < lines.length; i++) {
            parceLine(lines[i]);
        }

        // System log
        System.out.println("INFO Room loaded successfully.");

        // Return the room
        return room;
    }

    private static void parceLine(String line) throws Exception{
        // Split the line into parts
        String[] parts = line.trim().split(";");
        if (parts.length != numberOfLineParams) {
            return;
        }

        // Parse position
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        if (x < 0 || x >= room.cols() || y < 0 || y >= room.rows()) {
            throw new Exception("Position out of range.");
        }
        Position pos = new Position(y, x);
        if (room.obstacleAt(pos) || room.robotAt(pos)) {
            throw new Exception("Position is already occupied.");
        }

        // Object type
        String objectType = "autonomous robot";

        // Create obstacle
        if (parts[0].equals("O")) {
            // Create obstacle
            room.createObstacleAt(y, x);
            objectType = "obstacle";
        }

        // Handle controlled robot creation
        boolean isControlled = false;
        if (parts[0].equals("CR")) {
            // Check if controlled robot already exists
            if (controlledRobotAdded) {
                throw new Exception("Controlled robot already exists.");
            }
            controlledRobotAdded = true;
            isControlled = true;
            objectType = "controlled robot";
        }

        // Create robot
        if (parts[0].equals("AR") || parts[0].equals("CR")) {
            // Create robot
            Robot robot = RobotFactory.create(room, pos, isControlled);
            if (robot == null) {
                throw new Exception("Robot creation failed.");
            }
        }

        // System log
        System.out.println("INFO Creating " + objectType + " at " + x + " " + y);
    }
}
