package view.common;

import direction.Direction;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Represents buttons with different images according to ButtonDirection value

public class MoveButton extends Button {

    // buttons arrow brightness
    private static final double BRIGHTNESS = 0.4;
    private final Direction direction;

    public MoveButton(Direction direction) {
        super();
        this.direction = direction;
        buildButton();
    }

    public Direction getDirection() {
        return direction;
    }

    private void buildButton() {
        // add css class
        getStyleClass().add("move-button");

        // set image
        var image = new Image(getClass().getResourceAsStream(
                getImagePath()), 20, 20, true, false);

        // adjust brightness and set image
        setGraphic(adjustBrightness(image));
    }

    //adjust brightness
    private ImageView adjustBrightness(Image image) {
        var imgView = new ImageView(image);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(BRIGHTNESS);

        imgView.setEffect(colorAdjust);
        return imgView;
    }

    private String getImagePath() {
        String path = "/icons/";
        switch (direction) {
            case UP:
                path += "up.png";
                break;
            case DOWN:
                path += "down.png";
                break;
            case LEFT:
                path += "left.png";
                break;
            case RIGHT:
                path += "right.png";
                break;
        }
        return path;
    }
}
