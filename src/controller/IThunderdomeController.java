package controller;

import model.IThunderdome;
import view.IThunderdomeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface IThunderdomeController {

    IThunderdome getModel();

    IThunderdomeView getView();

    void loadModel(Path gameContents) throws IOException;

    void run() throws InterruptedException;
    
}
