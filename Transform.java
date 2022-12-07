

public class Transform  
{
    private Vector2 basisX;
    private Vector2 basisY;
    private Vector2 position;
    //CONTRUCTORS
    public Transform() {
        basisX = new Vector2();
        basisY = new Vector2();
        position = new Vector2();
    }
    public Transform(final Vector2 axisX, final Vector2 axisY, final Vector2 p_pos) {
        basisX = axisX;
        basisY = axisY;
        position = p_pos;
    }
    public Transform(final Transform other) {
        basisX = other.basisX;
        basisY = other.basisY;
        position = other.position;
    }
    //END OF CONSTRUCTORS
    //BOILER PLATE SETTER GETTER
    public void setBasisX(final Vector2 axisX) {
        basisX = axisX;
    }
    public final Vector2 getBasisX() {
        return basisX;
    }
    public void setBasisY(final Vector2 axisY) {
        basisY = axisY;
    }
    public final Vector2 getBasisY() {
        return basisY;
    }
    public void setLocalPosition(final Vector2 origin) {
        position = origin;
    }
    public final Vector2 getLocalPosition() {
        return position;
    }
    //END OF SETTER AND GETTER
    //FUNCTIONS
    public double dotX(final Vector2 other) {
        return basisX.x * other.x + basisY.x * other.y;
    }
    public double dotY(final Vector2 other) {
        return basisX.y * other.x + basisY.y * other.y;
    }
    public double basisDeterminant() {
        return basisX.x * basisY.y - basisX.y * basisY.x;
    }
    public Vector2 basisTransform(final Vector2 other) {
        return new Vector2(dotX(other), dotY(other));
    }
    public Vector2 basisInverseTransform(final Vector2 other) {
        return new Vector2(basisX.getDotProduct(other), basisY.getDotProduct(other));
    }
    public Vector2 transform(final Vector2 other) {
        return basisTransform(other).added(position);
    }
    public Vector2 inverseTransform(final Vector2 other) {
        final Vector2 diff = other.subtract(position);
        return basisInverseTransform(diff);
    }
    public Transform invert() {
        final double temp = basisX.y;
        basisX.y = basisY.x;
        basisY.x = temp;
        position = basisTransform(position.multiplied(-1));
        return this;
    }
    public Transform inverse() {
        return (new Transform(this)).invert();
    }
    public Transform affine_invert() {
        final double determinant = basisDeterminant();
        if(determinant == 0)
            return this;
        final double iDeterminant = 1.0 / determinant;
        final double temp = basisX.y;
        basisX.y = basisY.x;
        basisY.x = temp;
        basisX.multiplied(new Vector2(iDeterminant, -iDeterminant));
        basisY.multiplied(new Vector2(-iDeterminant, iDeterminant));
        position = basisTransform(position.multiplied(-1));
        return this;
    }
    public Transform affine_inverse() {
        return (new Transform(this)).affine_invert();
    }
    public Transform orthonormalized() {
        basisX.normalized();
        basisY.subtracted(basisX.multiply(basisX.getDotProduct(basisY))).normalized();
        return this;
    }
    public Transform orthonormalize() {
        return (new Transform(this)).orthonormalized();
    }
    //END OF FUNCTIONCS
    //TRANSLATE, SCALE, ROTATION
    public Transform translated(final Vector2 translation) {
        position.added(basisTransform(translation));
        return this;
    }
    public Transform translate(final Vector2 translation) {
        return (new Transform(this)).translated(translation);
    }
    public void setScale(final Vector2 scale) {
        basisX.normalized().multiplied(scale.x);
        basisY.normalized().multiplied(scale.y);
    }
    public Transform scaled(final Vector2 scale) {
        basisX.multiplied(scale);
        basisY.multiplied(scale);
        position.multiplied(scale);
        return this;
    }
    public Transform scale(final Vector2 scale) {
        return (new Transform(this)).scaled(scale);
    }
    public Vector2 getScale() {
        return new Vector2(
            basisX.getLength(), 
            basisY.getLength() * (basisDeterminant() < 0? -1: 1)
        );
    }
    public double getRotation() {
        return basisX.getAngle();
    }
    public void setRotation(final double radian) {
        final Vector2 scale = getScale();
        final double cos = Math.cos(radian);
        final double sin = Math.sin(radian);
        basisX.set(cos, sin);
        basisY.set(-sin, cos);
        basisX.multiplied(scale.x);
        basisY.multiplied(scale.y);
    }
    //END
    //OPERATORS
    public Transform multiplied(final Transform other) {
        position = transform(other.getLocalPosition());
        
        final double xx = dotX(other.getBasisX());
        final double xy = dotY(other.getBasisX());
        final double yx = dotX(other.getBasisY());
        final double yy = dotY(other.getBasisY());
        
        basisX.set(xx, xy);
        basisY.set(yx, yy);
        return this;
    }
    public Transform multiply(final Transform other) {
        return (new Transform(this)).multiplied(other);
    }
    //END OF OPERATORS
}
