/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class CCSG {
    static final Random RANDOM = new Random(0);
    ArrayList<ConvexCollidingShape> generateObjects(int count, Vector2d minCorner, Vector2d maxCorner, double maxLength) {
        ArrayList<ConvexCollidingShape> ret = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Vector2d pos = new Vector2d(RANDOM.nextDouble()*(maxCorner.x-minCorner.x-maxLength), RANDOM.nextDouble()*(maxCorner.x-minCorner.x-maxLength));
            double width = RANDOM.nextDouble()*maxLength;
            double height = RANDOM.nextDouble()*maxLength;
            Vector2d b = new Vector2d(pos.x+height,pos.y);
            Vector2d c = new Vector2d(pos.x+height,pos.y+width);
            Vector2d d = new Vector2d(pos.x,pos.y+width);
            Vector2d[] ar = new Vector2d[]{pos,b,c,d};
            ret.add(generateShape(ar));
        }
        return ret;
    }
    ConvexCollidingShape generateShape(Vector2d[] corners) {
        int[] pointsCount = new int[corners.length];
        for (int i = 0; i < pointsCount.length; i++) {
            pointsCount[i] = RANDOM.nextInt(3);
            
        }
        for(int i=0; i<3; i++) {
            
            pointsCount[i] = Math.max(1, pointsCount[i]);
        }
        int total = 0;
        for (int i : pointsCount) {
            total+=i;
        }
        int n = 0;
        Vector2d[] ret = new Vector2d[total];
        for (int i = 0; i < pointsCount.length; i++) {
            int j = pointsCount[i];
            Vector2d start = corners[i];
            Vector2d end = corners[(i+1)%corners.length];
            double r = 0;
            for (int k = 0; k < j; k++) {
               r = r+RANDOM.nextDouble()*(1-r);
               ret[n] = pointOnSegment(start,end,r);
               n++;
            }
            
        }
        return new ConvexCollidingShape(ret);
    }
    
    Vector2d pointOnSegment(Vector2d start, Vector2d end, double t) {
        Vector2d v = Vector2d.sub(end, start);
        return Vector2d.add(start,Vector2d.mul(t, v));
    }
}
