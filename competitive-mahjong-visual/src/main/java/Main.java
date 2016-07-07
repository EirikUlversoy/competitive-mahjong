
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage theStage)
    {
        theStage.setTitle("Hello, World!");
        theStage.show();
        theStage.setMaxHeight(1000);
        theStage.setMaxWidth(1000);
        
    }
}
