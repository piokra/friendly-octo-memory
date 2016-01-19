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

/**
 *
 * @author Pan Piotr
 */
public interface Health {
    default void dealDamage(Damage damage) {
        damage.dealDamage(this);
    }
    
    void dealSuperDamage(Damage d);
    void dealPhysicalDamage(PhysicalDamage d);
    void dealMagicalDamage(MagicalDamage d);
    
    double getHealthPercentile();
    double getHealthPoints();
    double getMaxHealthPoints();
    
    void setHealthPercentile(double p);
    void setHealthPoints(double h);
    void setMaxHealthPoints(double mh);
    
    default void onDamageReceived(double d) {
        
    }
    default void onMagicalDamageReceived(double d) {
        
    }
    
    default void onPhysicalDamageReceived(double d) {
        
    }
}
