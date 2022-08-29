import controller.IThunderdomeController;
import controller.ThunderdomeController;
import model.IThunderdome;
import model.Thunderdome;
import view.IThunderdomeView;
import view.text_view.ThunderdomeTextView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        IThunderdomeView view = new ThunderdomeTextView();
        IThunderdome model = new Thunderdome();
        IThunderdomeController controller = new ThunderdomeController(model, view);
        controller.run();
    }
}
