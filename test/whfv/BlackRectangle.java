/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;
import whfv.UI.View;
import whfv.position.Position;
import whfv.utill.Matrix2x2d;
import whfv.utill.Matrix3x3d;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class BlackRectangle implements View {

    RectangleShape rs = new RectangleShape(new Vector2f(50, 50));
    public BlackRectangle(Position position) {
        mPosition = position;
    }
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        Vector2f pos = new Vector2f((float) getPosition().getCoordinates().x, (float) getPosition().getCoordinates().y);
        rs.setPosition(pos);
        RenderStates newStates = new RenderStates(states.blendMode, Transform.combine(states.transform, Matrix3x3d.toSFMLTransform(mTransformation)), states.texture, states.shader);
        rs.draw(target, newStates);
    }

    @Override
    public void process(double timestep) {
    }

    @Override
    public Event processEvent(Event e) {
        return e;
    }
    private Position mPosition = null;

    @Override
    public Position getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(Position position) {
        mPosition = position;
    }

    @Override
    public Rect2D getBoundingRectangle() {
        return new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(50, 50));
    }

    Matrix3x3d mTransformation = Matrix3x3d.IDENTITY;

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        if (homoTransformation != null) {
            mTransformation = homoTransformation;
        }
    }

    @Override
    public void transform(Matrix2x2d transformation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
