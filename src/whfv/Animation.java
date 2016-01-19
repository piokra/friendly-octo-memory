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
package whfv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import whfv.resources.ImageType;
import whfv.resources.ResourceBank;
import whfv.utill.Pair;

/**
 *
 * @author Pan Piotr
 */
public class Animation implements Drawable, Processable {

    private final LinkedList<Pair<String, Integer>> mStates;
    private final Texture mTexture;
    private final int mYPerState;
    private final int mXPerState;
    private final int mMilliPerChange;
    private final Shape mDrawable;
    private final int mMaxXStates;
    private int mXState = 0;
    private int mYState = 0;
    private double mAnimationTime = 0;

    public static Animation loadAnimation(Path p) throws IOException {
        File f = p.toFile();

        Scanner scanner = new Scanner(f);
        String filename = scanner.nextLine();
        Image img = (Image) ResourceBank.getResource(ImageType.TYPE, filename);

        int x, y, m;
        x = scanner.nextInt();
        y = scanner.nextInt();
        m = scanner.nextInt();
        int r, g, b;
        r = scanner.nextInt();
        g = scanner.nextInt();
        b = scanner.nextInt();
        System.out.println(r + " " + g + " " + b);
        img.createMaskFromColor(new Color(r, g, b));
        Texture t = new Texture();
        try {
            t.loadFromImage(img);
        } catch (TextureCreationException ex) {
            Logger.getLogger(Animation.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException();
        }

        Animation ani = new Animation(t, x, y, m, null);
        int j = scanner.nextInt();
        for (int i = 0; i < j; i++) {
            int pos = scanner.nextInt();
            String s = scanner.nextLine();
            s = s.trim();

            ani.addState(s, pos);
        }
        return ani;
    }

    public Animation(Texture mTexture, int mYPerState, int mXPerState,
            int mMilliPerChange, Shape mDrawable) {
        this(mTexture, mYPerState, mXPerState, mMilliPerChange, mDrawable, new LinkedList<>());
    }

    public Animation(Texture mTexture, int mYPerState, int mXPerState,
            int mMilliPerChange, Shape mDrawable, LinkedList<Pair<String, Integer>> mStates) {
        this.mTexture = mTexture;
        this.mYPerState = mYPerState;
        this.mXPerState = mXPerState;
        this.mMilliPerChange = mMilliPerChange;
        this.mDrawable = mDrawable;
        mMaxXStates = mTexture.getSize().x / mXPerState;
        if (mDrawable != null) {
            mDrawable.setTexture(mTexture);
        }
        this.mStates = mStates;
    }

    public Animation(Animation other, Shape drawable) {
        this(other.mTexture, other.mYPerState, other.mXPerState,
                other.mMilliPerChange, drawable, other.mStates);
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        mDrawable.setTextureRect(new IntRect(mXPerState * mXState,
                mYPerState * mYState, mXPerState, mYPerState));
        mDrawable.draw(target, states);
    }

    @Override
    public void process(double timestep) {
        mAnimationTime += timestep;
        while (mAnimationTime > mMilliPerChange) {
            mAnimationTime -= mMilliPerChange;
            mXState++;
            mXState %= mMaxXStates;
        }
    }

    public void addState(String state, int pos) {
        mStates.add(new Pair<>(state, pos));
    }

    public void setState(String state) {
        for (Pair<String, Integer> mState : mStates) {
            if (mState.first.equals(state)) {
                setState(mState.second);
                return;
            }
        }
    }

    public void setState(int pos) {
        if (mYState != pos) {
            mYState = pos;
            mXState = 0;
        }
    }
}
