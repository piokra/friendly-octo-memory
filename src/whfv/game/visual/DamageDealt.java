/*
 * Copyright (C) 2016 Pan Piotr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package whfv.game.visual;

import java.io.IOException;
import java.util.LinkedList;
import javax.annotation.Resource;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Transform;
import whfv.Timer;
import whfv.game.DefaultGameObject;
import whfv.game.GameObject;
import whfv.game.GameWorld;
import whfv.game.TimedGameObject;
import whfv.game.damage.Damage;
import whfv.game.damage.MagicalDamage;
import whfv.game.damage.PhysicalDamage;
import whfv.game.processors.GameObjectProcessor;
import whfv.game.processors.GameObjectRotator;
import whfv.game.processors.GameTimedObjectProcessor;
import whfv.position.Position;
import whfv.resources.FontType;
import whfv.resources.ResourceBank;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class DamageDealt extends DefaultGameObject implements TimedGameObject {

    private final Font mFont;
    private final Damage mDamage;
    private final Text mText;
    private final Timer mTimer = new Timer(100);
    public DamageDealt(Position pos, Damage damage, Font font) {
        super(pos);
        mFont = font;
        mDamage = damage;
        Integer dmg = (int) mDamage.damage;
        mText = new Text(dmg.toString(), mFont);
        mText.setCharacterSize(14);
        mText.setColor(damageToColor(damage));
        
        getProcessors().add(new GameTimedObjectProcessor(this));
        //getProcessors().add(new GameObjectRotator(this, 0.2));
    }

    public DamageDealt(Position pos, Damage damage) throws IOException {
        this(pos, damage, (Font) ResourceBank.getResource(FontType.TYPE, "SEGOEUI.TTF"));
    }

    private Color damageToColor(Damage damage) {
        if (damage instanceof MagicalDamage) {
            return Color.BLUE;
        }
        if (damage instanceof PhysicalDamage) {
            return new Color(122, 122, 122);
        }
        return Color.BLACK;
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        Vector2d pos = getPosition().getCoordinates();
        Matrix3x3d tra = Matrix3x3d.matMatMul(
                Linear2DHTransformations.translationMatrix(pos.x, pos.y), getTransform());
        Transform trans = Matrix3x3d.toSFMLTransform(tra);
        RenderStates newStates = new RenderStates(states.blendMode, 
                Transform.combine(trans, states.transform), states.texture, states.shader);
        mText.draw(target, newStates);
        
    }

    @Override
    public void expire() {
        getParent().queueRemoveGameObject(this);
    }

    @Override
    public Timer getTimer() {
        return mTimer;
        
    }

}
