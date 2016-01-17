/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.game;

import java.util.Random;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import whfv.BlackRectangle;
import whfv.TransformingShape;
import whfv.position.AbsolutePosition;
import whfv.position.Position;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix2x2d;
import whfv.utill.Matrix3x3d;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;


public class GameObjectImpl implements GameObject {
    private final Random RANDOM = new Random();
    double theta = 0;
    Rect2D r = new Rect2D(new Vector2d(50,50), Vector2d.VECTOR_ZERO);
    TransformingShape ts = new TransformingShape(r.toConvexCollidinShape());
    BlackRectangle br = new BlackRectangle(new AbsolutePosition(new Vector2d(RANDOM.nextDouble()*500,RANDOM.nextDouble()*500)));
    @Override
    public Position getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setPosition(Position position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    Matrix3x3d transform = Matrix3x3d.IDENTITY;
    @Override
    public void process(double timestep) {
        theta+=timestep*0.0001;
        //System.out.println("theta:" +theta);
        transform = Linear2DHTransformations.rotationMatrix(theta);
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        RenderStates r = new RenderStates(states.blendMode, Transform.combine(states.transform,Matrix3x3d.toSFMLTransform(transform)), states.texture, states.shader);
        br.draw(target, r);
    }

    @Override
    public void addMe(GameWorld world) {
        world.addDrawable(this);
        world.addProcessable(this);
    }

    @Override
    public void removeMe(GameWorld world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transform(Matrix2x2d transformation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
