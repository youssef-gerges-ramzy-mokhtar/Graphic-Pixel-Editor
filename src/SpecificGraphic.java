import java.awt.*;

// Specific Graphic is a general class simply stating that every graphic should have the ability to draw in its own specific way
abstract class SpecificGraphic {
	/**
	 * draw is an abstract method used to define the drawing behaviour of any entity that is rendered into the Screen/Canvas
	 * @param g used to render the entity into the Graphics2D object
	 */
	public abstract void draw(Graphics2D g);
}