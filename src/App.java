import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {

        playGame();

    }

    private static Card remitCard(String s) {
        String[] parts = s.split(" ");
        return new Card(Integer.parseInt(parts[0]), parts[1], true);
    }

    public static void playGame()
    {
        Game game = new Game();
        game.createDeck();
        game.mixDeck();
        game.createPiles();
        Scanner in = new Scanner(System.in);

        while(true)
        {
            game.printPiles();
            game.printOtherPiles();
            System.out.println("\n" +
                    "-[1] - request a movement\n" +
                    "-[2] - request one card from the deck left?\n");
            System.out.println("waiting...");
            String inp = in.nextLine();

            if(inp.equals("1"))
            {
                System.out.println("Introduce the card that you want to move [value] [space] [suit]: ");
                Card card = remitCard(in.nextLine());
                System.out.println("Introduce the origin's pile name: ");
                String origin = in.nextLine();
                System.out.println("Introduce the destiny's pile name: ");
                String destiny = in.nextLine();

                game.moveCard(card, origin, destiny);

            }else if(inp.equals("2"))
            {
                game.askDeckCard();
            }
        }
    }

}
