package shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
 
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  newColour   the new colour for the foreground of the Canvas 
     */
    public void setForegroundColor(String colorString){
        switch (colorString.toLowerCase()) {
            case "red": graphic.setColor(Color.red); break;
            case "black": graphic.setColor(Color.black); break;
            case "blue": graphic.setColor(Color.blue); break;
            case "yellow": graphic.setColor(Color.yellow); break;
            case "green": graphic.setColor(Color.green); break;
            case "magenta": graphic.setColor(Color.magenta); break;
            case "white": graphic.setColor(Color.white); break;
            case "orange": graphic.setColor(Color.orange); break;
            case "cyan": graphic.setColor(Color.cyan); break;
            case "pink": graphic.setColor(Color.pink); break;
            case "gray": graphic.setColor(Color.gray); break;
            case "darkgray": graphic.setColor(Color.darkGray); break;
            case "lightgray": graphic.setColor(Color.lightGray); break;
            case "brown": graphic.setColor(new Color(139, 69, 19)); break;
            case "purple": graphic.setColor(new Color(128, 0, 128)); break;
            case "violet": graphic.setColor(new Color(238, 130, 238)); break;
            case "gold": graphic.setColor(new Color(255, 215, 0)); break;
            case "silver": graphic.setColor(new Color(192, 192, 192)); break;
            case "beige": graphic.setColor(new Color(245, 245, 220)); break;
            case "turquoise": graphic.setColor(new Color(64, 224, 208)); break;
            case "indigo": graphic.setColor(new Color(75, 0, 130)); break;
            case "maroon": graphic.setColor(new Color(128, 0, 0)); break;
            case "navy": graphic.setColor(new Color(0, 0, 128)); break;
            case "olive": graphic.setColor(new Color(128, 128, 0)); break;
            case "teal": graphic.setColor(new Color(0, 128, 128)); break;
            case "salmon": graphic.setColor(new Color(250, 128, 114)); break;
            case "coral": graphic.setColor(new Color(255, 127, 80)); break;
            case "khaki": graphic.setColor(new Color(240, 230, 140)); break;
            case "lavender": graphic.setColor(new Color(230, 230, 250)); break;
            case "orchid": graphic.setColor(new Color(218, 112, 214)); break;
            case "plum": graphic.setColor(new Color(221, 160, 221)); break;
            case "crimson": graphic.setColor(new Color(220, 20, 60)); break;
            case "chartreuse": graphic.setColor(new Color(127, 255, 0)); break;
            case "lime": graphic.setColor(new Color(0, 255, 0)); break;
            case "aquamarine": graphic.setColor(new Color(127, 255, 212)); break;
            case "peru": graphic.setColor(new Color(205, 133, 63)); break;
            case "seagreen": graphic.setColor(new Color(46, 139, 87)); break;
            case "slategray": graphic.setColor(new Color(112, 128, 144)); break;
            case "dodgerblue": graphic.setColor(new Color(30, 144, 255)); break;
            case "firebrick": graphic.setColor(new Color(178, 34, 34)); break;
            case "deeppink": graphic.setColor(new Color(255, 20, 147)); break;
            case "hotpink": graphic.setColor(new Color(255, 105, 180)); break;
            case "rosybrown": graphic.setColor(new Color(188, 143, 143)); break;
            case "midnightblue": graphic.setColor(new Color(25, 25, 112)); break;
            case "sienna": graphic.setColor(new Color(160, 82, 45)); break;
            case "peachpuff": graphic.setColor(new Color(255, 218, 185)); break;
            case "palegreen": graphic.setColor(new Color(152, 251, 152)); break;
            case "mistyrose": graphic.setColor(new Color(255, 228, 225)); break;
            case "darkorchid": graphic.setColor(new Color(153, 50, 204)); break;
            case "steelblue": graphic.setColor(new Color(70, 130, 180)); break;
            case "powderblue": graphic.setColor(new Color(176, 224, 230)); break;
            case "neonblue": graphic.setColor(new Color(21, 244, 238)); break;
            default: graphic.setColor(Color.black); break;
        }
    }

    

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel{
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }

}

