import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

public class Tube {
    private int x;
    private int yTop;
    private int yBottom;
    private boolean passed; // Indicates if the tube has been passed by the bird
    private Image imageTop;
    private Image imageBottom;
    private Random random;

    public Tube() {
        x = 500; // Initial x position
        yTop = -200; // Initial y position for top tube
        yBottom = 300; // Initial y position for bottom tube
        passed = false; // Initialize to false, as the tube starts off-screen
        random = new Random();
    }

    public void update() {
        x -= 2; // Move the tube towards the left
    }

    public Image getImageTop() {
        if (imageTop == null) {
            imageTop = Util.loadImage("lib/tube.png");
        }
        return imageTop;
    }

    public Image getImageBottom() {
        if (imageBottom == null) {
            imageBottom = Util.loadImage("lib/tube2.png");
        }
        return imageBottom;
    }

    public int getX() {
        return x;
    }

    public int getYTop() {
        return yTop;
    }

    public int getYBottom() {
        return yBottom;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getWidth() {
        // Assuming both top and bottom images have the same width
        if (imageTop != null) {
            return imageTop.getWidth(null);
        } else {
            return 0;
        }
    }

    public int getHeight() {
        // Assuming both top and bottom images have the same height
        if (imageTop != null) {
            return imageTop.getHeight(null);
        } else {
            return 0;
        }
    }

    public Rectangle getTopBounds() {
        return new Rectangle(x, yTop, getWidth(), getHeight());
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(x, yBottom, getWidth(), getHeight());
    }

    public boolean intersects(Bird bird) {
        return getTopBounds().intersects(bird.getBounds()) || getBottomBounds().intersects(bird.getBounds());
    }

    public boolean isBottomTube() {
        // Randomly determine if this tube is a bottom tube
        return random.nextBoolean();
    }
}
