package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    private static final String HELP_WINDOW_MESSAGE = "#helpMessage";

    public HelpWindowHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(HELP_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public String getUrl() {
        Label tmp = (Label) getChildNode(HELP_WINDOW_MESSAGE);
        return tmp.getText().split(" ")[5];
    }
}
