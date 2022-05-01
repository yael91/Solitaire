package piles;

import objects.Card;
import piles.SuitPile;

import java.util.Stack;

public class SuitPileManager {

    public enum SuitPileEnum {
        ClubsPile, DiamondsPile, SpadesPile, HeartsPile
    }

    private final SuitPile[] suitStacks = new SuitPile[SuitPileEnum.values().length];

    public SuitPileManager() {
        for (int i = 0; i < suitStacks.length; i++) {
            suitStacks[i] = new SuitPile();
        }
    }

    public boolean canPushInOneOfThePiles(Card card) {
        for (SuitPile suitPile : suitStacks) {
            if (suitPile.canPush(card)) {
                return true;
            }
        }
        return false;
    }

    public void pushToSuitStack(Card card) {
        for (SuitPile suitStack : suitStacks) {
            if (suitStack.canPush(card)) {
                suitStack.push(card);
                return;
            }
        }
    }

    public Stack<Card> getSuitStack(SuitPileEnum index) {
        Stack<Card> stack = new Stack<>();
        for (Card card : this.suitStacks[index.ordinal()]) {
            stack.push(card);
        }
        return stack;
    }

    public boolean fullHouse() {
        return (suitStacks[SuitPileEnum.ClubsPile.ordinal()].size() == 13
                && suitStacks[SuitPileEnum.DiamondsPile.ordinal()].size() == 13
                && suitStacks[SuitPileEnum.SpadesPile.ordinal()].size() == 13
                && suitStacks[SuitPileEnum.HeartsPile.ordinal()].size() == 13);
    }
}
