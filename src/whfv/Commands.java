/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import java.util.ArrayList;
import whfv.utill.Pair;

/**
 *
 * @author Uzytkownik
 */
public class Commands {
    private final ArrayList<Pair<CommandArgs,CommandTask>> mCommands = new ArrayList<>();
    
    public void addCommand(CommandArgs args, CommandTask task) {
        mCommands.add(new Pair<>(args,task));
    }
    public boolean processCommand(String cmd) {
        boolean processed = false;
        String[] splited = CommandArgs.separate(cmd);
        for(String s : splited) {
            System.out.println(s);
        }
        for(Pair<CommandArgs,CommandTask> p : mCommands) {
            if(p.first.matches(splited)) {
                p.second.doTask(splited);
                processed = true;
            }
        }
        return processed;
    }
}
