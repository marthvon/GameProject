import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Character extends Node
{
    private Animation animation = null;
    private Thread animThread = null;
    private Physics physics = null;
    private Thread physThread = null;
    
    Character() {
        
    }
    Character(final Vector2 p_position, final double p_rotation) {
        
    }
    Character(final Vector2 p_position) {
    
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
        public class Sprite {
            public GreenfootImage sprite = null;
            public int frame = 0;
            public int jumpToStateAtEnd = 0;
            public Sprite(final GreenfootImage p_sprite, final int p_frame) {
                sprite = p_sprite;
                frame = p_frame;
            }
            public Sprite(final GreenfootImage p_sprite, final int p_frame, final int p_state) {
                sprite = p_sprite;
                frame = p_frame;
                jumpToStateAtEnd = p_state;
            }
        };
        //spriteSheet.get(X).get(Y) wherein X is the state and Y is the frame
        protected ArrayList<ArrayList<Sprite>> spriteSheet = new ArrayList<ArrayList<Sprite>>();
        protected int currentFrame = 0;
        protected int currentState = 0;
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
        super.act();
    }
}
