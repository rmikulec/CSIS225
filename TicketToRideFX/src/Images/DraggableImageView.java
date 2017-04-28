package Images;

/**
 * Created by Ryan on 4/27/2017.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public  class DraggableImageView extends ImageView {
    private double mouseX ;
    private double mouseY ;
    public DraggableImageView(Image image) {
        super(image);

        setOnMousePressed(event -> {
            mouseX = event.getSceneX() ;
            mouseY = event.getSceneY() ;
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX ;
            double deltaY = event.getSceneY() - mouseY ;
            relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
            mouseX = event.getSceneX() ;
            mouseY = event.getSceneY() ;
        });
    }
}