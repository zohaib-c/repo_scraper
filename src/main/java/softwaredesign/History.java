package softwaredesign;

import java.util.Stack;

/* We decided to make this class a Singleton class, because of the following two reasons:
 *   There is going to be only one command history at a time. Passing that object to the respective methods leads
 *   to an unnecessarily big amount of arguments in chained calls, when only the last method actually needs the
 *   History. To illustrate, pushing a command to the History stack, needs to be done at the most specific
 *   moment. For example. for 'ranking contributor commit' the history would need to be passed from the main loop
 *   in Application, to the executeCommand() in Application, to the execute() in Ranking, then RankingContributor
 *   and then finally the full command could be pushed. The Singleton design pattern, solves this issue since the
 *   Singleton is a global object that can be accessed in all classes.
 */

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
