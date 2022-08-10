package controller;

import model.IThunderdome;

import java.io.File;

public interface IThunderdomeController {

    IThunderdome getModel();

    void loadModel(File gameContents);

    void run();
    
}
