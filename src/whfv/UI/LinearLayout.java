/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.UI;

import java.util.ArrayList;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.window.event.Event;
import whfv.position.Position;
import whfv.position.RelativePosition;
import whfv.utill.Matrix2x2d;
import whfv.utill.Matrix3x3d;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

public class LinearLayout implements Layout {

    private final ArrayList<View> mViews = new ArrayList<>();
    private Position mPosition;
    private Vector2d mSize;
    private double mSpacing = 16;
    private final Vector2d mOrientation;
    private final Vector2d mOtherOrientation;
    private Rect2D mBoundingBox = Rect2D.ZERO;
    private Vector2d mMaxSize = Vector2d.VECTOR_ZERO;

    public LinearLayout(Vector2d mOrientation) {
        this.mOrientation = mOrientation;
        this.mOtherOrientation = new Vector2d(mOrientation.y, mOrientation.x);
    }
    
    
    
    @Override
    public void addView(View view) {
        Position p;
        if (mViews.isEmpty()) {
            p = new RelativePosition(new Vector2d(mSpacing, mSpacing), getPosition());
        } else {
            View previous = mViews.get(mViews.size() - 1);
            Vector2d lv = Matrix2x2d.matVecMul(previous.getBoundingRectangle().toMatrix(), mSize);
            double l = lv.y-lv.x;
            p = new RelativePosition(Vector2d.mul(mSpacing+l, mOrientation), previous.getPosition());
            mMaxSize = Vector2d.add(mMaxSize, Vector2d.componentwiseMul(previous.getPosition().getPosition(), mOrientation));
            mMaxSize = Vector2d.componentwiseMax(mMaxSize, previous.getBoundingRectangle().maxCorner);
            mBoundingBox = new Rect2D(Vector2d.VECTOR_ZERO, mMaxSize);
        }
        if (view != null) {
            mViews.add(view);
            view.setPosition(p);

        }
    }

    @Override
    public void removeView(View view) {
        throw new UnsupportedOperationException("Not working yet");
        //mViews.remove(view);
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        for (View view : mViews) {
            view.draw(target, states);
        }
    }

    @Override
    public void process(double timestep) {
        for (View view : mViews) {
            view.process(timestep);
        }
    }

    @Override
    public Event processEvent(Event e) {
        for (View view : mViews) {
            view.processEvent(e);
        }
        return e; 
    }

    @Override
    public Position getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(Position position) {
        mPosition = position;
    }

    @Override
    public Rect2D getBoundingRectangle() {
        return mBoundingBox;
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        mBoundingBox = mBoundingBox.transform(homoTransformation);
        for (View view : mViews) {
            view.transform(homoTransformation);
        }
    }

    @Override
    public void transform(Matrix2x2d transformation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
