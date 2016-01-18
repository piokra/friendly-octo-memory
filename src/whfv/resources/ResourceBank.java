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
package whfv.resources;

import whfv.console.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Font;
import whfv.utill.Pair;

/**
 *
 * @author Uzytkownik
 */
public class ResourceBank {

    private static final ArrayList<String> mResourceLocations = initResourceLocations();
    private static final HashMap<String, Pair<ResourceType, Object>> mHashMap = new HashMap<>();
    private static final HashMap<ResourceType, ResourceGetter> mResourceGetters = initResourceGetters();

    protected static HashMap<ResourceType, ResourceGetter> initResourceGetters() {
        HashMap<ResourceType, ResourceGetter> map = new HashMap<>();
        map.put(FontType.TYPE, new ResourceGetter() {
            @Override
            public Object loadResource(Path p) throws IOException {
                Font font = new Font();
                font.loadFromFile(p);
                return font;
            }

        });
        return map;
    }

    protected static ArrayList<String> initResourceLocations() {

        ArrayList<String> fontLocations = new ArrayList<>();
        fontLocations.add("res\\fonts\\");
        fontLocations.add("build\\res\\fonts\\");
        fontLocations.add("");
        fontLocations.add(System.getenv("windir") + "\\Fonts\\");

        return fontLocations;
    }

    public static Object getResource(ResourceType type, String resourcename) throws IOException {
        IOException ex = null;
        for (String s : mResourceLocations) {

            try {
                return ResourceBank.getResource(type, FileSystems.getDefault().getPath(s + resourcename), false);
            } catch (IOException ex1) {
                ex = ex1;
            }
        }
        Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        throw ex;

    }

    protected static ResourceGetter getResourceGetter(ResourceType type) {
        return mResourceGetters.get(type);
    }

    protected static void addResourceGetter(ResourceType type, ResourceGetter getter) {
        mResourceGetters.put(type, getter);
    }

    protected static Object loadResource(ResourceType type, Path p) throws IOException {
        return getResourceGetter(type).loadResource(p);
    }

    protected static Object getResource(ResourceType type, Path p, boolean silent) throws IOException {
        Object ret = null;
        String filename = p.getFileName().toString();
        if (mHashMap.containsKey(filename)) {
            Pair<ResourceType, Object> pair = mHashMap.get(filename);
            if (pair.first != type) {
                throw new IOException("Wrong resource type found in hashmap");
            }
            return pair.second;
        }
        Logger.getLogger(ResourceBank.class.getName()).log(Level.INFO, filename);

        try {
            File f = p.toFile();
            if (f == null) {
                throw new NoSuchFileException(filename);
            }
            if (!f.canRead()) {
                throw new NoSuchFileException(filename);
            }

            ret = loadResource(type, p);
            mHashMap.put(filename, new Pair(type, ret));
        } catch (IOException ex) {
            if (!silent) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw ex;
        }
        return ret;
    }

    public static Object getResource(ResourceType type, Path p) throws IOException {
        return ResourceBank.getResource(type, p, false);
    }

    public static Object tryGettingObject(ResourceType type, String resourcename) {

        try {
            return getResource(type, resourcename);
        } catch (IOException ex) {

        }
        return null;
    }
}
