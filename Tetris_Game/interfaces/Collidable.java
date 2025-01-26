package interfaces;

public interface Collidable {
        void checkMovementCollision(); // sprawdzenie kolizji przy opadaniu
        void checkRotationCollision(); // sprawdzenie kolizji przy przekrecaniu
        void checkStaticBlockCollision(); // sprawdzenie kolizji z opadnietymi klockami
}
