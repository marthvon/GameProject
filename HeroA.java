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
            super();
            self = p_self;
        }
        public void run() {
            
            
        }
    }
    
    private class HeroAAnimation extends Animation {
        private final static int IDLE = 0;
        private final static int WALKING = 1;
        private final static int GUN = 2;
        
        public HeroAAnimation(HeroA p_self) {
            super();
            self = p_self;
            
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            spriteSheet.add(new ArrayList<Animation.Sprite>());
            
            for(int i = 1; i <= 5; ++i)
                spriteSheet.get(IDLE).add(new Animation.Sprite(new GreenfootImage("idle" + i +".png"), 6));
                
            for(int i = 1; i <= 8; ++i)
                spriteSheet.get(WALKING).add(new Animation.Sprite(new GreenfootImage("walking" + i +".png"), 5, 2));
                
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun1.png"), 7));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun2.png"), 3));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun3.png"), 3));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun4.png"), 2));
            spriteSheet.get(GUN).add(new Animation.Sprite(new GreenfootImage("gun5.png"), 1));
            
            GreenfootImage texture = spriteSheet.get(0).get(0).sprite;
            //self.getImage().drawImage(texture, texture.getWidth(), texture.getHeight());
            self.setImage(texture);
        }
    }
}
