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
public interface DrawClonable {

    /**
     * This method is supposed to return a drawable that upon drawing would generate the same graphics as if drawing the original object
     * no need to clone the whole object
     * @return cloned drawable
     */
    Drawable cloneDrawable();
}
