/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
