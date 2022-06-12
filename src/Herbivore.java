import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Herbivore implements Runnable {
    String EMOJI = " \uD83D\uDC11";
    static int herbivoresCount = 0;
    String name;
    final int WEIGHT = 2;
    int chance = 100 - 80;

    Field field;

    int food = 1;
    int MAX_FOOD = 1;
    boolean alive = true;

    public Herbivore(Field field) {
        herbivoresCount++;
        name = EMOJI + " " + herbivoresCount;
        this.field = field;
    }

    public void eat(List<Plant> plants) {
        //System.out.println("Quantity of \uD83D\uDC11 on " + field.getCoordinate() + " = " + field.getHerbivoresCount());
        synchronized (field) {
            for (Plant plant : plants) {
                if (food < MAX_FOOD) {
                    int randomInt = ThreadLocalRandom.current().nextInt(100);
                    //System.out.println("Random(100) " + randomInt);
                    if (randomInt > 10) {
                        System.out.println(this.getName() + " ate " + plant.name + " food = " + food);
                        food = food + plant.WEIGHT;
                        food = Math.min(food, MAX_FOOD);
                        plant.alive = false;
                        plants.remove(plant);
                    }
                }
            }
            return;
        }

    }


    @Override
    public void run() {
        while (food > 0) {
            food = food - 1;
            synchronized (field) {
                eat(field.getPlants());
                System.out.println("Quantity of " + EMOJI + " on " + field.getCoordinate() + " = " + field.herbivores.size());
                if (field.predators.size() > 1) {
                    //reproduction();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        synchronized (field) {
            this.alive = false;
            field.herbivores.remove(this);
            System.out.println(this.getName() + " died");
        }
    }

    public String getName() {
        return name;
    }
}
