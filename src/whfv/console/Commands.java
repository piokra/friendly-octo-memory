/* 
 * Copyright (C) 2016 Pan Piotr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package whfv.console;

import java.util.ArrayList;
import whfv.utill.Pair;

/**
 *
 * @author Uzytkownik
 */
public class Commands {

    private final ArrayList<Pair<CommandArgs, CommandTask>> mCommands = new ArrayList<>();

    public void addCommand(CommandArgs args, CommandTask task) {
        mCommands.add(new Pair<>(args, task));
    }

    public boolean processCommand(String cmd) {
        boolean processed = false;
        String[] splited = CommandArgs.separate(cmd);
        System.out.println("splitted");
        for (String s : splited) {
            System.out.println(s);
        }
        
        for (Pair<CommandArgs, CommandTask> p : mCommands) {
            if (p.first.matches(splited)) {
                p.second.doTask(splited);
                processed = true;
            }
        }
        return processed;
    }
}
