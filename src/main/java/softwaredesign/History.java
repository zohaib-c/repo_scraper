package softwaredesign;

import java.util.Stack;

public class History {
    public static History instance;
    private final Stack<String> s;

    private History() {
        this.s = new Stack<>();
    }

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    public void push(String cmd){
        this.s.push(cmd);
    }

    public String pop(){
        return this.s.pop();
    }

    public Boolean isEmpty() {return this.s.isEmpty();}
}
