package textadventure.game;

public class Game{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private CLS cls_var;
    public Game (){
        parser = new Parser();
        player = new Player();
    }

    public static void main(String[] args){
        Game game = new Game();
        game.setupGame();
        game.play();
    }

    public void printInformation(){
        System.out.println(currentRoom.getExitString());
        System.out.println(currentRoom.getShortDescription());
        System.out.println(currentRoom.getInventoryString());
        System.out.println(player.getInventoryString());
    }

    public void setupGame(){
        Room LivingRoom = new Room("living room","You are in the living room.","You have found your way to the place where you entered the house. There is a door on your left and stairs on your right. You are standing in front of a table, where a half-eaten meal and a grocery bag is placed on. There is a bookshelf behind you, but there is only one book on the shelf.");
        Room Kitchen = new Room("kitchen","You are in the kitchen.","You walked into the kitchen. The counter is to the right of you next to the stove and refrigerator. You open the cabinets. They are empty. There is a hallway in front of you and a door behind you.");
        Room UpStairs = new Room("up stairs","You went up the stairs.", "There are two rooms the stairs lead you to. Do you want to go right (Room1) or left (Room2)?");
        Room DiningRoom = new Room("dining room","You are in the dining room.","You made your way to the dining room. The chandelier is shattered on the table. The chairs are on the floor broken. There is a butterknife on the shelf next to a phone. On the right is a door.");
        Room Basement = new Room("basement","short", "long");
        Room RightRoom = new Room("Room1","You are in a room.","You are in a room and it's dark. You feel around the room for a light switch but bump into a table. You touch the top of the table and feel a hard object. What may it be?");
        Room LeftRoom = new Room("Room2","You are in a room.", "You are in a room and it's dark. You feel around the room for a light switch, but it's not there. Instead you feel a doorknob.");
        Room Exit = new Room("exit","short", "long");
        Room Pit = new Room("in closet","short", "long");

        Item meal = new Item("meal", "The meal on the table looks half-eaten.");
        Item grocery = new Item ("bag", "long");
        Item book = new Item ("book", "des");
        Item key = new Item ("key", "descr");
        Item butterknife = new Item ("butterknife","decribe");
        Item phone = new Item ("phone", "describe");
        Item bluekey = new Item ("key", "info");
        Item man = new Item ("man on the floor", "info");
        
        currentRoom = LivingRoom;

        LivingRoom.setExit("kitchen", Kitchen);
        LivingRoom.setExit("up stairs", UpStairs);
        Kitchen.setExit("living room", LivingRoom);
        Kitchen.setExit("dining room", DiningRoom);
        DiningRoom.setExit("kitchen", Kitchen);
        DiningRoom.setExit("basement", Basement);
        Basement.setExit("exit", Exit);
        Basement.setExit("dining room", DiningRoom);
        UpStairs.setExit("left", LeftRoom);
        UpStairs.setExit("right", RightRoom);
        LeftRoom.setExit("in closet", Pit);
        LeftRoom.setExit("down stairs", LivingRoom);
        RightRoom.setExit("down stairs", LivingRoom);

        LivingRoom.setItem("meal", meal);
        LivingRoom.setItem("bag",grocery);
        LivingRoom.setItem("book",book);
        RightRoom.setItem("key", key);
        DiningRoom.setItem("butterknife",butterknife);
        DiningRoom.setItem("phone",phone);
        Basement.setItem("key", bluekey);
        Basement.setItem("man on the floor", man);
        
        try {
            cls_var.main(); 
        }catch(Exception e) {
            System.out.println(e); 
        }

        printInformation();
        play();
    }
    public void play() {
        while(true) {            
            Command command = parser.getCommand(); 
            try {
                cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
            processCommand(command);
            printInformation();   
        }
    }
    public void processCommand(Command command){
        String commandWord = command.getCommandWord().toLowerCase();
        switch(commandWord){
            case "speak":
                System.out.println("You wanted me to speak this word, " + command.getSecondWord());
                break;
            case "go":
                goRoom(command);
                break;
            case "grab":
                grab(command);
                break;
            case "drop":
                drop(command);
                break;
            case "eat":
                eat(command);
                break;
            case "inspect":
                inspect(command);
                break;
            case "open":
                open(command);
                break;
            case "read":
                read(command);
                break;
        }
    }
    public void read(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Read what?");
        }
        String item = command.getSecondWord();
        if(item.equals("book")){
            System.out.println("The book");
        }
        else{
            System.out.println("You can't read that!");
        }
    }
    public void open(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Open what?");
        }
        String place = command.getSecondWord();
    }
    public void inspect(Command command){
        String printString = "Inspecting ";
        String thingToLook = null;
        if(!command.hasSecondWord()){
            System.out.println("Inspect what?");
            return;
        }
        if(!command.hasLine()){
            thingToLook = command.getSecondWord();
        }
        else if(command.hasLine()){
            thingToLook = command.getSecondWord()+command.getLine();
        }
        if(thingToLook.equals(currentRoom.getName())){
            printString += currentRoom.getName()+"\n"+currentRoom.getLongDescription();
        }
        else if(currentRoom.getItem(thingToLook) != null) {
            printString += currentRoom.getItem(thingToLook).getName()+"\n"+currentRoom.getItem(thingToLook).getDescription();
        }
        else if(currentRoom.getItem(thingToLook) != null) {
            printString += player.getItem(thingToLook).getName()+"\n"+player.getItem(thingToLook).getDescription();
        }
        else{
            printString += "\nYou can't look at that";
        }
        System.out.println(printString);
    }
    public void eat(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Eat what?");
        }
        String item = command.getSecondWord();
        if(item.equals("meal")){
            currentRoom.removeItem("meal");
            player.removeItem("meal");
            System.out.println("You ate a sandwich.");
        }
        else{
            System.out.println("Don't eat that!");
        }
    }
    public void grab(Command command){
        if(!command.hasSecondWord()){
            System.out.println("grab what?");
        }
        String item = command.getSecondWord();
        Item itemToGrab = currentRoom.removeItem(item);
        if(itemToGrab == null){
            System.out.println("you can't grab that!"); 
        }
        else{
            player.setItem(item, itemToGrab);
        }
    }
    public void drop(Command command){
        if(!command.hasSecondWord()){
            System.out.println("drop what?");
            return;
        }
        String item = command.getSecondWord();
        Item itemToDrop = player.removeItem(item);
        if(itemToDrop == null){
            System.out.println("you can't drop that!"); 
            return;
        }
        else{
            currentRoom.setItem(item, itemToDrop);
        }
    }
    public void goRoom(Command command){
        String direction = " ";
        if(!command.hasSecondWord()){
            System.out.println("go where?");
        }
        if(!command.hasLine()){
            direction = command.getSecondWord();
        }
        else if (command.hasLine()){
            direction = command.getSecondWord()+command.getLine();
        }
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom == null){
            System.out.println("you can't go there"); 
        }
        else{
            currentRoom = nextRoom;
        }
        currentRoom = nextRoom;
        if (command.getSecondWord().equals("in closet"))
        	System.out.println("You lose");
        	nextRoom = currentRoom;
       }
}
