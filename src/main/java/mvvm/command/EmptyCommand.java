package mvvm.command;

public class EmptyCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    public String toString() {
        return "";
    }
}
