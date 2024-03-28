package cz.hyperion;

final class Vector {
    double x;
    double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

   double getLength() {
        return Math.sqrt(x * x + y * y);
    }
}
