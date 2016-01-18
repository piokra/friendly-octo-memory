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
    private Vector2d mSize = Vector2d.VECTOR_ZERO;
    private double mSpacing = 16;
    private final Vector2d mOrientation;
    private final Vector2d mOtherOrientation;
    private Rect2D mBoundingBox = Rect2D.ZERO;
    private Vector2d mMaxSize = Vector2d.VECTOR_ZERO;
    private Matrix3x3d mTransformation = Matrix3x3d.IDENTITY;
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
            Vector2d lv = Matrix2x2d.matVecMul(previous.getBoundingRectangle().toMatrix(), mOrientation);
            double l = lv.y-lv.x;
            //System.out.println(l);
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
        mTransformation = homoTransformation;
        mBoundingBox = mBoundingBox.transform(homoTransformation);
        for (View view : mViews) {
            view.transform(homoTransformation);
        }
    }

    @Override
    public Matrix3x3d getTransform() {
        return mTransformation;
    }

}
