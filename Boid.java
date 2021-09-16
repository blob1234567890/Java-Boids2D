import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class Boid {
    static int boidCount = 0;
    int boidID;

    Color color = Color.BLACK;
    int size = 5;
    int width;
    int height;

    Point2d position;
    Vector2d velocity;
    Vector2d acceleration;
    double viewRadius = 50;
    double maxSpeed = 3;
    double maxForce = 0.4;

    public Boid(double[] position, double[] velocity, int width, int height) {
        this.position = new Point2d(position);
        this.velocity = new Vector2d(velocity);
        this.acceleration = new Vector2d();

        boidID = boidCount;
        boidCount++;

        this.width = width;
        this.height = height;
    }

    public void flock (Boid[] flock) {
        //find neighbors, these are the only boids we are intersted in for finding alignment, cohesion, and seperation
        Boid[] flockNeighbors = this.flockNeighbors(flock);

        //these are "steering" forces or where we want to go
        //add them all together to get acceleration
        Vector2d alignment = this.alignment(flockNeighbors);
        Vector2d cohesion = this.cohesion(flockNeighbors);
        Vector2d seperation = this.seperation(flockNeighbors);

        double alignmentStrength = 0.4;
        double cohesionStrength = 0.7;
        double seperationStrength = 0.9;

        alignment.scale(alignmentStrength);
        cohesion.scale(cohesionStrength);
        seperation.scale(seperationStrength);

        acceleration.add(alignment);
        acceleration.add(cohesion);
        acceleration.add(seperation);
    }

    //choose a new random velocity if you are going off the frame
    private void edges() {
        //should be steering to avoid edges, but I am lazy
        if (position.x > width) {
            velocity.set(-Math.random(), velocity.y);
        } else if (position.x < -width) {
            velocity.set(Math.random(), velocity.y);
        }
        if (position.y > height) {
            velocity.set(velocity.x, -Math.random());
        } else if (position.y < -height) {
            velocity.set(velocity.x, Math.random());
        }
    }

    public void update() {
        position.add(velocity);
        velocity.add(acceleration);
        setMag(velocity, maxSpeed);
        this.edges();
    }

    //find the local flock mates
    private Boid[] flockNeighbors(Boid[] flock) {
        ArrayList<Boid> neighborList = new ArrayList<Boid>();
        for (Boid other : flock) {
            double d = this.position.distance(other.position);
            if (other != this && d <= viewRadius) {
                neighborList.add(other);
                if (other.boidID == boidID) {
                }
            }
        }

        Boid[] neighbors = new Boid[neighborList.size()];
        return neighborList.toArray(neighbors);
    }

    //main drivers of the flock
    private Vector2d alignment(Boid[] flock) {
        //alignment - steer to align with average velocity of local flockmates
        Vector2d steering = new Vector2d();
        for (Boid other : flock) {
            steering.add(other.velocity);
        }
        if (flock.length > 0) {
            steering.scale(1.0 / flock.length); //averages velocities
            setMag(steering, maxSpeed);
            steering.sub(this.velocity);
            limit(steering, maxForce);
        }
        return steering;
    }

    private Vector2d cohesion(Boid[] flock) {
        //cohesion - steer towards the average position of local flockmates
        Vector2d steering = new Vector2d();
        for (Boid other : flock) {
            Vector2d posVec = new Vector2d();
            posVec.sub(other.position, this.position); //vector from current position to other position
            steering.add(posVec);
        }
        if (flock.length > 0) {
            steering.scale(1.0 / flock.length);
            setMag(steering, maxSpeed);
            steering.sub(this.velocity);
            limit(steering, maxForce);
        }
        return steering;
    }

    private Vector2d seperation(Boid[] flock) {
        // // // this is broken
        //seperation - steer to avoid crowding local flockmates
        Vector2d steering = new Vector2d();
        for (Boid other : flock) {
            Vector2d posVec = new Vector2d(); //vector from current position to other position
            posVec.sub(other.position, this.position);

            double d = this.position.distance(other.position);

            posVec.scale(-1.0 / d);
            steering.add(posVec);
        }
        if (flock.length > 0) {
            steering.scale(1.0 / flock.length);
            setMag(steering, maxSpeed);
            steering.sub(this.velocity);
            limit(steering, maxForce);
        }
        return steering;
    }

    //draw boids
    public void draw(Graphics g) {
        g.setColor(color);

        g.fillOval((int)(position.x - size/2.0), (int)(position.y - size/2.0), size, size);
        g.drawLine((int)position.x, (int)position.y, (int)(position.x + velocity.x * size/velocity.length()), (int)(position.y + velocity.y * size/velocity.length()));
    }

    //some extras for vectors
    //set magnitude of vector
    private void setMag(Vector2d v, double s) {
        v.scale(s / v.length());
    }

    //limit magnitude to l
    private void limit(Vector2d v, double l) {
        double n = v.length();
        double m = Math.min(n, l) / n;
        setMag(v, m);

    }

}
