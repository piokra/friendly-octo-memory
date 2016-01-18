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

import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class RelativeSize implements Size {

    private Vector2d mRelativeSize;
    private Size mParent;

    public RelativeSize(Vector2d mRelativeSize, Size mParent) {
        this.mRelativeSize = mRelativeSize;
        this.mParent = mParent;
    }
    @Override
    public Vector2d getSize() {
        return Vector2d.add(mRelativeSize, mParent.getSize());
    }

    @Override
    public void setSize(Vector2d size) {
        mRelativeSize=size;
    }
    
}
