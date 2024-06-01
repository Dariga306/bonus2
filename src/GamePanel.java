import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private Game game;
    private Bird bird;
    private ArrayList<Tube> tubes;
    private Background bg;
    private boolean gameStarted;
    private int timerCount;
    private int tubeSpawnDelay;
    private final int TUBE_SPAWN_INTERVAL = 200; // Adjust this value as needed

    public GamePanel() {
        game = new Game();
        bird = game.bird;
        bg = new Background();
        tubes = new ArrayList<>();
        tubes.add(new Tube());
        gameStarted = false;
        timerCount = 3; // Countdown timer for 3 seconds before starting the game
        tubeSpawnDelay = TUBE_SPAWN_INTERVAL;
        new Thread(this).start();
    }

    public void update() {
        if (!gameStarted) {
            if (timerCount > 0) {
                try {
                    Thread.sleep(1000); // Sleep for 1 second
                    timerCount--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                gameStarted = true;
            }
        } else {
            game.update();
            if (!bird.isCollided()) {
                for (int i = 0; i < tubes.size(); i++) {
                    Tube tube = tubes.get(i);
                    tube.update();
                    if (tube.intersects(bird)) {
                        bird.collide();
                    }
                    if (!tube.isPassed() && tube.getX() + tube.getWidth() < bird.getX()) {
                        tube.setPassed(true);
                        tubeSpawnDelay = TUBE_SPAWN_INTERVAL; // Reset tube spawn delay
                    }
                }
                if (tubeSpawnDelay <= 0) {
                    tubes.add(new Tube());
                    tubeSpawnDelay = TUBE_SPAWN_INTERVAL; // Reset tube spawn delay
                } else {
                    tubeSpawnDelay--;
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);

        for (Tube tube : tubes) {
            g.drawImage(tube.getImageTop(), tube.getX(), tube.getYTop(), null);
            g.drawImage(tube.getImageBottom(), tube.getX(), tube.getYBottom(), null);
        }

        if (!bird.isCollided()) {
            g.drawImage(bird.getImage(), bird.getX(), bird.getY(), null);
        } else {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
        }

        if (!gameStarted) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString(Integer.toString(timerCount), getWidth() / 2 - 10, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        while (true) {
            update();
            try {
                Thread.sleep(23); // roughly 43 frames per second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
