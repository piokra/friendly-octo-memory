/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
