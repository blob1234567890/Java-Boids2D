import java.awt.Color;
import java.awt.Graphics;

public class Flock {
    public int flock_size;
    Boid[] boidArray;

    public Flock(int[] frameDimensions) {
        this.flock_size = 200;

        int width = frameDimensions[0]/2;
        int height = frameDimensions[1]/2;

        boidArray = new Boid[this.flock_size];
        for (int i = 0; i < this.flock_size; i++) {

            //random positions
            double x = (-1 * width) + (Math.random() * (((width/2) - (-1 * width)) + 1));
            double y = (-1 * height) + (Math.random() * (((height/2) - (-1 * height)) + 1));
            double[] position = new double[] {x, y};

            //random velocity
            x = (Math.random() * 2) - 1;
            y = (Math.random() * 2) - 1;
            double[] velocity = new double[] {x, y};

            //add new Boid to boidArray
            boidArray[i] = new Boid(position, velocity, width, height);
        }
    }

    public void update(){
        //umm, update positions of all boids?

        for (Boid boid : boidArray) {
            boid.flock(this.boidArray);
            boid.update();
            boid.acceleration.scale(0);
        }
    }

    public void draw(Graphics g) {
        for (Boid boid : boidArray) {
            boid.draw(g);
        }
    }
}
