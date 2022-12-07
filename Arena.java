import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Arena extends World
{
    private double lastCall = 0;
    private double deltaTime = 0;
    
    public Arena()
    {    
        super(600, 400, 1); 
        lastCall = (double)(System.currentTimeMillis());
    }
    
    public void act() {
        final double now = (double)(System.currentTimeMillis());
        deltaTime = now - lastCall;
        showText("delta time:=" + deltaTime, 75, 10);
        lastCall = now;
    }
    
    public final double getTimeStep() {
        return deltaTime;
    }
}
