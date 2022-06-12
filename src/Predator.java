import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Predator extends Animal implements Runnable, Reproductionable{
    String EMOJI = "\uD83D\uDC03";
    static int countOfPredators = 0;
    String name;
    final int WEIGHT = 50;
    final int MAX_FOOD = 8;
    Field field;
    final int FOOD_PER_DAY = 2;
    int food = 8;

    boolean alive = true;
    int spareFood = 0;

    Predator(Field field) {
        countOfPredators++;
        name = EMOJI + " " + countOfPredators;

        this.field = field;
        System.out.println(name + " was born");
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void eat(List<Herbivore> herbivores) {
        //System.out.println("Quantity of \uD83D\uDC11 on " + field.getCoordinate() + " = " + field.getHerbivoresCount());
        for (Herbivore herbivore : herbivores) {

            if (food < MAX_FOOD) {
                int randomInt = ThreadLocalRandom.current().nextInt(100);
                //System.out.println("Random(100) " + randomInt);
                if (randomInt > herbivore.chance) {
                    System.out.println(this.getName() + " ate " + herbivore.name + " food = " + food);
                    food = food + herbivore.WEIGHT;
                    food = Math.min(food, MAX_FOOD);
                    herbivore.alive = false;
                    herbivores.remove(herbivore);
                }
            }
            return;
        }

    }

    void reproduction() {
        if (ThreadLocalRandom.current().nextInt(100) > 80) {
            field.newAnimals.add(new Predator(field));
            System.out.println("Добавлен новый хищник в очередь на создание");
            /*Predator predator = new Predator(field);
            field.addPredator(predator);
            new Thread(predator).start();*/
        }
    }

    public void run() {
        while (food > 0) {
            food = food - FOOD_PER_DAY;
            synchronized (field) {
                eat(field.getHerbivores());
                System.out.println("Quantity of " + EMOJI + " on " + field.getCoordinate() + " = " + field.predators.size());
                if (field.predators.size() > 1) {
                    reproduction();
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
            field.predators.remove(this);
            System.out.println(this.getName() + " died");
        }
        return;
    }

    @Override
    public Runnable getNew(Field field) {
        return new Predator(field);
    }
}


