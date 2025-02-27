package org.example;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    public static double mouseX;
    public static double mouseY;


    @Override
    public void start(Stage stage) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screenSize.width;
        HEIGHT = screenSize.height;
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        javafx.scene.canvas.Canvas canvas = new Canvas(WIDTH, HEIGHT);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setImageSmoothing(false);

        StackPane root = new StackPane(canvas);

        Scene scene = new Scene(root);
        scene.setCursor(Cursor.DEFAULT);

        stage.setScene(scene);
        stage.setTitle("Procedural Movement");
        stage.show();
        scene.getCursor();
        scene.setOnMouseMoved(move -> {
            mouseX = move.getX();
            mouseY = move.getY();
        });
        int distance = 30;
        Head p = new Head(50, 50, 1000, 68, distance);
        Moveable m = p.addBodypart(84, distance);
        int[] bodySizes = {87, 85, 83, 77, 64, 60, 51, 38, 32, 19, 15};

        for (int size : bodySizes) {
            m = m.addBodypart(size, distance);

        }

        for (int i = 0; i < 10000; i++) {

          //m = m.addBodypart(50, distance);
        }







        Controller c = new Controller(p);
        c.startGameLogic();

        stage.setOnCloseRequest(event -> c.stopGameLogic());

        View v = new View(graphicsContext, p);
        v.startRendering();
    }


    public static void main(String[] args) {
        launch();
    }

}