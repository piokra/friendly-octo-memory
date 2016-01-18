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
package whfv;

import whfv.console.Commands;
import whfv.console.Console;
import whfv.console.DefaultCommands;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;

import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class WHFV {

    public static void main(String[] args) {
        RenderWindow rw = new RenderWindow();
        Commands cm = new DefaultCommands(rw);

        rw.create(new VideoMode(500, 500), "yoloylo");
        Console c;
        try {
            c = new Console(cm, 10, Vector2i.ZERO, 500, 16);
        } catch (IOException ex) {
            Logger.getLogger(WHFV.class.getName()).log(Level.SEVERE, null, ex);
            c = null;
        }

        while (rw.isOpen()) {

            rw.clear(Color.BLUE);
            if (c != null) {
                rw.draw(c);
            }
            rw.display();

            for (Event e : rw.pollEvents()) {
                if (c != null) {
                    e = c.processEvent(e);
                }
                if (e == null) {
                    continue;
                }
                if (e.type == Type.CLOSED) {
                    rw.close();
                }
                if (e.type == Type.KEY_PRESSED) {
                    if (e.asKeyEvent().key == Keyboard.Key.ESCAPE) {
                        rw.close();
                    }
                }
            }
        }

    }
}
