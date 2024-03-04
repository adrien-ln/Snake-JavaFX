package snakebattle.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import snakebattle.controller.GameController;
import snakebattle.model.Food;
import snakebattle.model.Snake;


/**
 * Classe mdoélisant la vue du jeu.
 */
public class GameView {

    private Group gameElementsGroup;
    private List<Label> nameLabelList = new ArrayList<>();
    private Pane view;
    private GameController gameController;
    private Button backButton;


    /**
     * Constructeur de la classe GameView.
     * @param gameController Le contrôleur du jeu.
     */
    public GameView(GameController gameController) {
        this.gameController = gameController;
        this.gameElementsGroup = new Group();
        this.view = new Pane();
        this.view.getChildren().add(gameElementsGroup);
        // Dessiner contour du terrain
        Rectangle terrainContour = new Rectangle(0, 0, gameController.getGameModel().getTerrain().getLargeur(), gameController.getGameModel().getTerrain().getHauteur());
        terrainContour.setFill(Color.GREY);
        terrainContour.setStroke(Color.BLACK);
        terrainContour.setStrokeWidth(10);
        gameElementsGroup.getChildren().add(terrainContour);

        this.backButton = new Button("Retour au Menu");
        backButton.setOnAction(event -> gameController.returnToMenu());
        gameElementsGroup.getChildren().add(backButton);
    }

    /**
     * Retourne le Pane de la vue du jeu (pour l'afficher dans une scène).
     */
    public Pane getView() {
        return view;
    }

    /**
     * Dessine le serpent passé en paramètre.
     * @param snake Le serpent à dessiner.
     * @param isPlayer Si le serpent est un joueur ou non.
     */
    public void drawSnake(Snake snake, boolean isPlayer) {
        // Supprimer uniquement les cercles et les labels correspondant à ce serpent
        gameElementsGroup.getChildren().removeIf(node ->
                (node instanceof Circle || node instanceof Label) && ((Node) node).getUserData() == snake);

        if (!snake.isMort()) {
            // Dessiner le nouveau serpent en parcourant la liste à l'envers
            for (int i = snake.getTail().size() - 1; i >= 0; i--) {
                Point2D head = snake.getTail().get(i);
                Circle circle = createSnakeCircle(head.getX(), head.getY(), isPlayer);
                gameElementsGroup.getChildren().add(circle);

                // Ajouter le nom à côté de la tête du serpent
                if (i == 0 && isPlayer) {
                    String playerName = snake.getName();
                    addNameLabel(snake, playerName, head.getX() + 15, head.getY());
                }
            }   
        }
    }

    /**
     * Ajoute un label contenant le nom de la têtedu serpent.
     * @param snake Le serpent.
     * @param playerName Le nom du joueur ou de l'IA.
     * @param x La position X du label.
     * @param y La position Y du label.
     */
    private void addNameLabel(Snake snake, String playerName, double x, double y) {
        Label nameLabel = new Label(playerName);
        nameLabel.setLayoutX(x);
        nameLabel.setLayoutY(y);
        nameLabelList.add(nameLabel);
        gameElementsGroup.getChildren().add(nameLabel);
    }

    /**
     * Crée un cercle pour représenter une partie du corps d"un serpent.
     * @param x La position en x du cercle.
     * @param y La position en y du cercle.
     * @param isPlayer Si le serpent est un joueur ou non.
     */
    private Circle createSnakeCircle(double x, double y, boolean isPlayer) {
        double radius = 10; // Exemple de rayon
        Paint color;

        if(isPlayer){
            color = createGradient(Color.BLACK, Color.RED);
        }else{
            color = createGradient(Color.BLUE, Color.LIGHTBLUE);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(color); // Remplissage dégradé
        return circle;
    }

    /**
     * Crée un dégradé de couleur.
     * @param startColor La couleur de départ.
     * @param endColor La couleur de fin.
     */
    private Paint createGradient(Color startColor, Color endColor) {
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, startColor),
                new Stop(1, endColor));
    }

    /**
     * Met à jour la vue à partir du modèle.
     * @param gameController Le contrôleur du jeu.
     */
    public void updateViewFromModel(final GameController gameController) {
        // Supprimer les cercles de serpent
        gameElementsGroup.getChildren().removeIf(node -> node instanceof Circle && ((Circle) node).getUserData() != "snake");
        // Supprimer les labels de nom
        gameElementsGroup.getChildren().removeAll(nameLabelList);

        // Dessiner les serpents
        for(Snake snake : gameController.getGameModel().getPlayerSnakes()) {
            drawSnake(snake, true);
        }

        for (Snake snake : gameController.getGameModel().getIaSnakes()) {
            drawSnake(snake, false);
        }

        // Dessiner la nourriture
        for (Food food : gameController.getGameModel().getTerrain().getFoods()) {
            drawFood(food);
        }
    }

    /**
     * Dessine la nourriture passée en paramètre.
     * @param food La nourriture à dessiner.
     */
    private void drawFood(Food food) {
        Circle circle = new Circle(food.getPos().getX(), food.getPos().getY(), 5);
        circle.setFill(Color.GREEN);
        gameElementsGroup.getChildren().add(circle);
    }

    /**
     * Appel la méthode qui gère déplacement du serpent du joueur en fonction de la position de la souris.
     * @param mouseX Position X de la souris.
     * @param mouseY Position Y de la souris.
     */
    public void handleMouseMoved(double mouseX, double mouseY) {
        if (gameController.getGameModel().getMousePlayer() == null) return;
        gameController.setSnakeDirectionFromMouse(mouseX, mouseY);
    }

    public Button getBackButton() {
        return this.backButton;
    } 

}