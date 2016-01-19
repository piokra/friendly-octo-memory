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
package whfv.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import org.jsfml.window.event.Event;
import whfv.Drawable;
import whfv.EventProcessor;
import whfv.Processable;
import whfv.collision.Collidable;
import whfv.collision.ConvexCollidingShape;
import whfv.collision.qt.BestFitQuadTree;
import whfv.holder.Holdable;
import whfv.holder.SpatialHoldable;
import whfv.hotkeys.Hotkeyable;
import whfv.physics.Physical;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix3x3d;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameWorld implements Drawable, Processable, EventProcessor {

    private final ConcurrentLinkedQueue<Hotkeyable> mHotkeyables = new ConcurrentLinkedQueue<>(); // this as well
    private final ConcurrentLinkedQueue<EventProcessor> mEventProcessors = new ConcurrentLinkedQueue<>(); // this doesnt really need to be concurrent maybe
    private final ConcurrentLinkedQueue<Drawable> mDrawables = new ConcurrentLinkedQueue<>(); // this too
    private final ArrayList<LinkedList<Processable>> mProcesables = new ArrayList<>();
    private final LinkedBlockingQueue<Processable> mNotAssigned = new LinkedBlockingQueue<>();
    private final ArrayList<LinkedBlockingQueue<Processable>> mToRemove = new ArrayList<>();
    private final LinkedBlockingQueue<GameObject> mQueuedGameObjectToRemove = new LinkedBlockingQueue<>();
    private final BestFitQuadTree mPhysicals;
    private final BestFitQuadTree mMouseEventProcessors;
    //private final BestFitQuadTree mDrawables;
    private boolean mStarted = false;
    private boolean mFinish = false;
    private Vector2d mScreenSize;
    private ConvexCollidingShape mScreenShape;
    private final Thread[] mThreads;
    private final CyclicBarrier mBarrier;
    private final double mTimeStep;
    private final long mTimePerStep;
    private long mTotalTimeInPreviousPFrame = 0;
    private double mAverageLastTime = 10;
    private int mThreadsNotLogged;
    private int mThreadsBelowAvg;
    private int mLastThreadsBelowAvg;
    private int mWorkloadToPickup = 0;
    private GameCamera mCamera = null;

    public GameWorld(Rect2D worldSize, Vector2d screenSize, double timeStep, long timeBetweenProcessingInMilliseconds) {
        mPhysicals = new BestFitQuadTree(5, worldSize);
        mMouseEventProcessors = new BestFitQuadTree(5, worldSize);
        mScreenSize = screenSize;
        mThreads = new Thread[Runtime.getRuntime().availableProcessors()];
        mBarrier = new CyclicBarrier(mThreads.length);
        mTimeStep = timeStep;
        for (int i = 0; i < mThreads.length; i++) {
            mThreads[i] = new Thread(new GameWorldRunnable(i));
            mProcesables.add(new LinkedList<>());
            mToRemove.add(new LinkedBlockingQueue<>());
        }
        mThreadsNotLogged = mThreads.length;

        mLastThreadsBelowAvg = mThreads.length;
        mThreadsBelowAvg = mThreads.length;
        mTimePerStep = timeBetweenProcessingInMilliseconds;
        System.out.println(mThreads.length);
    }

    private class GameWorldRunnable implements Runnable {

        private final int mThreadNumber;
        private final int mTotalThreads;
        private long mLastTime = 10;

        private GameWorldRunnable(int threadNumber) {
            mThreadNumber = threadNumber;
            mTotalThreads = mThreads.length;
        }

        @Override
        public void run() {
            do {

                removeWorkload();
                //check if you have more than avg workload
                if (mLastTime > mAverageLastTime) {
                    dropWorkload();
                }
                try {
                    mBarrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                }
                mWorkloadToPickup = mNotAssigned.size();
                try {
                    mBarrier.await();

                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                }
                //otherwise pickup workload
                if (mLastTime <= mAverageLastTime) {
                    pickupWorkload();
                }
                try {
                    mBarrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                }
                //process stuff here

                long start = System.currentTimeMillis();
                if (mThreadNumber == mTotalThreads - 1) { //pickup remaining workload this probably shouldnt happen
                    takeAllWorkload();
                }

                for (Processable processable : mProcesables.get(mThreadNumber)) {
                    processable.process(mTimeStep);
                }
                try {
                    mBarrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Processable processable : mProcesables.get(mThreadNumber)) {
                    processable.onFrameFinish();
                }
                long end = System.currentTimeMillis();
                long rem = mTimePerStep - (end - start);

                logTime(end - start);
                if (rem > 0) {
                    try {
                        Thread.sleep(rem);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    mBarrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!mFinish);
        }

        private void dropWorkload() {
            double percentile = 1.0 / ((double) mLastTime / (double) mAverageLastTime);
            int wantedSize = (int) (mProcesables.get(mThreadNumber).size() * percentile);
            while (mProcesables.get(mThreadNumber).size() != wantedSize) {
                addProcessable(mProcesables.get(mThreadNumber).removeFirst());
            }
        }

        private void pickupWorkload() {
            double percentile = 1.0 / (double) mLastThreadsBelowAvg;
            int wantedWorkload = (int) (percentile * mWorkloadToPickup) + 1;
            int takenWorkload = 0;
            while (takenWorkload != wantedWorkload) {
                if (!mNotAssigned.isEmpty()) {
                    Processable p = mNotAssigned.poll();
                    if (p != null) {
                        takenWorkload++;
                        mProcesables.get(mThreadNumber).add(p);
                    }
                } else {
                    return;
                }
            }
        }

        private void takeAllWorkload() { // this will only be executed on last thread
            while (!mNotAssigned.isEmpty()) {
                mProcesables.get(mThreadNumber).add(mNotAssigned.poll());
            }
        }

        private void removeWorkload() {
            while (!mToRemove.get(mThreadNumber).isEmpty()) {
                Processable p;
                try {
                    p = mToRemove.get(mThreadNumber).take();
                    mProcesables.get(mThreadNumber).remove(p);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }
    private final Semaphore mLogSemaphore = new Semaphore(1);

    protected void logTime(long time) {
        try {
            mLogSemaphore.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        mTotalTimeInPreviousPFrame += time;
        if (time <= mAverageLastTime) {
            mThreadsBelowAvg++;
        }
        mThreadsNotLogged--;
        if (mThreadsNotLogged == 0) {
            mAverageLastTime = (double) mTotalTimeInPreviousPFrame / (double) mThreads.length;
            mLastThreadsBelowAvg = mThreadsBelowAvg;
            mThreadsBelowAvg = 0;
            mThreadsNotLogged = mThreads.length;
            mWorkloadToPickup = mNotAssigned.size();

        }

        mLogSemaphore.release();
    }

    public void start() {
        if (!mStarted) {
            mStarted = true;
            int i = 0;
            for (Thread thread : mThreads) {
                thread.start();
                //System.out.println("Thread i:" + i + " started.");
                //i++;
            }
            //System.out.println("Barrier size: "+ mBarrier.getParties());

        }
    }

    public void stop() {
        if (mStarted) {
            mFinish = true;
            mStarted = false;
            for (Thread thread : mThreads) {
                try {
                    thread.join(10);
                } catch (InterruptedException ex) {
                    mBarrier.reset();
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, "Assuming stuff went wrong with barrier");
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            }
        }
        mFinish = false;

    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        
        RenderStates newStates = states;
        if(mCamera!=null) {
            Vector2d coord = mCamera.getCameraPosition();
            
            Matrix3x3d translationMatrix = 
                    Linear2DHTransformations.translationMatrix(-coord.x, -coord.y);
            Transform jtrans = Matrix3x3d.toSFMLTransform(translationMatrix);
            newStates = new RenderStates(states.blendMode, Transform.combine(jtrans, states.transform),
                    states.texture, states.shader);
        }
        for (Drawable mDrawable : mDrawables) {
            mDrawable.draw(target, newStates);
        }
    }

    @Override
    public void process(double timestep) {
        while (!mQueuedGameObjectToRemove.isEmpty()) {
            GameObject o;
            try {
                o = mQueuedGameObjectToRemove.take();
                o.removeMe(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public Event processEvent(Event e) {
        for (Hotkeyable mHotkeyable : mHotkeyables) {
            if (e == null) {
                return null;
            }
            e = mHotkeyable.asHotkeyProcessor().processEvent(e);
     
        }
        for (EventProcessor mEventProcessor : mEventProcessors) {
            if (e == null) {
                return null;
            }
            e = mEventProcessor.processEvent(e);
        }
        return e;
    }

    public void addGameObject(GameObject object) {
        object.addMe(this);
    }

    public void removeGameObject(GameObject object) {
        object.removeMe(this);
    }

    public void queueRemoveGameObject(GameObject g) {
        mQueuedGameObjectToRemove.add(g);
    }

    protected void addProcessable(Processable p) {
        mNotAssigned.add(p);
    }

    protected void removeProcessable(Processable p) {
        for (LinkedBlockingQueue<Processable> linkedBlockingQueue : mToRemove) {
            linkedBlockingQueue.add(p);
        }
    }

    protected void addDrawable(Drawable d) {
        mDrawables.add(d);
    }

    protected void removeDrawable(Drawable d) {
        mDrawables.remove(d);
    }

    protected SpatialHoldable<Collidable> addPhysical(Physical p) {
        return (SpatialHoldable<Collidable>)mPhysicals.add(p);
    }

    protected void removePhysical(SpatialHoldable<Collidable> p) { // this is not really needed I guess
        mPhysicals.remove(p);
    }

    protected void addEventProcessor(EventProcessor ep) {
        mEventProcessors.add(ep);
    }

    protected void removeEventProcessor(EventProcessor ep) {
        mEventProcessors.remove(ep);
    }

    protected void addHotkeyable(Hotkeyable h) {
        mHotkeyables.add(h);
    }

    protected void removeHotkeyable(Hotkeyable h) {
        mHotkeyables.remove(h);
    }
    
    public Collection<Collidable> getLikelyCollisions(Collidable c) {
        return mPhysicals.getLikelyCollisions(c);
    }

    public void setGameCamera(GameCamera camera) {
        mCamera = camera;
    }
    
}
