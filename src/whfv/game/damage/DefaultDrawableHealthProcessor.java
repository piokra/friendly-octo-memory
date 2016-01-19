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
package whfv.game.damage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import whfv.Drawable;
import whfv.game.GameObject;
import whfv.game.PhysicalGameObject;
import whfv.game.processors.GameObjectProcessor;
import whfv.game.visual.DamageDealt;
import whfv.position.RelativePosition;
import whfv.position.Size;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class DefaultDrawableHealthProcessor implements Health, Drawable,
        GameObjectProcessor {

    private final PhysicalGameObject mParent;
    private final Size mSize;
    private final RectangleShape mBackgroundRectangle;
    private final RectangleShape mRectangleShape;
    private double mMaxHealth;
    private double mHealth;

    public DefaultDrawableHealthProcessor(PhysicalGameObject mParent, Size mSize, double mMaxHealth, double mHealth) {
        this.mParent = mParent;
        this.mSize = mSize;
        this.mMaxHealth = mMaxHealth;
        this.mHealth = mHealth;
        if (mSize != null) {

            mRectangleShape = new RectangleShape(new Vector2f((float) mSize.getSize().x, (float) mSize.getSize().y));
            mBackgroundRectangle = new RectangleShape(new Vector2f((float) mSize.getSize().x * 1.1f, (float) mSize.getSize().y * 1.1f));
            mBackgroundRectangle.setFillColor(new Color(122, 122, 122));
            mRectangleShape.setFillColor(Color.RED);

        } else {
            mRectangleShape = null;
            mBackgroundRectangle = null;
        }
    }

    @Override
    public void dealSuperDamage(Damage d) {
        mHealth -= d.damage;
        try {
            mParent.getParent().addGameObject(new DamageDealt(new RelativePosition(new Vector2d(0, -16), mParent.getPosition()), d));
        } catch (IOException ex) {
            Logger.getLogger(DefaultDrawableHealthProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void dealPhysicalDamage(PhysicalDamage d) {
        mHealth -= d.damage * 0.7;
        try {
            mParent.getParent().addGameObject(new DamageDealt(new RelativePosition(new Vector2d(0, -16), mParent.getPosition()), d));
        } catch (IOException ex) {
            Logger.getLogger(DefaultDrawableHealthProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void dealMagicalDamage(MagicalDamage d) {
        mHealth -= d.damage * 1.3;
        try {
            mParent.getParent().addGameObject(new DamageDealt(new RelativePosition(new Vector2d(0, -16), mParent.getPosition()), d));
        } catch (IOException ex) {
            Logger.getLogger(DefaultDrawableHealthProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double getHealthPercentile() {
        return mHealth / mMaxHealth;
    }

    @Override
    public double getHealthPoints() {
        return mHealth;
    }

    @Override
    public double getMaxHealthPoints() {
        return mMaxHealth;
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        Vector2d parentCoord = mParent.getPosition().getCoordinates();
        mBackgroundRectangle.setPosition(new Vector2f((float) (parentCoord.x - mSize.getSize().x / 2 * 1.1),
                (float) (parentCoord.y - mSize.getSize().y / 2 * 1.1 - 18)));
        mRectangleShape.setPosition(new Vector2f((float) (parentCoord.x - mSize.getSize().x / 2),
                (float) (parentCoord.y - mSize.getSize().y / 2 - 18)));
        mRectangleShape.setSize(new Vector2f((float) (mSize.getSize().x * getHealthPercentile()), (float) (mSize.getSize().y)));
        mBackgroundRectangle.draw(target, states);
        mRectangleShape.draw(target, states);
    }

    @Override
    public void setHealthPercentile(double p) {
        mHealth *= p / getHealthPercentile();
    }

    @Override
    public void setHealthPoints(double h) {
        mHealth = h;
    }

    @Override
    public void setMaxHealthPoints(double mh) {
        mMaxHealth = mh;
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        if (getHealthPoints() < 0) {
            mParent.die();
        }
    }

}
