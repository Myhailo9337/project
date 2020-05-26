/**This class describes a simple menu for the game.
 * File: Menu.java
 * Authors: Okhrimenko Mykhailo, Serdiuk Andrii
 * */

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.List;

public class Menu {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private List<Pair<String, Runnable>> menuData;

    Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;

    public Menu(List<Pair<String, Runnable>> menuData){
        this.menuData = menuData;
    }

    Parent createContent() {
        root.getChildren().removeAll(root.getChildren());
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    /**This method adds the title to the menu.
     * */
    private void addTitle() {
        Title title = new Title("Game`s name");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }
    /**This method adds a vertical line to the menu.
     * */
    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 150);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }
    /**This method starts the animation for the menu to become visible.
     * */
    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    /**This method adds all the components to the menu.
     **/
    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }
    /**This class describes a menu item.
     * */
    public static class MenuItem extends Pane {
        private Text text;

        private Effect shadow = new DropShadow(5, Color.BLACK);
        private Effect blur = new BoxBlur(1, 1, 3);

        public MenuItem(String name) {
            Polygon bg = new Polygon(
                    0, 0,
                    200, 0,
                    215, 25,
                    200, 40,
                    0, 40
            );
            bg.setStroke(Color.color(1, 1, 1, 0.75));
            bg.setEffect(new GaussianBlur());

            bg.fillProperty().bind(
                    Bindings.when(pressedProperty())
                            .then(Color.color(0, 0, 0, 0.75))
                            .otherwise(Color.color(0, 0, 0, 0.25))
            );

            text = new Text(name);
            text.setTranslateX(5);
            text.setTranslateY(20);
            text.setFill(Color.WHITE);

            text.effectProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(shadow)
                            .otherwise(blur)
            );

            getChildren().addAll(bg, text);
        }

        public void setOnAction(Runnable action) {
            setOnMouseClicked(e -> action.run());
        }
    }

    /**This class describes a title for the menu.
     * */
    public static class Title extends Pane {
        private Text text;

        public Title(String name) {
            String spread = "";
            for (char c : name.toCharArray())
                spread += c + " ";

            text = new Text(spread);
            text.setFont(Font.loadFont(Menu.class.getResource("fonts/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(),48));
            text.setFill(Color.WHITE);
            text.setEffect(new DropShadow(30, Color.BLACK));

            getChildren().addAll(text);
        }

        public double getTitleWidth() {
            return text.getLayoutBounds().getWidth();
        }

        public double getTitleHeight() {
            return text.getLayoutBounds().getHeight();
        }
    }


}