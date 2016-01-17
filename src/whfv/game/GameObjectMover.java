/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.game;

import whfv.utill.Vector2d;

public class GameObjectMover implements GameObjectProcessor {

    private final GameObject mParent;
    protected final boolean mNormalized;
    protected final double mStrength;
    private double mNorth = 0;
    private double mSouth = 0;
    private double mWest = 0;
    private double mEast = 0;

    public GameObjectMover(GameObject parent, boolean normalized, double strength) {
        mParent = parent;
        mNormalized = normalized;
        mStrength = strength;
    }

    @Override
    public void process(double timestep) {
        Vector2d move = new Vector2d((mEast - mWest), (mSouth - mNorth));
        if (mNormalized) {
            move = Vector2d.normalized(move);
        }
        move = Vector2d.mul(move, mStrength);
        mParent.getPosition().move(move);
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    public void walk(Direction dir) {
        switch (dir) {
            case NORTH:
                walkNorth();
                return;
            case SOUTH:
                walkSouth();
                return;
            case WEST:
                walkWest();
                return;
            case EAST:
                walkEast();
                return;
        }
    }

    public void stop(Direction dir) {
        switch (dir) {
            case NORTH:
                stopNorth();
                return;
            case SOUTH:
                stopSouth();
                return;
            case WEST:
                stopWest();
                return;
            case EAST:
                stopEast();
                return;
        }
    }

    protected void walkNorth() {
        mNorth = 1;
        System.out.println("NORTH");
    }

    protected void stopNorth() {
        mNorth = 0;
        System.out.println("_NORTH");
    }

    protected void walkWest() {
        mWest = 1;
        System.out.println("WEST");
    }

    protected void stopWest() {
        mWest = 0;
        System.out.println("_WEST");
    }

    protected void walkSouth() {
        mSouth = 1;
        System.out.println("SOUTH");
        
    }

    protected void stopSouth() {
        mSouth = 0;
        System.out.println("_SOUTH");
    }

    protected void walkEast() {
        mEast = 1;
        System.out.println("EAST");
    }

    protected void stopEast() {
        mEast = 0;
        System.out.println("_EAST");
    }
}
