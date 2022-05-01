package views;

import objects.Card;
import objects.Rank;
import objects.Suit;

import java.util.HashMap;
import java.util.Map;
import java.applet.*;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class CardImages extends Applet {
    private static Map<String, String> cards = new HashMap<>();
    private static final String IMAGE_LOCATION = "images/";
    private static final String IMAGE_SUFFIX = ".png";

    public static String getKey(Card card) {
        return Rank.values()[card.getRank().ordinal()].toString() + "_" + Suit.values()[card.getSuit().ordinal()];
    }

    public static String getCardImageUrl(Card card) {
        assert card != null;
        return getCardImageUrl(getKey(card));
    }

    private static String getCardImageUrl(String imageName) {
        String imageUrlString = cards.get(imageName);

        if (imageUrlString == null) {
            imageUrlString = String.valueOf(views.CardImages.class.getClassLoader().getResource(IMAGE_LOCATION + imageName + IMAGE_SUFFIX));
            cards.put(imageName, imageUrlString);
        }

        return imageUrlString;
    }

    public static String getBackCardImageUrl() {
        return getCardImageUrl("back");
    }

    public static String getBaseUrlString() {
        return getCardImageUrl("base");
    }
}
