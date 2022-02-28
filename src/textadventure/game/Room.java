package textadventure.game;
import java.util.HashMap;
import java.util.Set;
public class Room{
	private String shortDescription;
    private String longDescription;
    private HashMap<String,Room>exits;
    private HashMap<String,Item>inventory;
    private String name;
    public Room(String name, String shortDescription, String longDescription){
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        exits = new HashMap<>();
        inventory = new HashMap<>();
    }
    public String getName(){
        return name;
    }
    public void setItem(String name, Item item){
        inventory.put(name, item);
    }
    public Item removeItem(String key){
        return inventory.remove(key);
    }
    public Item getItem(String key){
        return inventory.get(key);
    }
    public String getShortDescription(){
        return shortDescription;
    }
    public String getLongDescription(){
        return longDescription;
    }
    public void setExit(String direction, Room neighbor){
        exits.put(direction,neighbor);
    }
    public Room getExit(String direction){
        return exits.get(direction);
    }
    public boolean hasExit(){
        return (exits != null);
	}
	public String getInventoryString(){
        String returnString = "Room Inventory: ";
        Set<String> keys = inventory.keySet();
        for(String item: keys){
            returnString += " \" " + item + " \" ";
        }
        return returnString;
    }
    public String getExitString(){
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit: keys){
            returnString += "\" " + exit + "\" ";
        }
        return returnString;
    }
}