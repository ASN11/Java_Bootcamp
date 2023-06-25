package edu.school21.classes;

public class Car {
    private String model;
    private int speed;

    public Car() {}

    public Car(String model, int speed) {
        this.model = model;
        this.speed = speed;
    }
    public int changeSpeed(int changeValue) {
        speed += changeValue;
        return speed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", speed=" + speed +
                '}';
    }
}
