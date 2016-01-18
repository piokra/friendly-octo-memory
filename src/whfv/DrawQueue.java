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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 *
 * @author Uzytkownik
 */
public class DrawQueue implements Drawable {

    private final BlockingQueue<Drawable> mDrawables = new LinkedBlockingQueue<>();

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        if (isFinalized()) {
            while (!mDrawables.isEmpty()) {
                try {
                    Drawable d = mDrawables.take();
                    d.draw(target, states);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DrawQueue.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        resetQueue();
    }
    private boolean mFinalized = false;

    public void finalizeQueue() {
        mFinalized = true;
    }

    public boolean resetQueue() {
        if (mDrawables.isEmpty()) {
            mFinalized = false;
        }
        return !mFinalized;
    }

    public boolean isFinalized() {
        return mFinalized;
    }

    public boolean addDrawable(Drawable drawable) {
        if (!isFinalized()) {
            mDrawables.add(drawable);
            return true;
        }
        return false;
    }

    public Drawable[] toArray() {
        if (isFinalized()) {
            return (Drawable[]) mDrawables.toArray();
        }
        return null;
    }

    public Drawable[] toArray(Drawable[] d) {
        if (isFinalized()) {
            return mDrawables.toArray(d);
        }
        return d;
    }
}
