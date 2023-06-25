package model;

import annotations.OrmColumn;
import annotations.OrmColumnId;
import annotations.OrmEntity;

@OrmEntity(table = "cars")
public class Car {
    @OrmColumnId
    private int id;
    @OrmColumn(name = "brand", length = 15)
    private String brand;
    @OrmColumn(name = "model", length = 15)
    private String model;
    @OrmColumn(name = "speed")
    private int speed;

    public Car() {}
    public Car(String brand, String model, int speed) {
        this.brand = brand;
        this.model = model;
        this.speed = speed;
    }

    public Car(int id, String brand, String model, int speed) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", speed=" + speed +
                '}';
    }
}
