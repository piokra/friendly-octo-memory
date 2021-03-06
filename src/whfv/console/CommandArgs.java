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

import java.util.logging.Level;
import java.util.logging.Logger;
import static whfv.console.ArgumentType.NotAType;
import static whfv.console.ArgumentType.Number;
import static whfv.console.ArgumentType.Text;

/**
 *
 * @author Uzytkownik
 */
public class CommandArgs {

    private final ArgumentType[] mArgumentTypes;
    private final String mCommandName;

    public CommandArgs(String cmdName, ArgumentType[] types) {
        mCommandName = cmdName;
        mArgumentTypes = types;
    }

    protected static String[] separate(String unseparated) {

        String[] t = unseparated.split(" ");
        int count = 0;
        int i = 0;
        for (String s : t) {
            t[i] = s.trim();
            if (s.length() > 0) {
                count++;
            }
            i++;
        }
        int pos = 0;
        String[] ret = new String[count];
        for (String s : t) {
            if (s.length() > 0) {
                ret[pos] = s;
                pos++;
            }
        }
        return ret;
    }

    public boolean matches(String unseparated) {
        return matches(separate(unseparated));
    }

    public boolean matches(String[] separatedText) {

        
        if (separatedText.length != (mArgumentTypes.length + 1)) {
            System.out.println("Wrong length Got: "+separatedText.length + "Expected: "+(mArgumentTypes.length+1));
            return false;
        }

        if (!mCommandName.equalsIgnoreCase(separatedText[0])) {

            return false;
        }

        
        for (int i = 1; i < separatedText.length; i++) {

            if (getArgumentType(separatedText[i]) != mArgumentTypes[i - 1]) {

                return false;
            }

        }

        return true;
    }

    static protected ArgumentType getArgumentType(String str) {
        if (str.length() == 0) {
            return NotAType;
        }
        String str2;
        if (str.charAt(0) == 'N') {
            str2 = str.substring(1);
        } else {
            str2 = str;
        }
        try {

            Float.valueOf(str2);
            return Number;

        } catch (NumberFormatException e) {
            Logger.getLogger(CommandArgs.class.getName()).log(Level.INFO, null, e);
        }

        return Text;
    }

    static protected Float getFloatFromString(String str) {
        if (getArgumentType(str) != Number) {
            return Float.NaN;
        }
        if (str.charAt(0) == 'N') {
            str = str.substring(1);
        }
        return Float.valueOf(str);
    }

    static protected String getStringFromString(String str) {
        if (getArgumentType(str) != Text) {
            return "";
        }
        if (str.charAt(0) == 'T') {
            str = str.substring(1);
        }
        return str;
    }
}
