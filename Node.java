import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Node extends Actor
{
    private Node parent = null;
    private ArrayList<Node> childNodes = new ArrayList<Node>();
    private Transform transform;
    private boolean update_dirty = false;
    
    public Node() {
        super();
    }
    protected void finalize() {
        removeFromSceneTree();
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
    
    public void act() {
    
    }
}
