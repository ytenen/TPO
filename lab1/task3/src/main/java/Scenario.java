import interfaces.Command;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
}