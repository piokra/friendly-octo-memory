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
package whfv.position;

import whfv.utill.Matrix2x2d;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;
import whfv.utill.Vector3d;

public class AbsolutePosition implements Position {

    private Vector2d mPosition = null;
    private final Vector3d mStartingPosition;
    private Matrix3x3d mTransform = Matrix3x3d.IDENTITY;

    public AbsolutePosition(Vector2d position) {
        mPosition = position;
        mStartingPosition = new Vector3d(position.x, position.y, 1);
        
    }

    @Override
    public Vector2d getPosition() {
        return mPosition;
    }

    @Override
    public Vector2d getCoordinates() {
        return mPosition;
    }

    @Override
    public void move(Vector2d d) {
        mPosition = Vector2d.add(mPosition, d);
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        mPosition = Vector3d.fromHomogeneousVector(Matrix3x3d.
                matVecMul(homoTransformation, mStartingPosition));
    }

    @Override
    public Matrix3x3d getTransform() {
        return mTransform;
    }

}
