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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import whfv.resources.FontType;
import whfv.resources.ImageType;
import whfv.resources.ResourceBank;

/**
 *
 * @author Pan Piotr
 */
public class ResourceBankTest {
    
    public ResourceBankTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void imageReadTest() {
        try {
            Image image = (Image) ResourceBank.getResource(ImageType.TYPE, "hero.png");
        } catch (IOException ex) {
            Logger.getLogger(ResourceBankTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
        RenderWindow r = new RenderWindow();
    }
    
    @Test
    public void fontReadTest() {
        try {
            Font font = (Font) ResourceBank.getResource(FontType.TYPE, "SEGOEUI.TTF");
        } catch (IOException ex) {
            Logger.getLogger(ResourceBankTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }
    
}
