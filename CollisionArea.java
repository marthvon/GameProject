import greenfoot.*;
import java.util.ArrayList;

public class CollisionArea extends Node
{
    private static boolean showCollisions = false;
    
    private boolean[] collisionLayer = new boolean[16];
    /*this mask compares with the other layer which notifies this with collision*/
    private ArrayList<Integer> collisionMask = new ArrayList<Integer>();
    
    CollisionArea(final Vector2 p_position, final Vector2 p_scale, final double p_rotation) {
        super(p_position, p_scale, p_rotation);
    }
    CollisionArea(final Vector2 p_position, final Vector2 p_scale) {
        super(p_position, p_scale);
    }
    CollisionArea(final Vector2 p_position, final double p_rotation) {
        super(p_position, p_rotation);
    }
    CollisionArea(final Vector2 p_position) {
        super(p_position);
    }
    CollisionArea() {
       super();
    }
    
    private void CollidingTo(CollisionArea area, final int layer) {
        getParent().notifyCollisionTo(area.getParent(), layer);
    }
    private void CollidedFrom(CollisionArea area, final int mask) {
        getParent().notifyCollisionFrom(area.getParent(), mask);
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
        super.act();
        for(final CollisionArea area : getIntersectingObjects(CollisionArea.class)) {
            for(final Integer mask : collisionMask)
                if (area.getCollisionLayer(mask)) {
                    CollidingTo(area, mask);
                    area.CollidedFrom(this, mask);
                }
        }
    }
}
