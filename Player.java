import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Player extends Character
{
    private Vector2 input = new Vector2();
    private boolean fire = false;
    private Control control = Control.DEFAULT;
    
    Player(final Vector2 p_position, final Vector2 p_scale, final double p_rotation) {
        super(p_position, p_scale, p_rotation);
    }
    Player(final Vector2 p_position, final Vector2 p_scale) {
        super(p_position, p_scale);
    }
    Player(final Vector2 p_position, final double p_rotation) {
        super(p_position, p_rotation);
    }
    Player(final Vector2 p_position) {
        super(p_position);
    }
    Player() {
       super();
    }
    
    public static class Control {
        public Control(
            final String leftControl, final String rightControl, 
            final String upControl, final String downControl,
            final String gunControl
        ) {
            left = leftControl;
            right = rightControl;
            up = upControl;
            down = downControl;
            gun = gunControl;
        }
        public String left;
        public String right;
        public String up;
        public String down;
        public String gun;
        public static Control DEFAULT = new Control("a", "d", "w", "s", "space");
    };    
    
    public void act()
    {
        updateMovementKeys();
        updateCommandKeys();
        super.act();
    }
    
    protected void updateMovementKeys() {
        input.set(
            (Greenfoot.isKeyDown(control.right)? 1 : 0)+(Greenfoot.isKeyDown(control.left)? -1 : 0), 
            (Greenfoot.isKeyDown(control.down)? 1 : 0)+(Greenfoot.isKeyDown(control.up)? -1 : 0)
        );
        input.normalized();
    }
    
    protected void updateCommandKeys() {
        final String key = Greenfoot.getKey();
        fire = key == null? false : key.equals(control.gun);
    }
    
    protected final Vector2 getDirectionalInput() {
        return input;
    }
    protected final boolean isFire() {
        return fire;
    }
}
