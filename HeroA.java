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
    }
    
    private class HeroAPhysics extends Physics {
        private final static double speed = 30;
        public HeroAPhysics(HeroA p_self) {
            super(p_self);
        }
        public void run() {
            
            
        }
    }
    
    private class HeroAAnimation extends Animation {
        private final static int IDLE = 0;
        private final static int WALKING = 1;
        private final static int GUN = 2;
        
        public HeroAAnimation(HeroA p_self) {
            super(p_self);
            
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            
            for(int i = 1; i <= 5; ++i)
                spriteSheet.get(IDLE).add(new Animation.Sprite(new GreenfootImage("idle" + i +".png"), 6));
                
            for(int i = 1; i <= 8; ++i)
                spriteSheet.get(WALKING).add(new Animation.Sprite(new GreenfootImage("walking" + i +".png"), 5, WALKING));
                
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun1.png"), 7));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun2.png"), 3));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun3.png"), 3));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun4.png"), 2));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun5.png"), 1));
            
            self.setTexture(new GreenfootImage(spriteSheet.get(0).get(0).sprite));
        }
        
        public void run() {
            final Player player = ((Player)self); 
            switch(currentState) {
                case IDLE:
                case WALKING:
                    if(player.isFire()) {
                        currentState = GUN;
                        currentFrame = 0;
                        break;
                    }
                    final int lastState = currentState;
                    final Vector2 input = player.getDirectionalInput();
                    final boolean isLength = (input.getMagnitude() > 0);
                    currentState = isLength? WALKING: IDLE;
                    if(lastState != currentState)
                        currentFrame = 0;
                    Vector2 scale = new Vector2(player.getLocalTransform().getScale());
                    if( isLength &&
                        !((scale.y > 0) ^ (input.x > 0))
                    ) {
                        player.setRotation(Math.PI + player.getRotationAngle());
                        scale.multiplied(new Vector2(1, -1));
                        player.setScale(scale);
                    }
                break;
                default:
                break;
            }
            super.run();
        }
    }
}
