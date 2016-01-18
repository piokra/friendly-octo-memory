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
