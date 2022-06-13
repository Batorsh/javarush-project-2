import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class Field {
    int coordinateX;
    int coordinateY;
    int vertical = 1;
    int diagonal = 1;
    int predatorsQty = 50;
    List<Predator> predators = new ArrayList<>();
    List<Herbivore> herbivores = new ArrayList<>();

    Queue<Animal> newAnimals = new LinkedList<>();

    List<Plant> plants = new ArrayList<>();

    //List<Reproductionable> reproductionables = new SynchronousQueue<Reproductionable>();

    public Field(int x, int y) {
        coordinateX = x;
        coordinateY = y;

    }

    public String getCoordinate() {
        return "Field " + "[" + coordinateX + "][" + coordinateY + "]";
    }

    synchronized public List<Predator> getPredators() {
        return predators;
    }

    synchronized public void addPredator(Predator predator) {
        predators.add(predator);
    }

    synchronized public List<Herbivore> getHerbivores() {
        return herbivores;
    }

    synchronized public void addHerbivore(Herbivore herbivore) {
        herbivores.add(herbivore);
    }

    synchronized public int getHerbivoresCount() {
        return herbivores.size();
    }

    synchronized public void removeHerbivore(Herbivore herbivore) {
        herbivores.remove(herbivore);
    }

    synchronized public void addPlant(Plant plant) {
        plants.add(plant);
    }
    synchronized public List<Plant> getPlants() {
        return plants;
    }
}
