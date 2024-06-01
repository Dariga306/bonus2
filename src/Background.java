import java.awt.Image;

public class Background {
    private Image image;
    Background(){

    }
    public Image getImage () {

        if (image == null) {
            image = Util.loadImage("lib/bg.png");
        }

        return image;
    }
}