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
public interface TimeMeasured {
    
    default long measureTime() {
        long start = System.currentTimeMillis();
        task();
        long end = System.currentTimeMillis();
        return end-start;
        
    }
    void task();
}
