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
        Drawer drawer = new Drawer();

        ParalellProcessor pp = new ParalellProcessor();
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
