/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

/**
 *
 * @author Uzytkownik
 */
public class HolderException extends Exception {

    /**
     * Creates a new instance of <code>HolderException</code> without detail
     * message.
     */
    public HolderException() {
    }

    /**
     * Constructs an instance of <code>HolderException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public HolderException(String msg) {
        super(msg);
    }
}
