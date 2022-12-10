import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Character extends Node
{
    private Animation animation = null;
    private Thread animThread = null;
    protected Physics physics = null;
    private Thread physThread = null;
    
    protected int currentState = 0;
    protected int currentFrame = 0;
    
    Character(final Vector2 p_position, final Vector2 p_scale, final double p_rotation) {
        super(p_position, p_scale, p_rotation);
    }
    Character(final Vector2 p_position, final Vector2 p_scale) {
        super(p_position, p_scale);
    }
    Character(final Vector2 p_position, final double p_rotation) {
        super(p_position, p_rotation);
    }
    Character(final Vector2 p_position) {
        super(p_position);
    }
    Character() {
       super();
    }
    
    protected class Physics implements Runnable {
        protected Character self;
        protected double delta = 0;
        protected int currentState = 0;
        protected int currentFrame = 0;
        public Physics(Character p_self) {
            self = p_self;
        }
        public void arguments(final double p_delta, final int state, final int frame) {
            delta = p_delta;
            currentState = state;
            currentFrame = frame;
        }
        public void passtDelta(final double p_delta) {
            delta = p_delta;
        }
        public void passState(final int state) {
            currentState = state;
        }
        public void passFrame(final int frame) {
            currentFrame = frame;
        }
        public void run() {}
    }
    
    protected abstract class Animation implements Runnable {
        protected Character self;
        protected double delta = 0;
        protected double accum = 0;
        protected int currentState = 0;
        protected int currentFrame = 0;
        public Animation(Character p_self) {
            self = p_self;
        }
        
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
        
        public void arguments(final double p_delta, final int state, final int frame) {
            delta = p_delta;
            currentState = state;
            currentFrame = frame;
        }
        protected void passDelta(final double p_delta) {
            delta = p_delta;
        }
        public void passState(final int state) {
            currentState = state;
        }
        public int returnState() {
            return currentState;
        }
        public void passFrame(final int frame) {
            currentFrame = frame;
        }
        public int returnFrame() {
            return currentFrame;
        }
        public void run() {
            accum += delta;
            ArrayList<Animation.Sprite> state = spriteSheet.get(currentState);
            Animation.Sprite sprite = state.get(currentFrame);
            final int temp = (int)(accum / 16);
            if(sprite.frame <= temp) {
                if((currentFrame + 1) == state.size()) {
                    currentState = sprite.jumpToStateAtEnd;
                    currentFrame = 0;
                } else {
                    ++currentFrame;
                }
                Animation.Sprite newSprite = state.get(currentFrame);
                self.setTexture(new GreenfootImage(newSprite.sprite));
                accum -= temp * 16;
            }
        }
    }
    
    protected void setAnimation(Animation p_animation) {
        animation = p_animation;
    }
    protected void setPhysics(Physics p_physics) {
        physics = p_physics;
    }
    
    private boolean runAnimation(final double delta) {
        if(animation == null)
            return false;
        animation.arguments(delta, currentState, currentFrame);
        animThread = new Thread(animation);
        animThread.start();
        return true;
    }
    
    private boolean runPhysics(final double delta) {
        if(physics == null)
            return false;
        animation.arguments(delta, currentState, currentFrame);
        physThread = new Thread(physics);
        physThread.start();
        return true;
    }

    public void act() {
        double delta = 0;
        if(getWorld() instanceof Arena)
            delta = ((Arena)getWorld()).getTimeStep();
        final boolean successPhysicsThread = runPhysics(delta);
        final boolean successAnimationThread = runAnimation(delta);
        
        if(successPhysicsThread)
            try {
                physThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
        if(successAnimationThread)
            try {
                animThread.join();
            } catch (InterruptedException e) { 
                e.printStackTrace();
            }
        currentState = animation.returnState();
        currentFrame = animation.returnFrame();
        super.act();
    }
}
