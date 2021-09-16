import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;

public class Boids_Scene extends JPanel {
    private int[] frameDimensions = new int[2];
    private Flock flock;

    public Boids_Scene(int[] frameDimensions) {
        this.frameDimensions = frameDimensions.clone();

        flock = new Flock(frameDimensions);
    }

    public Dimension getPreferredSize() {
        return new Dimension(frameDimensions[0], frameDimensions[1]);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //center flock
        g.translate(frameDimensions[0] / 2, frameDimensions[1] / 2);

        flock.draw(g);
    }

    public void animate() {
        int waitTime = 15;
        while (true) {
            try {
				Thread.sleep(waitTime);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

            flock.update();

            repaint();
        }
    }

    private static final long serialVersionUID = 42l;
}
