
import java.util.Scanner;

class Main {
    static Scanner scanner = new Scanner(System.in);
    static int sup_water = 400;
    static int sup_milk = 540;
    static int sup_beans = 120;
    static int sup_cups = 9;
    static int money = 550;
    enum State {MENU, TAKE, CHECK,  BUY, FILL, EXIT};

    static State currentState = State.MENU;

    private static void printState() {
        System.out.println("The coffee machine has: ");
        System.out.println(sup_water + " ml of water");
        System.out.println(sup_milk + " ml of milk");
        System.out.println(sup_beans + " g of coffee beans");
        System.out.println(sup_cups + " disposable cups");
        System.out.println("$" + money + " of money");
        System.out.println("");
    }
    private static void take() {
        System.out.println("I gave you $" + money);
        money = 0;
        System.out.println("");
    }

    private static String checkSupplies(int water, int milk, int beans, int cups) {
        String missing = (sup_water - water < 0 ) ? "water" : "";
        missing += (sup_milk - milk < 0 ) ? "milk" : "";
        missing += (sup_beans - beans < 0 ) ? "beans" : "";
        missing += (sup_cups - cups < 0 ) ? "cups" : "";
        //missing += "!";
        return missing;
    }

    private static void editSupplies(int water, int milk, int beans, int cups, int price) {
        sup_water = sup_water - water;
        sup_milk = sup_milk - milk;
        sup_beans = sup_beans - beans;
        sup_cups = sup_cups - cups;
        money = money + price;
    }

    private static void makeCoffee(int choice) {
        String msg = "";
        switch (choice) {
            case 1:

                msg = checkSupplies(250,0,16,1);
                if (msg.isEmpty()) {
                    editSupplies(250,0,16,1,4);
                }
                break;
            case 2:
                msg = checkSupplies(350,75,20,1);
                if (msg.isEmpty()) {
                    editSupplies(350,75,20,1,7);
                }
                break;
            case 3:
                msg = checkSupplies(200,100,12,1);
                if (msg.isEmpty()) {
                    editSupplies(200,100,12,1,6);
                }
                break;
        }
        if (msg.isEmpty()) {
            System.out.println("I have enough resources, making you a coffee!");
            System.out.println("");
        } else {
            System.out.println("Sorry, not enough " + msg + "!");
            System.out.println("");
        }
    }

    private static void fill() {
        System.out.println("Write how many ml of water you want to add: ");
        sup_water += checkInput();
        System.out.println("Write how many ml of milk you want to add: ");
        sup_milk += checkInput();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        sup_beans += checkInput();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        sup_cups += checkInput();
        System.out.println("");
    }

    private static void buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        int selection = checkInput();
        if (currentState == State.BUY) {
            makeCoffee(selection);
        }
    }

    private static int checkInput() {
        String userInput;
        if (scanner.hasNext()) {
            userInput = scanner.nextLine();
            switch (currentState) {
                case  MENU:
                    switch (userInput) {
                        case "take":
                            currentState = State.TAKE;
                            return 999;
                        case "fill":
                            currentState = State.FILL;
                            return 999;
                        case "buy":
                            currentState = State.BUY;
                            return 999;
                        case "remaining":
                            currentState = State.CHECK;
                            return 999;
                        case "exit":
                            currentState = State.EXIT;
                            return 999;
                    }
                case  FILL:
                    return Integer.parseInt(userInput);
                case  BUY:
                    switch (userInput) {
                        case "back":
                            currentState = State.MENU;
                            return 999;
                        case "1":
                            return 1;
                        case "2":
                            return 2;
                        case "3":
                            return 3;
                    }
            }
        }
        return 999;
    }

    public static void main(String[] args) {
        boolean shouldExit = false;
        while (!shouldExit) {
            switch (currentState) {
                case MENU:
                    System.out.println("Write action (buy, fill, take, remaining, exit): ");
                    checkInput();
                    break;
                case TAKE:
                    take();
                    currentState = State.MENU;
                    break;
                case CHECK:
                    printState();
                    currentState = State.MENU;
                    break;
                case FILL:
                    fill();
                    currentState = State.MENU;
                    break;
                case BUY:
                    buy();
                    currentState = State.MENU;
                    break;
                case EXIT:
                    shouldExit = true;
                    break;
            }
        }
    }
}