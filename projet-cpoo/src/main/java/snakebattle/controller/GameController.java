package snakebattle.controller;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import snakebattle.model.GameModel;
import snakebattle.model.Snake;
import snakebattle.view.GameView;
import snakebattle.view.MenuView;
import snakebattle.model.Direction;

/**
 * Contrôleur du jeu qui gère les interactions entre le modèle et la vue.
 */
public class GameController {

    private GameModel gameModel;
    private GameView gameView;
    private Stage primaryStage;
    private Scene menuScene;
    private boolean reset;

     /**
     * Constructeur du contrôleur du jeu. Initialise le modèle et démarre le jeu.
     * @param gameModel Le modèle du jeu.
     */
    public GameController(GameModel gameModel, Stage primaryStage) {
        this.gameModel = gameModel;
    }

    /**
     * Appelle le modèle pour initialiser le jeu.
     * @param modeChoice Le mode de jeu choisi.
     * @param value Le nombre de joueurs IA.
     */
    public void initializeGame(int modeChoice, int value) {
        reset=false;
        gameModel.initializeGame(modeChoice, value);
    }
    
    /**
     * Démarre le jeu en utilisant AnimationTimer pour gérer 
     * les mises à jour continues en fonction du temps.
     */
    public void startGame() {
        new AnimationTimer() {
            long lastUpdateTime = 0;
            final long nanosecondsPerSecond = 1_000_000_000;
            final double updateRate = 60.0; // Nombre d'images par seconde
            final double updateInterval = nanosecondsPerSecond / updateRate;

            @Override
            public void handle(long now) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = now;
                }
                // Calculer la différence de temps en nanosecondes
                long elapsedTime = now - lastUpdateTime;
                // Vérifier si le temps écoulé dépasse l'intervalle de mise à jour
                if (elapsedTime >= updateInterval) {
                    if(reset){
                        stop();
                        return;
                    }
                    updateModel();
                    updateView();

                    lastUpdateTime = now;
                }
            }
        }.start();
    }

     /**
     * Met à jour le modèle.
     */
    private void updateModel() {
        this.gameModel.movePlayerSnake();
        this.gameModel.moveIaSnakes();
        this.gameModel.checkFoodCollision();
        this.gameModel.checkSnakeAutoCollision();
        this.gameModel.checkPlayerSnakeCollision();
    }

     /**
     * Met à jour la vue en fonction du modèle actuel.
     */
    public void updateView() {
        this.gameView.updateViewFromModel(this);
    }

     /**
     * Modifie la direction du serpent du joueur contrôlé par ZQSD.
     * @param Direction La direction souhaitée (Enum Direction).
     */
    public void setZqsdSnakeDirection(Direction d) {
        if (gameModel.getZqsdPlayer().getSnake() == null) return;
        gameModel.getZqsdSnake().setDirection(d);
    }
    
     /**
     * Modifie la direction du serpent du joueur contrôlé par les flèches directionnelles.
     * @param Direction La direction souhaitée (Enum Direction).
     */
    public void setArrowSnakeDirection(Direction d) {
        if(gameModel.getArrowPlayer().getSnake() == null) return;
        gameModel.getArrowSnake().setDirection(d);
    }

    /**
     * Modifie la direction du serpent du joueur en fonction de la position de la souris.
     * @param mouseX Position X de la souris.
     * @param mouseY Position Y de la souris.
     */
    public void setSnakeDirectionFromMouse(double mouseX, double mouseY) {
        if(gameModel.getMouseSnake() == null || gameModel.getMousePlayer() == null || gameModel.getMouseSnake().getTail().isEmpty()) return;
        Point2D localMouse = gameView.getView().sceneToLocal(mouseX, mouseY);
        double newDirection = calculateDirection(localMouse.getX(), localMouse.getY(), gameModel.getMouseSnake());
        gameModel.getMouseSnake().setMouseDirection(newDirection);
    }

     /**
     * Calcule la direction du serpent en fonction des coordonnées de la souris.
     * @param mouseX Position X de la souris.
     * @param mouseY Position Y de la souris.
     * @param snake Le serpent.
     * @return La nouvelle direction en degrés.
     */
    private double calculateDirection(double mouseX, double mouseY, Snake snake) {
        double headX = snake.getHead().getX();
        double headY = snake.getHead().getY();
    
        double angle = Math.atan2(mouseY - headY, mouseX - headX);
        double newDirection = Math.toDegrees(angle);
    
        // Ajuster pour obtenir une valeur positive
        if (newDirection < 0) {
            newDirection += 360;
        }
    
        return newDirection;
    }

     /**
     * Associe la vue du jeu au GameController.
     */
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

     /**
     * Retourne le modèle du jeu.
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public void returnToMenu() {
        primaryStage.setScene(menuScene);
        primaryStage.show();
        resetGame();
    }

    public void setPrimeprimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMenuScene(Scene menuScene) {
        this.menuScene = menuScene;
    }

    public void resetGame() {
        this.reset=true;
        this.gameModel = new GameModel(1000, 1000, "Joueur 1", "Joueur 2");
    }

}

