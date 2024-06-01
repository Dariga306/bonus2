import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;
    private double yvel;
    private double gravity;
    private int jumpDelay;
    private boolean collision;
    private Image image;
    private Keyboard keyboard;

    public Bird() {
        x = 100;
        y = 200;
        width = 40;
        height = 40;
        yvel = 0;
        gravity = 0.6;
        jumpDelay = 0;
        collision = false;
        keyboard = Keyboard.getInstance();
    }

    private void checkCollision() {
        if (y > 380) {
            collision = true;
        }
    }

    public void update() {
        yvel += gravity;

        if (jumpDelay > 0)
            jumpDelay--;

        if (keyboard.isDown(KeyEvent.VK_SPACE) && jumpDelay <= 0 && !collision) {
            yvel = -10;
            jumpDelay = 10;
        }

        if (!collision) {
            y += (int) yvel;
        }

        checkCollision();
    }

    public Image getImage() {
        if (image == null) {
            image = Util.loadImage("lib/bird.png");
        }
        return image;
    }

    public boolean isCollided() {
        return collision;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void collide() {
        collision = true;
    }
}
