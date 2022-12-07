import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Character extends Node
{
    private Animation animation = null;
    private Thread animThread = null;
    private Physics physics = null;
    private Thread physThread = null;
    
    //move to node
    protected Vector2 position;
    protected Vector2 rotation;
    protected ArrayList<CollisionArea> ChildNodes;
    
    Character() {
        position = new Vector2(getX(), getY());
        final double rad = Math.toRadians(getRotation());
        rotation = new Vector2(Math.cos(rad), Math.sin(rad)); //check if sign is right
    }
    Character(final Vector2 p_position, final double p_rotation) {
        position = p_position;
        setLocation((int)position.x, (int)position.y);
        
        final double rad = Math.toRadians(p_rotation);
        rotation = new Vector2(Math.cos(rad), Math.sin(rad));
        setRotation((int)(p_rotation));
    }
    
    protected class Physics implements Runnable {
        double delta = 0;
        public void setDelta(final double p_delta) {
            delta = p_delta;
        }
        public void run() {}
    }
    
    protected abstract class Animation implements Runnable {
        double delta = 0;
        public void setDelta(final double p_delta) {
            delta = p_delta;
        }
        public void run() {}
    }
    
    protected void setAnimation(Animation p_animation) {
        animation = p_animation;
    }
    protected void setPhysics(Physics p_physics) {
        physics = p_physics;
    }
    
    private void runAnimation(final double delta) {
        if(animation == null)
            return;
        if(animThread != null) {
            try {
                animThread.join();
            } catch (InterruptedException e) { 
                e.printStackTrace();
                return;
            }
        }
        animation.setDelta(delta);
        animThread = new Thread(animation);
    }
    
    private void runPhysics(final double delta) {
        if(physics == null)
            return;
        if(physThread != null) {
            try {
                physThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        physics.setDelta(delta);
        physThread = new Thread(physics);
    }

    public void act() {
        double delta = 0;
        if(getWorld() instanceof Arena)
            delta = ((Arena)getWorld()).getTimeStep();
        runPhysics(delta);
        runAnimation(delta);
    }
}
