package snakebattle.model;

/**
 * Classe modélisant un joueur dans le jeu.
 */
public class Player {

    private Snake snake;
    private String name;

    public Player(String name, Snake snake) {
        this.name = name;
        this.snake = snake;
        this.snake.setName(name);
    }

    /**
     * Retourne le serpent du joueur.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Retourne le nom du joueur.
     */
    public String getName() {
        return name;
    }

}
