import greenfoot.*;
import java.util.ArrayList;

public class HeroA extends Player 
{
    HeroA(final Vector2 p_position, final Vector2 p_scale, final double p_rotation) {
        super(p_position, p_scale, p_rotation);
        setAnimation(new HeroAAnimation(this));
        setPhysics(new HeroAPhysics(this));
    }
    HeroA(final Vector2 p_position, final Vector2 p_scale) {
        super(p_position, p_scale);
        setAnimation(new HeroAAnimation(this));
        setPhysics(new HeroAPhysics(this));
    }
    HeroA(final Vector2 p_position, final double p_rotation) {
        super(p_position, p_rotation);
        setAnimation(new HeroAAnimation(this));
        setPhysics(new HeroAPhysics(this));
    }
    HeroA(final Vector2 p_position) {
        super(p_position);
        setAnimation(new HeroAAnimation(this));
        setPhysics(new HeroAPhysics(this));
    }
    HeroA() {
       super();
       setAnimation(new HeroAAnimation(this));
       setPhysics(new HeroAPhysics(this));
       setControl(new HeroAControl(Control.DEFAULT, "space"));
    }
    
    protected final static int STATE_ACT_GUN = 2;
    
    private final static int max_health = 100;
    private int health = 100;
    
    public static class State {
        public final static int IDLE = 0;
        public final static int WALKING = 1;
        public final static int GUN = 2;
    }
    
    private class HeroAPhysics extends Physics {
        private final static double speed = 10;
        private final static double max_speed = 30;
        private final static double acceleration = 10;
        public HeroAPhysics(HeroA p_self) {
            super(p_self);
        }
        public void run() {
            Player player = (Player)self;
            
        }
    }
    
    private class HeroAAnimation extends Animation {
        
        public HeroAAnimation(HeroA p_self) {
            super(p_self);
            
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            
            for(int i = 1; i <= 5; ++i)
                spriteSheet.get(STATE_IDLE).add(new Animation.Sprite(new GreenfootImage("idle" + i +".png"), 6));
                
            for(int i = 1; i <= 8; ++i)
                spriteSheet.get(STATE_WALKING).add(new Animation.Sprite(new GreenfootImage("walking" + i +".png"), 5, STATE_WALKING));
                
            spriteSheet.get(STATE_ACT_GUN).add(new Animation.Sprite(new GreenfootImage("gun1.png"), 7));
            spriteSheet.get(STATE_ACT_GUN).add(new Animation.Sprite(new GreenfootImage("gun2.png"), 3));
            spriteSheet.get(STATE_ACT_GUN).add(new Animation.Sprite(new GreenfootImage("gun3.png"), 3));
            spriteSheet.get(STATE_ACT_GUN).add(new Animation.Sprite(new GreenfootImage("gun4.png"), 2));
            spriteSheet.get(STATE_ACT_GUN).add(new Animation.Sprite(new GreenfootImage("gun5.png"), 1));
            
            self.setTexture(new GreenfootImage(spriteSheet.get(0).get(0).sprite));
        }
        
        //put this outside as seperater
        public void run() {
            final Player player = ((Player)self); 
            switch(currentState) {
                case STATE_IDLE:
                case STATE_WALKING: /*
                    if(player.isFire()) {
                        currentState = GUN;
                        currentFrame = 0;
                        break;
                    }
                    final int lastState = currentState;
                    final Vector2 input = player.getDirectionalInput();
                    final boolean isLength = (input.getMagnitude() > 0);
                    currentState = isLength? STATE_WALKING: STATE_IDLE;
                    if(lastState != currentState)
                        currentFrame = 0;
                    
                    if( isLength && input.x != 0 &&
                        !((player.getLocalTransform().basisDeterminant() > 0) ^ (input.x > 0))
                    ) {
                        final Vector2 scale = player.getLocalTransform().getScale();
                        self.setScale(new Vector2(-1 * scale.x, 1 * Math.abs(scale.y)));
                    }*/
                break;
                default:
                break;
            }
            super.run();
        }
    }
    
    protected class HeroAControl extends Control {
        private String ACT_GUN;
        public HeroAControl(
            final String leftControl, final String rightControl, 
            final String upControl, final String downControl,
            final String gunControl
        ) {
            super(leftControl, rightControl, upControl, downControl);
            ACT_GUN = gunControl;
        }
        public HeroAControl(final Control control, final String gunControl) {
            super(control);
            ACT_GUN = gunControl;
        }
        
        protected boolean fire = false;
        public boolean isFire() {
            return fire;
        }
        
        public void updateKeys() {
            final String key = Greenfoot.getKey();
            fire = key == null? false : key.equals(ACT_GUN);
            super.updateKeys();
        }
        
        
        public void digestInput(Player self) {
            switch(currentState) {
                case STATE_IDLE:
                case STATE_WALKING:
                    if(fire) {
                        currentState = STATE_ACT_GUN;
                        currentFrame = 0;
                        break;
                    }
                    final int lastState = currentState;
                    final boolean isLength = (input.getMagnitude() > 0);
                    currentState = isLength? STATE_WALKING: STATE_IDLE;
                    if(lastState != currentState)
                        currentFrame = 0;
                    
                    if( isLength && input.x != 0 &&
                        !((self.getLocalTransform().basisDeterminant() > 0) ^ (input.x > 0))
                    ) {
                        final Vector2 scale = self.getLocalTransform().getScale();
                        self.setScale(new Vector2(-1 * scale.x, 1 * Math.abs(scale.y)));
                    }
                break;
                default:
                break;
            }
        }
    };
}
