package snakebattle.model;

import javafx.geometry.Point2D;

/**
 * Classe modélisant la nourriture dans le jeu.
 */
public class Food {

    private Point2D pos;

    /**
     * Constructeur de la classe Food.
     * @param x La position en x de la nourriture.
     * @param y La position en y de la nourriture.
     */
    public Food(Point2D pos) {
        this.pos = pos;
    }

    /**
     * Retourne la position de la nourriture.
     */
    public Point2D getPos() {
        return pos;
    }

}
