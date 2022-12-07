import greenfoot.*;
import java.util.ArrayList;

public class CollisionArea extends Node
{
    private static boolean showCollisions = false;
    
    private boolean[] collisionLayer = new boolean[16];
    /*this mask compares with the other layer which notifies this with collision*/
    private ArrayList<Integer> collisionMask = new ArrayList<Integer>();
    
    //move to node
    private Actor parent;
    private Vector2 size;
    private Vector2 offset;
    
    CollisionArea(final Actor p_parent, final Vector2 p_size, final Vector2 p_position) {
        GreenfootImage img = getImage();
        parent = p_parent;
        size = p_size;
        img.scale((int)size.x, (int)size.y);
        offset = p_position;
        //setLocation();
        if(showCollisions) {
            img.setColor(Color.RED);
            img.drawRect(0, 0, (int)size.x, (int)size.y);
            img.fill();
        }
    }
    CollisionArea(final Actor p_parent, final Vector2 p_size) {
        GreenfootImage img = getImage();
        size = p_size;
        img.scale((int)size.x, (int)size.y);
        offset = new Vector2();
        if(showCollisions) {
            img.setColor(Color.RED);
            img.drawRect(0, 0, (int)size.x, (int)size.y);
            img.fill();
        }
    }
    CollisionArea(final Actor p_parent) {
        GreenfootImage img = getImage();
        parent = p_parent;
        if(showCollisions) {
            img.setColor(Color.RED);
            img.drawRect(0, 0, (int)size.x, (int)size.y);
            img.fill();
        }
    }
    
    public void setLocalPosition(final Vector2 p_offset) {
        offset = p_offset;
    }
    public void updatePosition() {
        
    }
    private void CollidingWith(CollisionArea area, final int layer) {
    
    }
    private void CollidesOn(CollisionArea area, final int mask) {
        
    }
    public Actor getParentNode() {
        return parent;
    }
    
    public void toggleCollisionLayer(final int layer) {
        collisionLayer[layer] = !collisionLayer[layer];
    }
    public void toggleCollisionLayer(final int[] layers) {
        for(final int layer : layers)
            collisionLayer[layer] = !collisionLayer[layer];
    }
    public boolean getCollisionLayer(final int layer) {
        return collisionLayer[layer];
    }
    public void setCollisionMask(final int mask) {
        int index = 0;
        while(index != collisionMask.size()) {
            final Integer value = collisionMask.get(index); 
            if(mask < value) {
                collisionMask.add(index, new Integer(mask));
                return;
            } else if (mask == value)
                return;
            ++index;
        }
        collisionMask.add(mask);
    }
    public void removeCollisionMask(final int mask) {
        for(Integer iter : collisionMask){
            if(iter == mask)  {
                collisionMask.remove(iter);
                return;
            }
            else if (mask > iter)
                return;
        }
    }
    
    public void act() {
        for(final CollisionArea area : getIntersectingObjects(CollisionArea.class)) {
            for(final Integer mask : collisionMask)
                if (area.getCollisionLayer(mask)) {
                    
                }
        }
    }
}
