/**
 * Created by svkreml on 27.08.2017.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TicTac extends Application {
    int номерХода = 0;
    Координата смещение = new Координата(10, 10);
    char[][] pool = new char[3][3];
    char[] chars = {'X', 'O'};
    GraphicsContext gc;
       Canvas field = new Canvas(400, 400);
       Canvas game = new Canvas(400, 400);

    public static void main(String[] args) {
        launch(args);
    }

    private static void drawLine(GraphicsContext gc, double x1, double y1, double x2, double y2, Координата смещение) {
        gc.strokeLine(x1 + смещение.x, y1 + смещение.y, x2 + смещение.x, y2 + смещение.y);
    }

    private static void drawLine(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();

        gc = field.getGraphicsContext2D();
        нарисоватьПоле();
        root.getChildren().add(field);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        gc.beginPath();
        gc.moveTo(50, 50);
        gc.bezierCurveTo(150, 20, 150, 150, 75, 150);
        gc.closePath();
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {


                double sceneX = event.getSceneX();
                double sceneY = event.getSceneY();
                System.out.println(sceneX - смещение.x);
                System.out.println(sceneY - смещение.y);
                int x = (int) (sceneX - смещение.x) / 50;
                int y = (int) (sceneY - смещение.y) / 50;
                System.out.println(x + " " + y);
                if (x < 0 || x > 2 || y < 0 || y > 2) return;
                turn(x, y);
            }




        });

        // drawX(gc,смещение, 2,2);
        // drawO(gc,смещение, 8,1);
    }

    private void turn(int x, int y) {
        if (pool[x][y] == 'X' || pool[x][y] == 'O') return;
        try {
            if (номерХода % 2 == 0)
                drawX(gc, смещение, x, y);
            else drawO(gc, смещение, x, y);
            номерХода++;
            lookForWin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lookForWin() {
        for (char c : chars) {
            for (int i = 0; i < 3; i++) {
                if (pool[i][0] == c && pool[i][1] == c && pool[i][2] == c)
                    win(c);
                if (pool[0][i] == c && pool[1][i] == c && pool[2][i] == c)
                    win(c);
            }
            if (pool[0][0] == c && pool[1][1] == c && pool[2][2] == c) {
                win(c);
            }
            if (pool[2][0] == c && pool[1][1] == c && pool[0][2] == c) {
                win(c);
            }
        }
        if (номерХода >= 9) {
            нарисоватьПоле();
        }
    }

    private void win(char x) {
        gc.clearRect(0, 0, 300, 200);
        gc.fillText(x + " Win", 210, 20);
        try {
            нарисоватьПоле();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void нарисоватьПоле() {
        // внешний контур
        gc.clearRect(0, 0, 200, 400);
        gc = field.getGraphicsContext2D();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pool[i][j] = 0;
            }
        }
        номерХода = 0;
        drawLine(gc, 0, 0, 0, 150, смещение);
        drawLine(gc, 0, 0, 150, 0, смещение);
        drawLine(gc, 150, 0, 150, 150, смещение);
        drawLine(gc, 0, 150, 150, 150, смещение);
        // вертикальные линии
        drawLine(gc, 50, 0, 50, 150, смещение);
        drawLine(gc, 100, 0, 100, 150, смещение);
        // горизонтальные линии
        drawLine(gc, 0, 50, 150, 50, смещение);
        drawLine(gc, 0, 100, 150, 100, смещение);

        drawLine(gc, 200, 0, 200, 400);


    }

    private void drawX(GraphicsContext gc, Координата смещение, int posX, int posY) throws Exception {
        if (posX < 0 || posX > 2 || posY < 0 || posY > 2)
            throw new Exception("Вне границ поля (" + posX + ", " + posY + ")");
        pool[posX][posY] = 'X';
        //нарисовать крестик
        drawLine(gc, 5 + смещение.x + 50 * posX, 5 + смещение.y + 50 * posY, 45 + смещение.x + 50 * posX, 45 + смещение.y + 50 * posY);
        drawLine(gc, 5 + смещение.x + 50 * posX, 45 + смещение.y + 50 * posY, 45 + смещение.x + 50 * posX, 5 + смещение.y + 50 * posY);
    }

    private void drawO(GraphicsContext gc, Координата смещение, int posX, int posY) throws Exception {
        if (posX < 0 || posX > 2 || posY < 0 || posY > 2)
            throw new Exception("Вне границ поля (" + posX + ", " + posY + ")");
        pool[posX][posY] = 'O';
        //нарисовать круг
        gc.strokeOval(5 + смещение.x + 50 * posX, 5 + смещение.y + 50 * posY, 40, 40);
    }


    class Координата {
        double x;
        double y;

        public Координата(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
