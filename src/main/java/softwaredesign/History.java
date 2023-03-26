package softwaredesign;

import java.util.Stack;

public class History {
    private Stack<Command> history = new Stack<>();

    public void push(Command c){
        this.history.push(c);
    }

    public Command pop(){
        return this.history.pop();
    }

    public Boolean isEmpty() {return this.history.isEmpty();}
}
