/**This class defines a simple node which displays a level on a map.
 * File: MapNode.java
 * Authors: Okhrimenko Mykhailo, Serdiuk Andrii
 * */

import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class MapNode extends StackPane {

    Circle external;
    Circle internal;
    boolean locked = false;
    MapNode next;


    double right;
    double left;
    double top;
    double bottom;


    public MapNode(double radius){
        super();
        this.setPrefSize(2*radius,2*radius);
        this.setMaxSize(2*radius,2*radius);
        this.setVisible(true);
        external = new Circle(radius, Color.GRAY);
        internal = new Circle(radius - 10,Color.WHITE);
        addListeners();
        this.getChildren().addAll(external, internal);
        right = this.getLayoutX() + 2*external.getRadius();
        left = this.getLayoutX();
        top = this.getLayoutY();
        bottom = this.getLayoutY() + 2*external.getRadius();
    }

    public MapNode(double radius, boolean isLocked){
        this(radius);
        setLocked(isLocked);
    }

    public MapNode(double radius, boolean isLocked, MapNode next){
        this(radius, isLocked);
        this.next = next;
        next.setLocked(true);
    }

    public MapNode(double bigRadius, double smallRadius){
        super();
        this.setPrefSize(2*bigRadius,2*bigRadius);
        this.setMaxSize(2*bigRadius,2*bigRadius);
        this.setVisible(true);
        external = new Circle(bigRadius, Color.GRAY);
        internal = new Circle(smallRadius,Color.WHITE);
        addListeners();
        this.getChildren().addAll(external,internal);
    }

    /**This method sets next map node to this map node.
     * */
    public void setNext(MapNode next){
          this.next = next;
    }
    /**This method sets whether this map node is locked.
     * */
    void setLocked(boolean locked){
        this.locked = locked;
        if(locked) {
            internal.setFill(Color.gray(1, 0.4));
            this.setOpacity(0.6);
        }
        else {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.75), this);
                ft.setFromValue(0.6);
                ft.setToValue(1);
                ft.play();
                internal.setFill(Color.WHITE);
        }
    }

    /**This method unlocks next map node if this node has it.
     * */
    void unlockNext(){
        if(!locked && next != null && next.locked)
            next.setLocked(false);
    }

    /**This method adds listeners to the circles which scale this object when it one of the circles is hovered.
     * */
    private void addListeners() {
        external.setOnMouseEntered(event -> {
            MapNode.this.setScaleX(1.1);
            MapNode.this.setScaleY(1.1);
        });
        external.setOnMouseExited(event -> {
            MapNode.this.setScaleX(1.0);
            MapNode.this.setScaleY(1.0);
        });
        internal.setOnMouseEntered(event -> {
            MapNode.this.setScaleX(1.1);
            MapNode.this.setScaleY(1.1);
        });
        internal.setOnMouseExited(event -> {
            if(!external.isHover()){
                MapNode.this.setScaleX(1.0);
                MapNode.this.setScaleY(1.0);
            }
        });
        this.setOnMouseClicked(event -> {
            this.unlockNext();
        });
    }

}
