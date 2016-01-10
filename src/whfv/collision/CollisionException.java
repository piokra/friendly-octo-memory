/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

/**
 *
 * @author Pan Piotr
 */
public class CollisionException extends RuntimeException { //todo replace with a normal exception?

    /**
     * Creates a new instance of <code>CollisionException</code> without detail
     * message.
     */
    public CollisionException() {
    }

    /**
     * Constructs an instance of <code>CollisionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CollisionException(String msg) {
        super(msg);
    }
}
