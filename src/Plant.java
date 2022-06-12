public class Plant {
    static int countOfPlants = 0;
    String name;
    final int WEIGHT = 1;
    Field field;

    boolean alive = true;
    Plant(Field field){
        countOfPlants++;
        this.field = field;
        name = "Plant " + countOfPlants + " on field " + field.getCoordinate();
        System.out.println("Создан " + name);
    }
}
