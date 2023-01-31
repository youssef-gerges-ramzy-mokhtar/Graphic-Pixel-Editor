

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class BlurTool{


    private LayersHandler layerHandler;

    public BlurTool(OurCanvas canvas)
    {
        this.layerHandler = LayersHandler.getLayersHandler(canvas);
    }

   





	// Blurs the entire layer
	public void blur() throws IOException, InterruptedException
	{

		Color color[];

		// converts the current layer into a buffered image
        LayerData currentLayer = layerHandler.getDrawingLayer();
		BufferedImage input = new BufferedImage(currentLayer.getWidth(), currentLayer.getHeight(), BufferedImage.TYPE_INT_ARGB);
        

		// Again creating an object of BufferedImage to
		// create output Image
		BufferedImage output = new BufferedImage(
			input.getWidth(), input.getHeight(),
			BufferedImage.TYPE_INT_RGB);

		// Setting dimensions for the image to be processed
		int i = 0;
		int max = 400, rad = 10;
		int a1 = 0, r1 = 0, g1 = 0, b1 = 0;
		color = new Color[max];

		// Now this core section of code is responsible for
		// blurring of an image

		int x = 1, y = 1, x1, y1, ex = 5, d = 0;

		// Running nested for loops for each pixel
		// and blurring it
		for (x = rad; x < input.getHeight() - rad; x++) {
			for (y = rad; y < input.getWidth() - rad; y++) {
				for (x1 = x - rad; x1 < x + rad; x1++) {
					for (y1 = y - rad; y1 < y + rad; y1++) {
						color[i++] = new Color(
							input.getRGB(y1, x1));
					}
				}

				// Smoothing colors of image
				i = 0;
				for (d = 0; d < max; d++) {
					a1 = a1 + color[d].getAlpha();
				}

				a1 = a1 / (max);
				for (d = 0; d < max; d++) {
					r1 = r1 + color[d].getRed();
				}

				r1 = r1 / (max);
				for (d = 0; d < max; d++) {
					g1 = g1 + color[d].getGreen();
				}

				g1 = g1 / (max);
				for (d = 0; d < max; d++) {
					b1 = b1 + color[d].getBlue();
				}

				b1 = b1 / (max);
				int sum1 = (a1 << 24) + (r1 << 16)
						+ (g1 << 8) + b1;
				output.setRGB(y, x, (int)(sum1));
			}
		}

		// Writing the blurred image on the disc where
		// directory is passed as an argument
		ImageIO.write(output, "jpeg", new File("D:/test/BlurredImage.jpeg"));

		// Message to be displayed in the console when
		// program is successfully executed
		System.out.println("Image blurred successfully !");
	}
}


