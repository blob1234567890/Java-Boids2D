import javax.swing.JFrame;

public class Boids_Runner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Boids");

        int[] frameDimensions = {1000, 600};

        Boids_Scene scene = new Boids_Scene(frameDimensions);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scene);
        frame.pack();
        frame.setVisible(true);

        scene.animate();
    }
}
