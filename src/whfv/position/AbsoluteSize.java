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
public class AbsoluteSize implements Size {
    private Vector2d mSize;

    public AbsoluteSize(Vector2d size) {

        if (size==null) {
            throw new NullPointerException("Cant init size with null");
        }
        this.mSize = size;
    }

    @Override
    public Vector2d getSize() {
        return mSize;
    }

    @Override
    public void setSize(Vector2d size) {
        mSize=size;
    }
    
    
    
}
