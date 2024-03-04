package snakebattle.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe modélisant le modèle du jeu.
 */
public class GameModel {

    private Terrain terrain;
    private Player player1;
    private Player player2;

    private Player mousePlayer;
    private Player zqsdPlayer;
    private Player arrowPlayer;

    /**
     * Constructeur de la classe GameModel.
     * @param terrainWidth La largeur du terrain.
     * @param terrainHeight La hauteur du terrain.
     */
    public GameModel(int terrainWidth, int terrainHeight,String s, String t) {
        terrain = new Terrain(terrainWidth, terrainHeight);
    }

    /**
     * Initialise le jeu.
     * @param modeChoice Le mode de jeu choisi.
     * @param value Le nombre de joueurs IA.
     */
    public void initializeGame(int modeChoice, int value) {
        this.terrain.initAi(value);
        switch (modeChoice) {
            case 1:
                initMousePlayer();
                break;
            case 2:
                initMousePlayerAndZQSDPlayer();
                break;
            case 3:
                initZqsdPlayerAndArrowPlayer();
                break;
            default:
                break;
        }
    }

    /**
     * Initialise le joueurs souris. (mode 1 joueur)
     */
    private void initMousePlayer() {
        player1 = new Player("Joueur 1", new Snake(true));
        this.terrain.addPlayer(player1);
        this.mousePlayer = player1;
    }

    /**
     * Initialise les joueurs souris et ZQSD. (mode 2 joueurs)
     */
    private void initMousePlayerAndZQSDPlayer() {
        this.player1 = new Player("Joueur 1", new Snake(true));
        this.player2 = new Player("Joueur 2", new Snake(true));
        this.terrain.addPlayer(player1);
        this.terrain.addPlayer(player2);
        this.mousePlayer = player1;
        this.zqsdPlayer = player2;
    }

    /**
     * Initialise les joueurs ZQSD et les flèches directionnelles. (mode 2 joueurs)
     */
    private void initZqsdPlayerAndArrowPlayer() {
        this.player1 = new Player("Joueur 1", new Snake(true));
        this.player2 = new Player("Joueur 2", new Snake(true));
        this.terrain.addPlayer(player1);
        this.terrain.addPlayer(player2);
        this.zqsdPlayer = player2;
        this.arrowPlayer = player1;
    }

    /**
     * Vérifie si il y a des collisions entre un serpent et la nourriture.
     * @param snake Le serpent à vérifier.
     */
    private void checkFoodCollisionForSnake(Snake snake) {
        List<Food> foodsCopy = new ArrayList<>(terrain.getFoods());

        for (Food food : foodsCopy) {
            if (snake.checkCollisionWithFood(food)) {
                terrain.removeFood(food);
                terrain.generateFood();
                snake.grow();
            }
        }
    }

    /**
     * Vérifie si il y a des collisions entre les serpents et la nourriture.
     */
    public void checkFoodCollision() {
        // Vérification des collisions avec les serpents joueurs
        for (Snake playerSnake : this.terrain.getPlayerSnakes()) {
            checkFoodCollisionForSnake(playerSnake);
        }

        // Vérification des collisions avec les serpents IA
        for (Snake iaSnake : this.terrain.getIaSnakes()) {
            checkFoodCollisionForSnake(iaSnake);
        }
    }

    /**
     * Vérifie si il y a des collisions entre les serpents et leur propre queue.
     */
    public void checkSnakeAutoCollision() {
        List<Snake> allSnakes = new ArrayList<>(terrain.getIaSnakes());
        allSnakes.addAll(terrain.getPlayerSnakes());
        for (Snake snake : allSnakes) {
            snake.checkCollisionWithSelf(20.0);
        }
    }

    /**
     * Vérifie si il y a des collisions entre les serpents.
     */
    public void checkPlayerSnakeCollision() {
        List<Snake> allSnakes = new ArrayList<>(terrain.getIaSnakes());
        allSnakes.addAll(terrain.getPlayerSnakes());
        for(Snake snake : allSnakes){
            for(Snake otherSnake : allSnakes){
                if(snake != otherSnake){
                    if(snake.checkCollisionWithSnake(otherSnake, 10.0)){
                        if (snake.isPlayer()) {
                            this.terrain.getPlayerSnakes().remove(snake);
                        }else{
                            this.terrain.getIaSnakes().remove(snake);
                        }
                    }
                }
            }
        }
    }

    /**
     * Déplace les serpents des joueurs.
     */
    public void movePlayerSnake() {
        for(Snake snake : this.terrain.getPlayerSnakes()){
            snake.move();
        }
    }

    /**
     * Déplace les serpents IA.
     */
    public void moveIaSnakes(){
        for(Snake snake : this.terrain.getIaSnakes()){
            snake.moveRandom();
        }
    }

    /**
     * Retourne le terrain du jeu.
     */
    public Terrain getTerrain() {
        return this.terrain;
    }

    /**
     * Retourne lea liste des serpents IA.
     */
    public LinkedList<Snake> getIaSnakes(){
        return this.terrain.getIaSnakes();
    }

    /**
     * Retourne la liste des serpents des joueur.
     */
    public LinkedList<Snake> getPlayerSnakes() {
        return this.terrain.getPlayerSnakes();
    }

    /**
     * Retourne le joueur controlé par ZQSD.
     */
    public Player getZqsdPlayer() {
        return this.zqsdPlayer;
    }

    /**
     * Retourne le joueur controlé par les flèches directionnelles.
     */
    public Player getArrowPlayer() {
        return this.arrowPlayer;
    }

    /**
     * Retourne le joueur controlé par la souris.
     */
    public Player getMousePlayer() {
        return this.mousePlayer;
    }

    /*
     * Retourne le serpent du joueur contrôlé par ZQSD.
     */
    public Snake getZqsdSnake() {
        return this.zqsdPlayer.getSnake();
    }

    /**
     * Retourne le serpent du joueur contrôlé par les flèches directionnelles.
     */
    public Snake getArrowSnake() {
        return this.arrowPlayer.getSnake();
    }

    /**
     * Retourne le serpent du joueur contrôlé par la souris.
     */
    public Snake getMouseSnake() {
        return this.mousePlayer.getSnake();
    }
    
}
