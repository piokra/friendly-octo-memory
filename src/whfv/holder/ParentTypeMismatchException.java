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
public class ParentTypeMismatchException extends HolderException {

    /**
     * Creates instance of <code>ParentTypeMismatchException</code>
     * 
     * @param got The type of holdable parent got
     * @param expected The type of holdable parent expected
     */
    public ParentTypeMismatchException(Class<?> got, Class<?> expected) {
        super("Got: "+got+" Expected: "+expected);
    }

    
}
