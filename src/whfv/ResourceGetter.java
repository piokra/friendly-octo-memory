/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @author Uzytkownik
 */
public interface ResourceGetter {

    /**
     * Implementations of this interfaces are supposed to attempt to load a resource (type specific for a given interface) return the resource or throw IOException on failure
     * @param p path to the file
     * @return loaded resource
     * @throws IOException if unable to load the resource
     */
    Object loadResource(Path p) throws IOException;
}
