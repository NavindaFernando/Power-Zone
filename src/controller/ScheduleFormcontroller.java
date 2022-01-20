package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ScheduleFormcontroller {
    public ImageView imageEleven;
    public FontAwesomeIconView eyeHide;
    public FontAwesomeIconView eyeOpen;

    public void hideImageEleven(MouseEvent mouseEvent) {
        imageEleven.setVisible(false);
        eyeHide.setVisible(false);
        eyeOpen.setVisible(true);
    }

    public void openImageEleven(MouseEvent mouseEvent) {
        imageEleven.setVisible(true);
        eyeHide.setVisible(true);
        eyeOpen.setVisible(false);
    }
}
