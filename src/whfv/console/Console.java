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
package whfv.console;

import whfv.hotkeys.Hotkeyable;
import whfv.hotkeys.HotkeyProcessor;
import whfv.hotkeys.HotkeyTask;
import whfv.hotkeys.Hotkey;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import whfv.Drawable;
import whfv.EventProcessor;
import whfv.Focusable;
import whfv.resources.FontType;
import whfv.resources.ResourceBank;
import whfv.resources.ResourceType;

public class Console implements Drawable, Focusable, EventProcessor, Hotkeyable {

    private final Commands mCommands;
    private final Font mFont;
    private final int mStringCount;
    private final Vector2i mConsolePosition;
    private final int mConsoleWidth;
    private final ConcurrentLinkedQueue<String> mStrings = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Text> mTexts = new ConcurrentLinkedQueue<>();
    private final Shape mBackgroundShape;
    private final int mFontSize;

    public Console(Commands commands, int stringCount, Vector2i position, int width, int fontSize) throws IOException {
        mCommands = commands;
        mStringCount = stringCount;
        mConsolePosition = position;
        mConsoleWidth = width;
        mFontSize = fontSize;
        mBackgroundShape = new RectangleShape(new Vector2f(width, (stringCount + 1) * (fontSize + 4)));
        mBackgroundShape.setFillColor(new Color(30, 122, 122));
        mHotkeyProcessor.addHotkeyedTask(new Hotkey(Key.TILDE, false), mToggleConsoleTask);

        mFont = (Font) ResourceBank.getResource(FontType.TYPE, "SEGOEUI.TTF");

    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        if (!mHidden) {
            int pos = 4;

            mBackgroundShape.draw(target, states);

            for (Text t : mTexts) {
                t.setPosition(4, pos);
                t.draw(target, states);
                pos += mFontSize + 4;
            }
            Text t = new Text(mTempString, mFont, mFontSize);
            t.setPosition(4, pos);
            t.draw(target, states);

        }

    }
    private boolean mHidden = true;

    public void toggleConsole() {
        mHidden = !mHidden;
    }
    private final HotkeyTask mToggleConsoleTask = new HotkeyTask() {
        @Override
        public void doTask(KeyEvent e) {
            toggleConsole();
            toggleFocus();

        }
    };
    private boolean mFocused = false;
    private String mTempString = "";

    @Override
    public Event processEvent(Event e) {
        e = mHotkeyProcessor.processEvent(e);
        if (e == null) {
            return e;
        }
        if (isFocused()) {
            if (e.type == Event.Type.KEY_PRESSED) {
                if (e.asKeyEvent().key == Keyboard.Key.RETURN) {
                    finalizeInput();
                }

            }
            if (e.type == Event.Type.TEXT_ENTERED) {
                //if(e.asTextEvent().character != '\b' && e.asTextEvent().character < 32) return e;
                if (e.asTextEvent().character != '\b') {
                    mTempString += e.asTextEvent().character;
                } else if (mTempString.length() > 1) {
                    mTempString = mTempString.substring(0, mTempString.length() - 1);
                } else {
                    mTempString = "";
                }

            }

        }
        return e;
    }

    protected void finalizeInput() {
        mStrings.add(mTempString);
        mTexts.add(new Text(mTempString, mFont, mFontSize));
        if (!mCommands.processCommand(mTempString)) {
            mStrings.add("Failed to process: " + mTempString);
            mTexts.add(new Text("Failed to process: " + mTempString, mFont, mFontSize));
        }
        mTempString = "";
        while (mStrings.size() > mStringCount) {
            mStrings.poll();
            mTexts.poll();
        }
    }

    @Override
    public boolean isFocused() {
        return mFocused;
    }

    @Override
    public void toggleFocus() {
        mFocused = !mFocused;

    }

    @Override
    public void unfocus() {
        mFocused = false;
    }

    @Override
    public void focus() {
        mFocused = true;

    }
    private final HotkeyProcessor mHotkeyProcessor = new HotkeyProcessor();

    @Override
    public HotkeyProcessor asHotkeyProcessor() {
        return mHotkeyProcessor;
    }

}
