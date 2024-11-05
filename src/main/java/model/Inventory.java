/**
 * The Inventory class manages the player's inventory in the game.
 * It keeps track of the items, blood shards, and keys collected by the player.
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int capacity;
    private int bloodShardsAmount;
    private int keysCount;
    private List<GameObject> items;

    /**
     * Constructs an Inventory with a specified capacity.
     *
     * @param capacity The maximum number of items the inventory can hold.
     */
    public Inventory(int capacity) {
        this.capacity = capacity;
        this.keysCount = 0;
        this.bloodShardsAmount = 0;
        this.items = new ArrayList<>();
    }

    // Getter and setter methods for the inventory properties

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBloodShardsAmount() {
        return bloodShardsAmount;
    }

    public void setBloodShardsAmount(int bloodShardsAmount) {
        this.bloodShardsAmount = bloodShardsAmount;
    }

    public List<GameObject> getItems() {
        return items;
    }

    public void setItems(List<GameObject> items) {
        this.items = items;
    }

    /**
     * Adds an item to the inventory if there is space.
     *
     * @param item The game object to be added to the inventory.
     */
    public void addItem(GameObject item) {
        if (items.size() < capacity) {
            items.add(item);
        }
    }

    /**
     * Retrieves an item from the inventory.
     *
     * @param item The game object to be retrieved from the inventory.
     * @return The game object if found, otherwise null.
     */
    public GameObject getItem(GameObject item) {
        for (GameObject i : items) {
            if (i.equals(item)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Counts the number of keys in the inventory.
     *
     * @return The number of keys in the inventory.
     */
    public int countKeys() {
        keysCount = 0;
        for (GameObject i : items) {
            if (i.getType().equals(ObjectType.KEY)) {
                keysCount++;
            }
        }
        return keysCount;
    }
}
