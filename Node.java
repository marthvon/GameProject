import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Node extends Actor
{
    private Node parent = null;
    private ArrayList<Node> childNodes = new ArrayList<Node>();
    private Transform transform;
    private Transform globalTransformCache; 
    private boolean update_dirty = false;
    
    public Node() {
        super();
    }
    public Node(final Vector2 p_position, final double p_rotation) {
        super();
    }
    public Node(final Vector2 p_position) {
        super();
    }
    
    protected void finalize() {
        removeFromSceneTree();
    }
    
    public Transform getGlobalTransform() {
        if(parent == null)
            return new Transform(transform);
        else if(update_dirty) {
            globalTransformCache = parent.getGlobalTransform().multiplied(transform);
            update_dirty = false;
        }
        return new Transform(globalTransformCache);
    }
    public void propagateDrawUpdate() {
        update_dirty = true;
        for(Node child : childNodes) {
            if(getWorld() != child.getWorld())
                continue;
            child.propagateDrawUpdate();
        }
    }
    
    public void setPosition(final Vector2 pos) {
        transform.setLocalPosition(pos);
        propagateDrawUpdate();
    }
    public final Vector2 getPosition() {
        return transform.getLocalPosition();
    }
    public void setScale(final Vector2 scale) {
        transform.setScale(scale);
        propagateDrawUpdate();
    }
    public final Vector2 getScale() {
        return transform.getScale();
    }
    public void setRotation(final double radian) {
        transform.setRotation(radian);
        propagateDrawUpdate();
    }
    public double getRotationAngle() {
        return transform.getRotation();
    }
    
    public void removeFromSceneTree() {
        for(Node child: childNodes)
            child.removeFromSceneTree();
        getWorld().removeObject(this);
    }
    
    public boolean addChildToSceneTree(final boolean propagate_child) {
        if(getWorld() == null)
            return false;
        for(Node child: childNodes) {
            if(child.getWorld() != null)
                continue;
            getWorld().addObject(child, 0, 0);
            if(propagate_child)
                child.addChildToSceneTree(true);
        }
        return true;
    }
    public boolean addChildToSceneTree(final int index) {
        Node child = childNodes.get(index);
        if(child.getWorld() != null || getWorld() == null)
            return false;
        getWorld().addObject(child, 0, 0);
        return true;
    }
    public boolean addChildToSceneTree(final int index, final boolean propagate_child) {
        Node child = childNodes.get(index);
        if(child.getWorld() != null || getWorld() == null)
            return false;
        getWorld().addObject(child, 0, 0);
        return true;
    }
    
    public void updateDraw() {
        if(!update_dirty)
            return;
        getGlobalTransform();
        //setLocation;
        //setRotation;
        update_dirty = false;
    }
    
    public void act() {
        updateDraw();
    }
}
