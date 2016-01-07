/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 *
 * @author Uzytkownik
 */
public class Drawer implements Drawable {
    
    private final ConcurrentLinkedQueue<DrawQueue> mReadyFrames = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<DrawQueue> mJunkyard = new ConcurrentLinkedQueue<>();
    private Drawable[] mBackup = null;
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        updateBackup();
        if(mReadyFrames.isEmpty()) {
            while(mReadyFrames.size()>5)
                mReadyFrames.poll();
            DrawQueue dq = mReadyFrames.poll();
            dq.draw(target, states);
            mJunkyard.add(dq);
        } else {
            drawBackup(target, states);
        }
    }
    
    protected void drawBackup(RenderTarget target, RenderStates states) {
        if(mBackup!=null)
            for(Drawable d: mBackup)
                d.draw(target, states);
    }
    protected void updateBackup() {
        if(mReadyFrames.size()==1) {
            mBackup = mReadyFrames.peek().toArray(mBackup);
        }
    }
    protected DrawQueue getNextFrame() {
        if(mJunkyard.isEmpty())
            return new DrawQueue();
        else
            return mJunkyard.poll();
    }
    public void addFrame(DrawQueue queue) {
        mJunkyard.add(queue);
    }
}
