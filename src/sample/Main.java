/**This program describes a game in which the player is to help a student of Kyiv-Mohyla Academy to pass all the exams.
 * File: Main.java
 * Authors: Okhrimenko Mykhailo, Serdiuk Andrii
 * */

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;


public class Main extends Application {

        Stage stage;
        Scene gameScene;
        Menu menu;
        boolean pause = false;

        @Override
        public void start(Stage stage) {
            this.stage = stage;
            stage.setTitle("The name of the game");
            //stage.initStyle(StageStyle.UTILITY);
           menu = createMenu();
        }
    /**This method creates the general game menu.
     * */
    private Menu createMenu() {
        Pair<String,Runnable> pair;
        if(pause)
            pair = new Pair<String, Runnable>("Resume", () -> {stage.setScene(gameScene);});
        else
            pair = new Pair<String, Runnable>("Start", () -> {
                this.switchToGame();
            });
        List<Pair<String, Runnable>> menuData = Arrays.asList(
                pair,
                new Pair<String, Runnable>("Game Options", () -> {
                    createOptionsMenu();
                }),
                new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
        );
        Menu menu = new Menu(menuData);
        Scene scene = new Scene(menu.createContent(), 1000, 600);
        scene.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ESCAPE)
                stage.setScene(gameScene);
        });
        stage.setScene(scene);
        stage.show();
        return menu;
    }


    /**This method creates the menu with game options for the user.
     * */
    private void createOptionsMenu(){
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), menu.root);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        List<Pair<String, Runnable>> menuData = Arrays.asList(
                new Pair<String, Runnable>("Back", () -> {
                    createMenu();
                }),
                new Pair<String, Runnable>("Option 1", () -> {

                }),
                new Pair<String, Runnable>("Option 2", () -> {})
        );
        Menu menu = new Menu(menuData);
        Scene scene = new Scene(menu.createContent(),1000,600);
        scene.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.ESCAPE)
                stage.setScene(gameScene);
        });
        stage.setScene(scene);
    }

    /**This method allows the user to switch from the menu to the game.
         * */
        void switchToGame(){
            pause = false;
            HBox group = new HBox(10);
            MapNode fourth = new MapNode(35);
            MapNode third = new MapNode(35,true, fourth);
            MapNode second = new MapNode(35, true,third);
            MapNode first = new MapNode(35, false, second);
            group.setAlignment(Pos.CENTER);
            group.getChildren().addAll(first,second,third,fourth);
            gameScene = new Scene(group,1000,600);
            gameScene.setOnKeyPressed(event ->{
                if(event.getCode() == KeyCode.ESCAPE){
                    pause = !pause;
                    if(!pause)
                        stage.setScene(gameScene);
                    else
                        createMenu();
                }
            });
            stage.setScene(gameScene);
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
