import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Player extends Character
{
    protected final static int STATE_IDLE = 0;
    protected final static int STATE_WALKING = 1;
    
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
            final String upControl, final String downControl
        ) {
            MOVE_LEFT = leftControl;
            MOVE_RIGHT = rightControl;
            MOVE_UP = upControl;
            MOVE_DOWN = downControl;
        }
        public Control(final Control other) {
            MOVE_LEFT = other.MOVE_LEFT;
            MOVE_RIGHT = other.MOVE_RIGHT;
            MOVE_UP = other.MOVE_UP;
            MOVE_DOWN = other.MOVE_DOWN;
        }
        private String MOVE_LEFT;
        private String MOVE_RIGHT;
        private String MOVE_UP;
        private String MOVE_DOWN;
        public static Control DEFAULT = new Control("a", "d", "w", "s");
        
        protected Vector2 input = new Vector2();
        public final Vector2 getDirectionalInput() {
            return input;
        }
        
        public void updateKeys() {
            input.set(
                (Greenfoot.isKeyDown(MOVE_RIGHT)? 1 : 0) + (Greenfoot.isKeyDown(MOVE_LEFT)? -1 : 0), 
                (Greenfoot.isKeyDown(MOVE_DOWN)? 1 : 0) + (Greenfoot.isKeyDown(MOVE_UP)? -1 : 0)
            );
            input.normalized();
        }
        //virtual function. overload this
        public void digestInputs(Player self) {}
    };    
    
    public void act()
    {
        control.updateKeys();
        control.digestInputs(this);
        super.act();
    }
    
    public final Control getControl() {
        return control;
    }
    public void setControl(Control p_map) {
        control = p_map;
    }
}
