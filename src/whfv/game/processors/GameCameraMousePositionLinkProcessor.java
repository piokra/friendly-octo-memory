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
package whfv.game.processors;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.Window;
import whfv.game.GameCamera;
import whfv.game.GameObject;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameCameraMousePositionLinkProcessor implements GameObjectProcessor {

    private final GameCamera mParent;
    private final Window mWindow;

    public GameCameraMousePositionLinkProcessor(GameCamera mParent, Window mWindow) {
        this.mParent = mParent;
        this.mWindow = mWindow;
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        Vector2i mouse = Mouse.getPosition(mWindow);
        Vector2i center = mWindow.getSize();
        Vector2f centerf = new Vector2f(mouse);
        centerf = Vector2f.mul(centerf, 0.5f);
        
        Vector2d reld = new Vector2d(mouse.x-centerf.x, mouse.y-centerf.y);
        if(Mouse.isButtonPressed(Mouse.Button.LEFT)) {
            System.out.println(mouse);
            System.out.println(reld);
        }
        reld = Vector2d.mul(reld, timestep * 0.1);
        mParent.getPosition().move(reld);
        Vector2d pos = mParent.getPosition().getPosition();
        double l = Vector2d.length(pos);
        l = l * l;
        Vector2d nnpos = Vector2d.neg(Vector2d.normalized(pos));
        mParent.getPosition().move(Vector2d.mul(nnpos, l * timestep * 0.01));

    }

}
