/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.game;

import whfv.Processable;

/**
 *
 * @author Pan Piotr
 */
public interface GameObjectProcessor extends Processable {
    GameObject getParent();
}
