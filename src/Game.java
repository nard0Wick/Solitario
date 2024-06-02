import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Game {
    List<Card> deck;
    List<List<Card>> piles = new ArrayList<>();  
    HashMap<String, List<Card>> otherPiles = new HashMap<>();
    int pointer = -1;
    
    public void createDeck() 
    { 
        deck = new ArrayList<>();
        for (int i = 1; i <= 13; i++) 
        { 
            Collections.addAll((deck),
                new Card(i, "Hearts"),
                new Card(i, "Diamonds"),
                new Card(i, "Clubs"),
                new Card(i, "Spades"));
        } 

    } 
    
    public void mixDeck() 
    { 
        Collections.shuffle(deck, new Random()); 
    } 
 
    public void createPiles() 
    { 
        for(int i = 0; i < 7; i++)
        {
            int n = 7;
            piles.add(new ArrayList<>());
            for(int j = i; piles.get(i).size() < i+1; j+=n)
            {
                piles.get(i).add(deck.get(j));
                if(piles.get(i).size()-1 == i) piles.get(i).get(i).setStatus(true);
                n--;
            }
        }
        deck = deck.subList(28, deck.size());
        otherPiles.put("Hearts", new ArrayList<Card>());
        otherPiles.put("Diamonds", new ArrayList<Card>());
        otherPiles.put("Clubs", new ArrayList<Card>());
        otherPiles.put("Spades", new ArrayList<Card>());
    } 

    public void moveCard (Card card, String originPile, String destinyPile)
    {
        int originPileInd = getPileInd(originPile);
        int destinyPileInd = getPileInd(destinyPile);

        if(originPileInd != -1 && destinyPileInd != -1 && checkCompatibility(card, Integer.toString(destinyPileInd)))
        {
            piles.get(originPileInd).remove(card);
            if(piles.get(originPileInd).size() > 0) piles.get(originPileInd).get(piles.get(originPileInd).size()-1)
                    .setStatus(true);
            piles.get(destinyPileInd).add(card);
        }else if(originPileInd != -1 && otherPiles.get(destinyPile) != null && checkCompatibility(card, destinyPile)){
            piles.get(originPileInd).remove(card);
            if(piles.get(originPileInd).size() > 0) piles.get(originPileInd).get(piles.get(originPileInd).size()-1)
                    .setStatus(true);
            otherPiles.get(destinyPile).add(card);
        }else if(deck.get(0).equals(card)){
            if(destinyPileInd != -1 && checkCompatibility(card, Integer.toString(destinyPileInd))) {
                deck.remove(card);
                piles.get(destinyPileInd).add(card);
            }else if(otherPiles.get(destinyPile) != null && checkCompatibility(card, destinyPile)){
                deck.remove(card);
                otherPiles.get(destinyPile).add(card);
            }
            pointer --;
        }
    }

    public void askDeckCard(){
        if(deck.size() > 0) {
            pointer ++;

            if(pointer == deck.size()) {
                pointer = 0;
                deck.stream().forEach(c -> c.setStatus(false));
                Collections.reverse(deck);
            }

            deck.get(pointer).setStatus(true);

            List<Card> supplier = new ArrayList<>();
            supplier.add(deck.get(pointer));
            supplier.addAll(deck.subList(0, pointer));
            supplier.addAll(deck.subList(pointer+1, deck.size()));

            deck = supplier;
        }

    }

    public boolean checkCompatibility(Card card, String destiny)
    {
        try{
            int cardInd = Integer.parseInt(destiny);
            if(piles.get(cardInd).isEmpty()) return true;
            if(cardInd != -1){
                if(piles.get(cardInd).get(piles.get(cardInd).size()-1).getValue() == card.getValue()+1 &&
                        !piles.get(cardInd).get(piles.get(cardInd).size()-1).getColor().equals(card.getColor())) return true;
            }
        }catch (NumberFormatException ex){
            if(card.getSuit().equals(destiny) &&
                    (otherPiles.get(destiny).isEmpty() ||
                            otherPiles.get(destiny).get(otherPiles.get(destiny).size()-1).getValue()+1 == card.getValue())) return true;

            /*if(otherPiles.get(destiny).get(otherPiles.get(destiny).size()-1).getValue() == card.getValue()&&
                    !otherPiles.get(destiny).get(otherPiles.get(destiny).size()-1).getColor().equals(card.getColor())) return  true;*/


            }
        return false;
        /*int cardInd = Integer.parseInt(destiny);
        if(cardInd != -1){
            if(piles.get(cardInd).get(piles.get(cardInd).size()-1).getValue() == card.getValue()+1 &&
                    !piles.get(cardInd).get(piles.get(cardInd).size()-1).getColor().equals(card.getColor())) return true;
        }else if(card.getSuit().equals(destiny) &&
                otherPiles.get(destiny).get(otherPiles.get(destiny).size()-1).getValue()+1 == card.getValue()) return true;
        else{
            try{
                if(otherPiles.get(destiny).get(otherPiles.get(destiny).size()-1).getValue() == card.getValue()&&
                        !otherPiles.get(destiny).get(otherPiles.get(destiny).size()-1).getColor().equals(card.getColor())) return  true;
            }catch (NullPointerException ex)
            {
                return false;
            }

            }*/
    }

    //implement method for changing card position
    public void changePile()
    {

    }

    public int getPileInd(String pileName)
    {
        try
         {
             return Integer.parseInt(pileName.split("-")[1]);
        }
        catch (NumberFormatException | IndexOutOfBoundsException ex)
        {
            return -1;
        }

    }

    public void printPiles() 
    { 
        int longest = maxPile(); 

        System.out.printf("| %-15s || %-15s || %-15s || %-15s || %-15s || %-15s || %-15s |%n", "Pile-6", "Pile-5", "Pile-4", "Pile-3", "Pile-2", "Pile-1", "Pile-0");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
 
        for (int i= 0; i < longest; i++ ) {
        
            for(int j = piles.size()-1; j >= 0; j--)
            {
                try
                {

                    /*System.out.printf("| %-3s  %-10s |",
                            piles.get(j).get(i).getValue(),
                            piles.get(j).get(i).getSuit());*/

                    if(piles.get(j).get(i).isStatus())
                    {
                        System.out.printf("| %-3s  %-10s |",
                                piles.get(j).get(i).getValue(),
                                piles.get(j).get(i).getSuit());
                    } else if(!piles.get(j).get(i).isStatus())
                    {
                        System.out.printf("| %-3s  %-10s |",
                                "**",
                                "*****");
                    }

                }catch (IndexOutOfBoundsException ex)
                {
                    System.out.printf("| %-3s  %-10s |",
                            "  ",
                            "     ");
                }
            }
            System.out.println();
        }
    }  

    public void printOtherPiles() 
    { 
        for(String reference : otherPiles.keySet()) 
        { 
            System.out.printf("%-10s", reference);

            otherPiles.get(reference).stream().forEach(c -> {
                if ((c.isStatus())) {
                    System.out.printf("| %-3s %-10s |", c.getValue(), c.getSuit());
                } else {
                    System.out.printf("| %-3s %-10s |", "**", "*****");
                }
            });
            System.out.println();
        }

        System.out.printf("%-10s", "leftDeck");
        deck.stream().forEach(c -> {
            if ((c.isStatus())) {
                System.out.printf("| %-3s %-10s |", c.getValue(), c.getSuit());
            } else {
                System.out.printf("| %-3s %-10s |", "**", "*****");
            }
        });

    }

    public int maxPile() 
    { 
        int max = 0;
        for(List<Card> l : piles) 
        { 
            if(l.size() > max) max = l.size();
        } 
        return max;
    } 


    public List<List<Card>> getPiles() {
        return piles;
    }
    public HashMap<String, List<Card>> getOtherPiles() {
        return otherPiles;
    }
    
    
}
