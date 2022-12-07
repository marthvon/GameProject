
public class Vector2  
{
    public double x;
    public double y;
    
    public Vector2(){
        x = 0;
        y = 0;
    }
    public Vector2(final double p_x, final double p_y) {
        x = p_x;
        y = p_y;
    }
    public Vector2(final double fill) {
        x = fill;
        y = fill;
    }
    public Vector2(final Vector2 other) {
        x = other.x;
        y = other.y;
    }
    
    //SETTERS
    public void set(final double p_x, final double p_y) {
        x = p_x;
        y = p_y;
    }
    public void setRotation(final double radian) {
        final double length = getLength();
        x = Math.cos(radian) * length;
        y = Math.sin(radian) * length;
    }
    //END OF SETTERS
    //FUNCTIONS
    public double getMagnitude() {
        return x * x + y * y;
    }
    
    public double getLength() {
        return Math.sqrt(getMagnitude());
    }
    
    public double getDistanceTo(final Vector2 other) {
        final double temp_x = x - other.x;
        final double temp_y = y - other.y;
        return Math.sqrt(temp_x * temp_x + temp_y * temp_y);
    }
    
    public Vector2 moveToward(final Vector2 destination, final double delta) {
        Vector2 vd = destination.subtract(this);
        final double length = vd.getLength();
        return length <= delta || length < 0.0001? destination : vd.divided(length).multiplied(delta).added(this); 
    }
    
    public double getAngle() {
        return Math.atan2(y, x);
    }
    /*
     * return in radians the angle between(difference) two vector given the 
     * they have the same orgin
       */
    public double getAngleTo(final Vector2 other) {
        return Math.atan2(getCrossProduct(other), getDotProduct(other));
    }
    /*
     * returns in radians the angle given the other vector is the origin 
     * and this vector is rotating clockwise from the +x axis
       */
    public double getAngleToPoint(final Vector2 other) {
        return Math.atan2(y - other.y, x - other.x);
    }
    
    public double getDotProduct(final Vector2 other) {
        return x * other.x + y * other.y;
    }
    public double getCrossProduct(final Vector2 other) {
        return x * other.y - other.x * y;
    }
    
    public Vector2 reflect(final Vector2 other) {
        Vector2 oNormalized = other.normalize();
        return oNormalized.multiplied(2.0 * getDotProduct(oNormalized)).subtracted(this);
    }
    public Vector2 bounce(final Vector2 other) {
        return reflect(other).multiplied(-1);
    }
    public Vector2 project(final Vector2 other) {
        return other.multiply(getDotProduct(other) / other.getMagnitude());
    }
    public Vector2 slide(final Vector2 other) {
        return other.multiply(getDotProduct(other) * -1).added(this);
    }
    
    public Vector2 normalized() {
        double mag = getMagnitude();
        if(mag != 0) {
            mag = Math.sqrt(mag);
            x /= mag;
            y /= mag;
        }
        return this;
    }    
    public Vector2 normalize() {
        Vector2 ret = new Vector2(this);
        ret.normalize();
        return ret;
    }
    
    public Vector2 rotated(final double value) {
        setRotation(getAngle() + value);
        return this;
    }
    public Vector2 rotate(final double value) {
        return (new Vector2(this)).rotated(value);
    }
    //END OF FUNCTIONS
    //OPERATORS
    public Vector2 added(final Vector2 other) {
        x += other.x;
        y += other.y;
        return this;
    }
    public Vector2 added(final double value) {
        x += value;
        y += value;
        return this;
    }
    public Vector2 add(final Vector2 other) {
        return (new Vector2(this)).added(other);
    }
    public Vector2 add(final double value) {
        return (new Vector2(this)).added(value);
    }
    
    public Vector2 subtracted(final Vector2 other) {
        x -= other.x;
        y -= other.y;
        return this;
    }
    public Vector2 subtracted(final double value) {
        x -= value;
        y -= value;
        return this;
    }
    public Vector2 subtract(final Vector2 other) {
        return (new Vector2(this)).subtracted(other);
    }
    public Vector2 subtract(final double value) {
        return (new Vector2(this)).subtracted(value);
    }
    
    public Vector2 multiplied(final Vector2 other) {
        x *= other.x;
        y *= other.y;
        return this;
    }
    public Vector2 multiplied(final double value) {
        x *= value;
        y *= value;
        return this;
    }
    public Vector2 multiply(final Vector2 other) {
        return (new Vector2(this)).multiplied(other);
    }
    public Vector2 multiply(final double value) {
        return (new Vector2(this)).multiplied(value);
    }
    
    public Vector2 divided(final Vector2 other) {
        x /= other.x;
        y /= other.y;
        return this;
    }
    public Vector2 divided(final double value) {
        x /= value;
        y /= value;
        return this;
    }
    public Vector2 divide(final Vector2 other) {
        return (new Vector2(this)).divided(other);
    }
    public Vector2 divide(final double value) {
        return (new Vector2(this)).divided(value);
    }
    //END OF OPERATORS
}
