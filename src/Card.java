import java.util.Objects;

public class Card {
    int value; 
    String suit;  
    boolean status;

    String color;
   
    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
        this.status = false;
        if(suit.equals("Spades") || suit.equals("Clubs")) color = "black";
        else color = "red";
    }
    public Card(int value, String suit, boolean status)
    {
        this.value = value;
        this.suit = suit;
        this.status = status;
        if(suit.equals("Spades") || suit.equals("Clubs")) color = "black";
        else color = "red";
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && Objects.equals(suit, card.suit);
    }

}
