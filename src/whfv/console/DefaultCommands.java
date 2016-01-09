/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.console;

import org.jsfml.graphics.RenderWindow;

/**
 *
 * @author Uzytkownik
 */
public class DefaultCommands extends Commands {

    public DefaultCommands(final RenderWindow rw) {
        super();

        //QUIT COMMAND
        CommandArgs cmdArgs = new CommandArgs("quit", new ArgumentType[]{ArgumentType.Number});
        CommandTask ct = new CommandTask() {
            @Override
            public void doTask(String[] args) {
                rw.close();
            }

        };
        
        addCommand(cmdArgs, ct);
    }
}
