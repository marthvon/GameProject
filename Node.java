import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Node extends Actor
{
    private Node parent = null;
    private ArrayList<Node> childNodes = new ArrayList<Node>();
    private Transform transform;
    private Transform globalTransformCache; 
    private boolean update_dirty = false;
    private boolean redraw_texture = false;
    private GreenfootImage texture = null;
    
    public Node() {
        super();
        
        final double rad = Math.toRadians(getRotation());
        Vector2 basisX = (new Vector2(1, 0));
        basisX.setRotation(rad);
        Vector2 basisY = (new Vector2(0, 1));
        basisY.setRotation(rad);
        transform = new Transform(basisX, basisY, new Vector2(-1, -1));
        
        if(getImage() != null) 
            texture = new GreenfootImage(getImage());
    }
    public Node(final Vector2 p_position, final Vector2 p_scale, final double p_rotation) {
        super();
        
        Vector2 basisX = (new Vector2(p_scale.x, 0));
        basisX.setRotation(p_rotation);
        Vector2 basisY = (new Vector2(0, p_scale.y));
        basisY.setRotation(p_rotation);
        transform = new Transform(basisX, basisY, p_position);
        
        if(getImage() != null) {
            texture = new GreenfootImage(getImage());
            getImage().clear();
        }
        
        update_dirty = true;
        redraw_texture = true;
    }
    public Node(final Vector2 p_position, final Vector2 p_scale) {
        super();
        transform = new Transform(new Vector2(p_scale.x, 0), new Vector2(0, p_scale.y), p_position);
        
        if(getImage() != null) {
            texture = new GreenfootImage(getImage());
            getImage().clear();
        }
        
        update_dirty = true;
        redraw_texture = true;
    }
    public Node(final Vector2 p_position, final double p_rotation) {
        super();
        
        Vector2 basisX = (new Vector2(1, 0));
        basisX.setRotation(p_rotation);
        Vector2 basisY = (new Vector2(0, 1));
        basisY.setRotation(p_rotation);
        transform = new Transform(basisX, basisY, p_position);
        
        if(getImage() != null) {
            texture = new GreenfootImage(getImage());
            getImage().clear();
        }
        
        update_dirty = true;
        redraw_texture = true;
    }
    public Node(final Vector2 p_position) {
        super();
        
        final double rad = Math.toRadians(getRotation());
        Vector2 basisX = (new Vector2(1, 0));
        basisX.setRotation(rad);
        Vector2 basisY = (new Vector2(0, 1));
        basisY.setRotation(rad);
        transform = new Transform(basisX, basisY, p_position);
        
        if(getImage() != null) {
            texture = new GreenfootImage(getImage());
            getImage().clear();
        }
        
        update_dirty = true;
        redraw_texture = true;
    }
    public void addedToWorld(World world) {
        if(transform.getOrigin().x == -1) {
            transform.setOrigin(new Vector2(getX(), getY()));
            if(parent == null)
                globalTransformCache = new Transform(transform);
            else
                transform = parent.getGlobalTransform().affine_invert().multiplied(transform);
            return;
        } 
        getGlobalTransform();
        draw();
    }
    
    public void draw() {
        setLocation((int)globalTransformCache.getOrigin().x, (int)globalTransformCache.getOrigin().y);
        if(texture == null)
            return;
        GreenfootImage newImage = new GreenfootImage(texture);
        final Vector2 scale = globalTransformCache.getScale();
        final double radian = globalTransformCache.getRotation();
        newImage.scale( (int)(scale.x * newImage.getWidth()), Math.abs((int)(scale.y * newImage.getHeight())) );
        if(scale.y < 0)
            newImage.mirrorHorizontally();
        newImage.rotate((int)Math.toDegrees(globalTransformCache.getRotation()));
        setImage(newImage);
        redraw_texture = false;
    }
    
    protected void finalize() {
        removeFromSceneTree();
    }
    
    public final Node getParent() {
        return parent;
    }
    
    public final Transform getLocalTransform() {
        return transform;
    }
    public Transform getGlobalTransform() {
        if(parent == null) {
            globalTransformCache = new Transform(transform);
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
    
    public void setTexture(GreenfootImage p_texture) {
        texture = p_texture;
        redraw_texture = true;
    }
    public GreenfootImage getTexture() {
        return new GreenfootImage(texture);
    }
    
    public void removeFromSceneTree() {
        for(Node child: childNodes)
            child.removeFromSceneTree();
        if(getWorld() != null)
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
        if(index >= childNodes.size())
            return false;
        Node child = childNodes.get(index);
        if(child.getWorld() != null || getWorld() == null)
            return false;
        getWorld().addObject(child, 0, 0);
        return true;
    }
    
    //virtual need to be overloaded
    public void notifyCollisionTo(Node colliding, final int layer) {}
    public void notifyCollisionFrom(Node collided, final int mask) {}
    
    public void act() {
        if(update_dirty) {
            getGlobalTransform();
            draw();
        } else if (redraw_texture)
            draw();
    }
}
