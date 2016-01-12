/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.utill;

/**
 *
 * @author Pan Piotr
 */
public interface Transformable {
    void transform(Matrix3x3d homoTransformation);
    void transform(Matrix2x2d transformation);
}
