package ija.ija2023.project.env;

import ija.ija2023.project.common.Position;
import ija.ija2023.project.common.robot.Robot;
import ija.ija2023.project.common.robot.RobotFactory;
import ija.ija2023.project.room.Room;


/**
 * Class for parsing the input file and creating the room with its objects (obstacles and robots)
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see Room
 * @see Position
 * @see Robot
 * @see RobotFactory
 */
public class InputParser {
    static int numberOfLineParams = 3;
    static boolean controlledRobotAdded = false;
    static Room room = null;

    /**
     * Parse the input file and create the room with its objects
     *
     * @param content Content of the input file
     * @return Room created from the input file
     * @throws Exception If the input file is not valid
     */
    public static Room parseInput(String content) throws Exception{
        // Split the content of the file into lines
        String[] lines = content.split("\n");

        // Parse room dimensions
        String[] dimensions = lines[0].trim().split(";");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        // Create the room
        room = Room.create(height, width);

        // Parse the rest of the file
        for (int i = 1; i < lines.length; i++) {
            parseLine(lines[i]);
        }

        // System log
        System.out.println("INFO Room loaded successfully.");

        // Return the room
        return room;
    }

    /**
     * Parse a single line of the input file
     *
     * @param line Line to parse
     * @throws Exception If the line is not valid
     */
    private static void parseLine(String line) throws Exception{
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
            if (!room.createObstacleAt(y, x)) {
                throw new Exception("Failed to create obstacle at " + x + " " + y);
            }
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
