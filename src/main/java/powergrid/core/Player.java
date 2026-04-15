package powergrid.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import powergrid.utils.City;
import powergrid.utils.PowerPlant;
import powergrid.utils.ResourceType;

public class Player implements Comparable<Player> {
    private String name;
    private int elektro;
    private ArrayList<PowerPlant> ownedPlants;
    private HashMap<City, Integer> ownedCities;
    private HashMap<ResourceType, Integer> resources;
    private int citiesPowered;
    private PowerPlant highestPowerPlant;
    private boolean passedBid;
    private Color color;
    private boolean finishedPowering;

    public Player() {
        this("Player " + (GameState.players.size() + 1));
    }

    public Player(String name) {
        this.name = name;
        this.color = GameState.playerColors.remove(0);
        this.elektro = 50;
        this.ownedPlants = new ArrayList<>();
        this.ownedCities = new HashMap<>();
        this.resources = new HashMap<>();
        this.citiesPowered = 0;
        this.highestPowerPlant = null;
        this.passedBid = false;
        this.finishedPowering = false;

        resources.put(ResourceType.COAL, 0);
        resources.put(ResourceType.OIL, 0);
        resources.put(ResourceType.GARBAGE, 0);
        resources.put(ResourceType.URANIUM, 0);
        resources.put(ResourceType.HYBRID, 0);
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElektro() {
        return elektro;
    }

    public ArrayList<PowerPlant> getOwnedPlants() {
        return ownedPlants;
    }

    public HashMap<City, Integer> getCitiesBuilt() {
        return ownedCities;
    }

    public HashMap<ResourceType, Integer> getResources() {
        return resources;
    }

    public int getCitiesPowered() {
        return citiesPowered;
    }

    public void setCitiesPowered(int citiesPowered) {
        this.citiesPowered = citiesPowered;
    }

    public PowerPlant getHighestPowerPlant() {
        return highestPowerPlant;
    }

    public boolean finishedPowering()
    {
        return finishedPowering;
    }

    public void setFinished()
    {
        finishedPowering = true;
    }

    public boolean hasPassedBid() {
        return passedBid;
    }

    public void setPassedBid(boolean passedBid) {
        this.passedBid = passedBid;
    }

    public void addPowerPlant(PowerPlant p) {
        if (p == null) {
            return;
        }

        ownedPlants.add(p);

        if (highestPowerPlant == null || p.getPlantNumber() > highestPowerPlant.getPlantNumber()) {
            highestPowerPlant = p;
        }
    }

    public void addCity(City c) {
        if (c == null) {
            return;
        }

        if (!ownedCities.containsKey(c)) {
            ownedCities.put(c, c.getOwners().size() + 1);

            if (!c.getOwners().contains(this)) {
                c.addOwner(this);
            }
        }
    }

    public void buyResource(ResourceType type, int amount) {
        if (type == null || amount <= 0) {
            return;
        }

        int currentAmount = resources.getOrDefault(type, 0);
        resources.put(type, currentAmount + amount);
    }

    public void spendElektro(int amount) {
        if (amount <= 0) {
            return;
        }

        elektro -= amount;
        if (elektro < 0) {
            elektro = 0;
        }
    }

    public void gainElektro(int amount) {
        if (amount <= 0) {
            return;
        }

        elektro += amount;
    }

    public boolean canPowerCities() {
        return calculatePoweredCities() > 0;
    }

    public int calculatePoweredCities() {
        int total = 0;

        for (PowerPlant plant : ownedPlants) {
            if (plant.canPower()) {
                total += plant.getCitiesPowered();
            }
        }

        citiesPowered = Math.min(total, ownedCities.size());
        return citiesPowered;
    }

    public int calculateIncome(int citiesPowered) {
        int[] incomeTable = {
            10, 22, 33, 44, 54, 64, 73, 82, 90, 98,
            105, 112, 118, 124, 129, 134, 138, 142, 145, 148, 150
        };

        if (citiesPowered <= 0) {
            return 10;
        }

        if (citiesPowered >= incomeTable.length) {
            return 150;
        }

        return incomeTable[citiesPowered];
    }

    @Override
    public int compareTo(Player other) {
        if (other == null) {
            return -1;
        }

        if (this.ownedCities.size() > other.ownedCities.size()) {
            return -1;
        }
        if (this.ownedCities.size() < other.ownedCities.size()) {
            return 1;
        }

        int thisHighest = (highestPowerPlant == null) ? -1 : highestPowerPlant.getPlantNumber();
        int otherHighest = (other.highestPowerPlant == null) ? -1 : other.highestPowerPlant.getPlantNumber();

        if (thisHighest > otherHighest) {
            return -1;
        }
        if (thisHighest < otherHighest) {
            return 1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return name + " | Elektro: " + elektro + " | Cities: " + ownedCities.size();
    }
}
