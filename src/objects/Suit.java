package objects;

public enum Suit {
    hearts,
    diamonds,
    spades,
    clubs;

    // color check
    public boolean sameColorAs(Suit suit)
    {
        if( this == clubs || this == spades )
        {
            return suit == clubs || suit == spades;
        }
        else
        {
            return suit == diamonds || suit == hearts;
        }
    }
}
