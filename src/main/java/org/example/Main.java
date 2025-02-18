package org.example;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static int WIDTH = 0;
    public static int HEIGHT = 0;

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
        graphicsContext.setFill(Color.DARKBLUE);

        StackPane root = new StackPane(canvas);

        Scene scene = new Scene(root);
        scene.setCursor(Cursor.DEFAULT);

        stage.setScene(scene);
        stage.setTitle("IP12 Prototype");
        stage.show();
        scene.getCursor();
        Player p = new Player(50, 50, 25, 100);
        p.addFollower(50);

        p.addFollower(50);
        p.addFollower(80);
        p.addFollower(150);

        Controller c = new Controller(p);
        c.startGameLogic();

        scene.setOnMouseMoved(move -> {
            p.dirX = move.getX();
            p.dirY = move.getY();
        });
        stage.setOnCloseRequest(event -> c.stopGameLogic());

        View v = new View(graphicsContext, p);
        v.startRendering();
    }


    public static void main(String[] args) {
        launch();
    }

}