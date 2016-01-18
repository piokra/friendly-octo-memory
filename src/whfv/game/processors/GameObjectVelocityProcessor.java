/*
 * The MIT License
 *
 * Copyright 2016 Pan Piotr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package whfv.game.processors;

import whfv.game.GameObject;
import whfv.game.PhysicalGameObject;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameObjectVelocityProcessor implements GameObjectProcessor {

    private final PhysicalGameObject mParent;

    public GameObjectVelocityProcessor(PhysicalGameObject mParent) {
        this.mParent = mParent;
    }
    
    
    
    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        mParent.getPosition().move(Vector2d.mul(mParent.getVelocity(), timestep));
        mParent.transform(mParent.getTransform());
    }
    
}
