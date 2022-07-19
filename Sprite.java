import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Abstract Sprite class for moving images to be drawn on screen.
 * Concrete subclasses should override tick() for doing physics and such.
 * @author Henry Leonard
 */
public abstract class Sprite {
    private BufferedImage image;
    private int x = 0;
    private int y = 0;
    private int width;
    private int height;
    
    /**
     * Instantiate a new Sprite using the given image path.
     * @param image The path to an image.
     * @throws IOException if the image could not be loaded.
     */
    protected Sprite(String image) throws IOException {
        this.loadImage(image);
    }

    /**
     * Called at regular, constant intervals to update sprite physics.
     * Sprite motion, etc., should be done here.
     */
    public abstract void tick();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set both x and y coordinates.
     * @param x The x (horizontal) coordinate.
     * @param y The y (vertical) coordinate.
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Replace the current image with a new image loaded from the requested path.
     * @param path The path of the new image.
     * @throws IOException if the image could not be loaded.
     */
    public void loadImage(String path) throws IOException {
        this.image = ImageIO.read(new File(path));
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    /**
     * Get the bounds of this sprite.
     * @return The bounding box of this sprite as a Rectangle.
     */
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    /**
     * Draw this sprite.
     * @param graphics2D The Graphics (brush) object.
     */
    public void paint(Graphics2D graphics2D) {
        graphics2D.drawImage(this.image, this.x, this.y, this.width, this.height, null);
    }

    /**
     * Determine whether this sprite overlaps with another.
     * @param other The other sprite.
     * @return true if any part of this sprite overlaps any part of the other sprite; false otherwise.
     */
    public boolean intersect(Sprite other) {
        return this.getBounds().intersects(other.getBounds());
    }
}