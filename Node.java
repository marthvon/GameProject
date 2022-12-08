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
        final double rad = Math.toRadians(getRotation());
        Vector2 basisX = (new Vector2(1, 0));
        basisX.setRotation(rad);
        Vector2 basisY = (new Vector2(0, 1));
        basisY.setRotation(rad);
        transform = new Transform(basisX, basisY, new Vector2(-1, -1));
        update_dirty = true;
        getImage().clear();
    }
    public void addedToWorld(World world) {
        if(transform.getOrigin().x == -1)
            transform.setOrigin(new Vector2(getX(), getY()));
    }
    public Node(final Vector2 p_position, final Vector2 p_scale, final double p_rotation) {
        super();
        Vector2 basisX = (new Vector2(p_scale.x, 0));
        basisX.setRotation(p_rotation);
        Vector2 basisY = (new Vector2(0, p_scale.y));
        basisY.setRotation(p_rotation);
        transform = new Transform(basisX, basisY, p_position);
        update_dirty = true;
        getImage().clear();
    }
    public Node(final Vector2 p_position, final Vector2 p_scale) {
        super();
        transform = new Transform(new Vector2(p_scale.x, 0), new Vector2(0, p_scale.y), p_position);
        update_dirty = true;
        getImage().clear();
    }
    public Node(final Vector2 p_position, final double p_rotation) {
        super();
        Vector2 basisX = (new Vector2(1, 0));
        basisX.setRotation(p_rotation);
        Vector2 basisY = (new Vector2(0, 1));
        basisY.setRotation(p_rotation);
        transform = new Transform(basisX, basisY, p_position);
        update_dirty = true;
        getImage().clear();
    }
    public Node(final Vector2 p_position) {
        super();
        final double rad = Math.toRadians(getRotation());
        Vector2 basisX = (new Vector2(1, 0));
        basisX.setRotation(rad);
        Vector2 basisY = (new Vector2(0, 1));
        basisY.setRotation(rad);
        transform = new Transform(basisX, basisY, p_position);
        update_dirty = true;
        getImage().clear();
    }
    
    protected void finalize() {
        removeFromSceneTree();
    }
    
    public Node getParent() {
        return parent;
    }
    public Transform getGlobalTransform() {
        if(parent == null) {
            globalTransformCache = transform;
            return new Transform(transform); 
        } else if(update_dirty) {
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
        transform.setOrigin(pos);
        propagateDrawUpdate();
    }
    public final Vector2 getPosition() {
        return transform.getOrigin();
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
        final Vector2 pos = globalTransformCache.getOrigin(); 
        setLocation((int)pos.x, (int)pos.y);
        setRotation(globalTransformCache.getRotation());
        update_dirty = false;
    }
    
    //virtual need to be overloaded
    public void notifyCollisionTo(Node colliding, final int layer) {}
    public void notifyCollisionFrom(Node collided, final int mask) {}
    
    public void act() {
        updateDraw();
    }
}
