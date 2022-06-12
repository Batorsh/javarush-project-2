import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Field field = new Field(1, 1);

        for (int i = 0; i < 200; i++) {
            field.addPlant(new Plant(field));
        }

        for (int i = 0; i < 50; i++) {
            Herbivore herbivore = new Herbivore(field);
            field.addHerbivore(herbivore);
            executorService.submit(herbivore);
        }
        for (int i = 0; i < 3; i++) {
            Predator predator = new Predator(field);
            field.addPredator(predator);
            executorService.submit(predator);
            //Thread thread = new Thread(predator);
            //thread.start();
        }

        executorService.submit(() -> {
           while(true){

               if (field.newAnimals.size() > 0) {
                   Animal animal = field.newAnimals.peek();
                   executorService.submit(animal);
                   Thread.sleep(500);
               }
           }
        });


        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Program finished");
    }


}

