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

        Head p = new Head(500, 500, 600, 60, 20);
        Moveable m = p.addBodypart(70,20);
        for (int i = 0; i < 10; i++) {
            m.addBodypart(50,20);
            m = m.follower;
        }
        for (int i = 0; i < 10; i++) {
            m.addBodypart(40,20);
            m = m.follower;
        }
        for (int i = 0; i < 10; i++) {
            m.addBodypart(30,20);
            m = m.follower;
        }

        scene.setOnMouseMoved(move -> {
            mouseX = move.getX();
            mouseY = move.getY();
        });

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