package objects;

public class Card {
    private static Card[][] cards = new Card[Suit.values().length][Rank.values().length];
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public static Card getCard(Rank rank, Suit suit) {
        if (cards[suit.ordinal()][rank.ordinal()] == null) {
            cards[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
        }

        return cards[suit.ordinal()][rank.ordinal()];
    }

    private static Card cardsFactory(Rank rank, Suit suit) {
        if (cards[suit.ordinal()][rank.ordinal()] == null) {
            cards[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
        }
        return cards[suit.ordinal()][rank.ordinal()];
    }

    public static Card get(String pId) {
        assert pId != null;
        int id = Integer.parseInt(pId);
        return cardsFactory(Rank.values()[id % Rank.values().length], Suit.values()[id / Rank.values().length]);
    }

    public boolean equals(Card card) {
        return (this.getSuit() == card.getSuit() && this.getRank() == card.getRank());
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

}
