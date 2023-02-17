import java.awt.*;

// Specific Graphic is a general class simply stating that every graphic should have the ability to draw in its own specific way
abstract class SpecificGraphic {
	public abstract void draw(Graphics2D g);
}