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
package whfv.resources;

/**
 *
 * @author Uzytkownik
 */
public class ResourceType {

    private final String mType;

    protected ResourceType(String type) {
        mType = type;
    }

    @Override
    public int hashCode() {
        return mType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ResourceType)) {
            return false;
        }

        return mType.equals(o);
    }

    @Override
    public String toString() {
        return "Resource type: " + mType + ".";
    }

}
