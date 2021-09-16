# Java-Boids2D
2D Boids written in Java.

What are boids? A boid simulation is a flocking simulation using emergent behavior from localized rules.

I do use vecmath Point2d and Vector2D which is not default with Java. Make sure you download [Java 3D](https://www.oracle.com/java/technologies/javase/java-3d.html).

A few fun variables to play with: `flock_size` in `Flock.java`; `viewRadius`, `maxSpeed`, and `maxForce` plus `alignmentStrength`, `cohesionStrength`, and `seperationStrength` under the `flock` method, all in `Boid.java`.
