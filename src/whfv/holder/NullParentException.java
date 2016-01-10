/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.holder;

/**
 *
 * @author Pan Piotr
 */
public class NullParentException extends HolderException {

    /**
     * Creates a new instance of <code>NullParentException</code> without detail
     * message.
     */
    public NullParentException() {
        super("Parent is null");
    }

}
