package snakebattle.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;

/**
 * Classe modélisant le terrain du jeu.
 */
public class Terrain {

    private double largeur; // Largeur du terrain
    private double hauteur; // Hauteur du terrain
    private int aiNumber; //nombre d'Ia
    private LinkedList<Snake> playerSnakes;
    private LinkedList<Snake> iaSnakes;
    private LinkedList<Food> foodsPositions;

    /**
     * Constructeur de la classe Terrain. 
     * Initialise les serpents et la nourriture.
     * @param largeur La largeur du terrain.
     * @param hauteur La hauteur du terrain.
     * @param player1 Le joueur 1.
     * @param player2 Le joueur 2.
     */
    public Terrain(double largeur, double hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.playerSnakes = new LinkedList<>();
        this.iaSnakes = new LinkedList<>();
        this.foodsPositions = new LinkedList<>();
        initFoods(50);
    }

    /**
     * Ajoute un joueur au terrain.
     * @param player1
     */
    public void addPlayer(Player player) {
        this.playerSnakes.add(player.getSnake());
    }

    /**
     * Initialise les serpents IA du terrain.
     * @param aiNumber
     */
    public void initAi(int aiNumber){
        this.aiNumber = aiNumber;
        for(int i = 0; i < aiNumber; i++) {
            this.iaSnakes.add(new Snake(false));
        }
    }

    /**
     * Initialise la nourriture du terrain.
     * @param nbFoods quantité de nourriture à initialiser.
     */
    public void initFoods(int nbFoods) {
        for (int i = 0; i < nbFoods; i++) {
            generateFood();
        }
    }
    
    /**
     * Génère une nouvelle position aléatoire pour un Food.
     */
    public void generateFood() {
        Food newFood = new Food(randomPosition());
        addFood(newFood);
    }

    /**
     * Ajoute un Food à la liste des Food du terrain.
     * @param food Le Food à ajouter.
     */
    public void addFood(Food food) {
        foodsPositions.add(food);
    }

    /**
     * Supprime un Food de la liste des Food du terrain.
     * @param food Le Food à supprimer.
     */
    public void removeFood(Food food) {
        foodsPositions.remove(food);
    }

    /**
     * Retourne la liste des Food du terrain.
     */
    public List<Food> getFoods() {
        return foodsPositions;
    }

    /**
     * Retourne une position aléatoire sur le terrain.
     */
    private Point2D randomPosition() {
        double randomX = Math.random() * this.largeur;
        double randomY = Math.random() * this.hauteur;
        return new Point2D(randomX, randomY);
    }

    /**
     * Retourne la largeur du terrain.
     */
    public double getLargeur() {
        return largeur;
    }

    /**
     * Retourne la hauteur du terrain.
     */
    public double getHauteur() {
        return hauteur;
    }

    /**
     * Retourne la liste des serpents IA du terrain.
     */
    public LinkedList<Snake> getIaSnakes() {
        return iaSnakes;
    }

    /**
     * Retourne la liste des serpents joueurs du terrain.
     */
    public LinkedList<Snake> getPlayerSnakes() {
        return playerSnakes;
    }

}
