package scape;

import scape.Message.Content;

public class Producer extends Agent {

    private int epoch;
    private int stock;
    private int sellPrice;
    // Sale Variables
    private int production = 2;
    private int saleQuantity = 20;
    private int upperSL = 75;
    private int lowerSL = 25;

    // The Producer Constructor
    public Producer(Scape controller, String food) {
        super(controller, "producer");
        this.epoch = 0;
        this.setProduct(food);
        stock = 100;
        sellPrice = 50;
    }

    // The Producer's act function, called once per step, handling all the Producer's behavior.
    public void act() {
        epoch++;
        increaseStocks();
        updateSellPrice();
        if (messageWaiting) {
            handleMessages();
        }
    }

    // Increasing the Producer's stocks, called once per step, to simulate production.
    private void increaseStocks() {
        if (stock > (100 - production)) {
            stock = 100;
        } else {
            stock = stock + production;
        }
    }

    // Evaluating the Producer's sellprice, called once per step, adjusting it based on the current stock.
    // "upperS(tock)L(imit)" is set to 75, lowerS(tock)L(imit)" to 25.
    private void updateSellPrice() {
        if (stock > upperSL && sellPrice > 1) {
            sellPrice--;
        }
        if (stock < lowerSL && sellPrice < 100) {
            sellPrice++;
        }
    }

    // Handling all messages received this step, then emptying the message Vector.
    private void handleMessages() {
        for (Message message : messages) {
            Content content = message.content();
            switch (content) {
                case WHAT_IS_PRICE:
                    if (getProduct().equals(message.what())) {
                        if (stock > 3 * saleQuantity) {
                            message.sender().deliverMessage(new Message(this, Message.Content.PRICE_IS, getProduct(), sellPrice));
                        } else {
                            message.sender().deliverMessage(new Message(this, Message.Content.EMPTY_STOCK, getProduct()));
                        }
                    }
                    break;
                case ACCEPT_PRICE:
                    printToOutput(message.what(), message.number());
                    sell();
                    break;
                case REJECT_PRICE:
                    break;
                default:
                    System.exit(1);
            }
        }
        messages.clear();
        messageWaiting = false;
    }

    // Handling a sale, by decreasing stock and increasing the sellPrice.
    private void sell() {
        stock = stock - saleQuantity;
        sellPrice++;
    }

    // Returns the Producers' stock.
    public int getStock() {
        return stock;
    }

    // Returns the Producer's sellPrice.
    public int getSellPrice() {
        return sellPrice;
    }

    private void printToOutput(String product, int price) {
        System.out.print(epoch + ";buy;" + product);
        /*if (product.equals("fruit")) {
         System.out.print(";" + scape.avgFruitBuyPrice);
         }

         if (product.equals("meat")) {
         System.out.print(";" + scape.avgMeatBuyPrice);
         }

         if (product.equals("wine")) {
         System.out.print(";" + scape.avgWineBuyPrice);
         }

         if (product.equals("dairy")) {
         System.out.print(";" + scape.avgDairyBuyPrice);
         }*/
        System.out.println(";" + price);
    }
}