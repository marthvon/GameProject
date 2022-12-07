import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Player extends Character
{
    Vector2 input = new Vector2();
    Control control = Control.DEFAULT;
    
    public Player(final Vector2 position, final double rotation) {
        super();
        setPhysics(new PlayerPhysics());
        setAnimation(new PlayerAnimation());
    }
    public Player(final Vector2 position, final double rotation, final Control p_control) {
        super();
        control = p_control;
    }
    
    public static class Control {
        public Control(
            final String leftControl, final String rightControl, 
            final String upControl, final String downControl
        ) {
            left = leftControl;
            right = rightControl;
            up = upControl;
            down = downControl;
        }
        public String left;
        public String right;
        public String up;
        public String down;
        public static Control DEFAULT = new Control("a", "d", "w", "s");
    };    
    
    private class PlayerPhysics extends Physics {
        public void run() {
        
        }
    }
    
    private class PlayerAnimation extends Animation {
        public void run() {
        
        }
    }
    
    public void act()
    {
        updateMovementKeys();
        super.act();
    }
    
    public void updateMovementKeys() {
        input.set(
            (Greenfoot.isKeyDown(control.right)? 1 : 0)+(Greenfoot.isKeyDown(control.left)? -1 : 0), 
            (Greenfoot.isKeyDown(control.down)? 1 : 0)+(Greenfoot.isKeyDown(control.up)? -1 : 0)
        );
        input.normalized();
    }
}
