package snakebattle;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import snakebattle.controller.GameController;
import snakebattle.model.Direction;
import snakebattle.model.GameModel;
import snakebattle.view.GameView;
import snakebattle.view.MenuView;

/**
 * Classe principale du jeu.
 */
public class GameApp extends Application {

    private GameController gameController;

    private GameModel gameModel;

    private GameView gameView;
    private MenuView menuView;
    private Scene menuScene;
    private Scene gameScene;

    /**
     * Méthode principale pour lancer le jeu.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Init du modèle MVC
        this.gameModel = new GameModel(1000, 1000 , "Joueur 1", "Joueur 2");
        this.gameController = new GameController(gameModel, primaryStage);
        this.gameView = new GameView(gameController);
        this.gameController.setGameView(gameView);
        this.gameController.setPrimeprimaryStage(primaryStage);

        // Init de la scène du menu principal
        this.menuView = new MenuView();
        // Initialiser le modèle avec les paramètres du menu
        menuView.setOnPlayButtonClicked(event -> {
            this.gameController.initializeGame(menuView.getModeChoice(), (int) menuView.getAiCount());
            startGame(primaryStage);
        });

        Group menuRoot = new Group();
        menuRoot.getChildren().add(menuView);
        this.menuScene = new Scene(menuRoot, 1000, 1000);
        this.menuScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        // Centrer la vbox du menu sur la scène
        menuView.translateXProperty().bind(menuScene.widthProperty().subtract(menuView.widthProperty()).divide(2));
        menuView.translateYProperty().bind(menuScene.heightProperty().subtract(menuView.heightProperty()).divide(2));

        this.gameController.setMenuScene(menuScene);
        // Init la scène du jeu
        Group gameRoot = new Group();
        gameRoot.getChildren().add(gameView.getView()); // Ajouter l'écran d'accueil à la racine
        gameScene = new Scene(gameRoot, 1000, 1000);

        // Afficher la scène du menu principal
        primaryStage.setTitle("Snake Battle");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    /**
     * Initialise les contrôles du jeu.
     */
    public void initControls() {
        if (gameController.getGameModel().getMousePlayer() != null) {
            gameView.getView().setOnMouseMoved(event -> gameView.handleMouseMoved(event.getX(), event.getY()));
            gameView.getView().setOnMouseDragged(event -> gameView.handleMouseMoved(event.getX(), event.getY()));
        }
        gameScene.setOnKeyPressed(this::handleKeyPressed);
    }

    /**
     * Change de scène principal et démarre le jeu.
     * @param primaryStage La fenêtre du jeu.
     */
    private void startGame(Stage primaryStage) {
        initControls();
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.getView().requestFocus();
        gameController.startGame();
    }
    
    /**
     * Gère les événements liés aux touches du clavier pendant le jeu.
     * @param event La touche pressée.
     */
    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();

        if(gameController.getGameModel().getZqsdPlayer() != null){
            // Touches ZQSD
            if (code == KeyCode.Z) {
                gameController.setZqsdSnakeDirection(Direction.UP);
            }else if(code == KeyCode.S){
                gameController.setZqsdSnakeDirection(Direction.DOWN);
            }else if(code == KeyCode.Q){
                gameController.setZqsdSnakeDirection(Direction.LEFT);
            }else if(code == KeyCode.D){
                gameController.setZqsdSnakeDirection(Direction.RIGHT);
            }
        }
        
        if (gameController.getGameModel().getArrowPlayer() != null) {
                // Touches directionnelles
            if (code == KeyCode.UP) {
                gameController.setArrowSnakeDirection(Direction.UP);
            }else if(code == KeyCode.DOWN){
                gameController.setArrowSnakeDirection(Direction.DOWN);
            }else if(code == KeyCode.LEFT){
                gameController.setArrowSnakeDirection(Direction.LEFT);
            }else if(code == KeyCode.RIGHT){
                gameController.setArrowSnakeDirection(Direction.RIGHT);
            }
        }
    }
}
