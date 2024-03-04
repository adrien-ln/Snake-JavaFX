package snakebattle.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;

/**
 * Classe modélisant un serpent dans le jeu.
 */
public class Snake {

    private LinkedList<Point2D> tail; // Liste des positions de la queue du serpent
    private final int INITIAL_TAIL_LENGTH = 25; // Longueur initiale de la queue du serpent
    private double direction; // Direction actuelle du serpent
    private double vitesse; // Vitesse du serpent
    private boolean isPlayer; // Si le serpent est une IA ou non
    private String name; // Nom du serpent
    private boolean mort;

    /**
     * Constructeur de la classe Snake.
     * @param player Si le serpent est une IA ou non.
     */
    public Snake(boolean isPlayer) {
        Random random = new Random();
        int randomX = random.nextInt(1000); 
        int randomY = random.nextInt(1000);
        this.isPlayer = isPlayer;
        this.tail = new LinkedList<>();
        this.tail.add(new Point2D(randomX, randomY));

        // Ajoutez des points à la queue du serpent
        for(int i = 0; i < INITIAL_TAIL_LENGTH; i++) {
            Point2D last = this.tail.getLast();
            this.tail.add(new Point2D(last.getX(), last.getY()));
        }
        
        // Initialise la direction et la vitesse du serpent
        this.direction = random.nextInt(360);
        this.vitesse = 4;
        this.mort = false;
    }

    /**
     * Déplace le serpent.
     */
    public void move() {
        if(mort) return;
       // Calculez x et y du déplacement en fonction de la direction
       double deltaX = vitesse * Math.cos(Math.toRadians(direction));
       double deltaY = vitesse * Math.sin(Math.toRadians(direction));
       Point2D head = tail.getFirst();
   
       // Ajout de la nouvelle position à la tête du serpent
       Point2D newHead = new Point2D(head.getX() + deltaX, head.getY() + deltaY);

       // Si le serpent sort de l'écran, il réapparaît de l'autre côté
       int x = (int) newHead.getX();
       if(x < 0) {
           newHead = new Point2D(1000, newHead.getY());
       } else if(x > 1000) {
           newHead = new Point2D(0, newHead.getY());
       }

       int y = (int) newHead.getY();
       if(y < 0) {
           newHead = new Point2D(newHead.getX(), 1000);
       } else if(y > 1000) {
           newHead = new Point2D(newHead.getX(), 0);
       }
       // retire le dernier point t et ajoute la nouvelle tête
       tail.removeLast();
       tail.addFirst(newHead);
       head = newHead;
    }

    /**
     * Déplace le serpent dans une direction aléatoire.
     */
    public void moveRandom() {
        // Direction aléatoire dans l'interval [-30, 30] par rapport à la direction actuelle
        Random random = new Random();
        int randomChange = random.nextInt(60) - 30; // Valeur aléatoire dans la plage [-30, 30]
        // Ajoutez la valeur aléatoire à la direction actuelle
        direction = (direction + randomChange + 360) % 360;
        move();
    }

    /**
     * Augmente la taille du serpent.
     */
    public void grow() {
        Point2D last = this.tail.getLast();
        LinkedList<Point2D> newTail = new LinkedList<>(); // Créer une nouvelle liste
        newTail.add(new Point2D(last.getX(), last.getY()));
        this.tail.addAll(newTail); // Ajouter la nouvelle liste à la fin de la liste existante
    }

    /**
     * Modifie la direction du serpent en fonction d'une direction donnée.
     * @param Direction La direction souhaitée (Enum Direction).
     */
    public void setDirection(snakebattle.model.Direction d) {
        switch(d) {
            case UP:
                this.direction = 270;
                break;
            case DOWN:
                this.direction = 90;
                break;
            case LEFT:
                this.direction = 180;
                break;
            case RIGHT:
                this.direction = 0;
                break;
            default:
                break;
        }
    }

    /**
     * Modifie la direction du serpent en fonction de la position de la souris.
     * @param mouseX Position X de la souris.
     * @param mouseY Position Y de la souris.
     */
    public double setMouseDirection(double newDirection) {
        this.direction = newDirection;
        return newDirection;
    }

    /**
     * Vérifie la collision de la tête du serpent avec la queue de otherSnake
     * @param otherSnake L'autre serpent.
     * @param tolerance La distance minimale requise pour que la collision ait lieu.
     * @return Vrai s'il y a collision avec la queue, sinon faux.
     */
    public boolean checkCollisionWithSnake(Snake otherSnake, double tolerance) {
        if (this == null || otherSnake == null) return false;
        Point2D head = getHead();
        for (Point2D point : otherSnake.tail) {
            double distance = head.distance(point);
            // Utilisez une tolérance pour étendre la "hitbox"
            if (distance < tolerance) {
                this.mort = true;
                return true;
            }
        }return false;
    }

    /**
     * Vérifie la collision de la tête avec la queue du serpent.
     * @param minDistance La distance minimale requise entre la tête et les points suivants dans la liste.
     * @return Vrai s'il y a collision avec la queue, sinon faux.
     */
    public boolean checkCollisionWithSelf(double minDistance) {
        List<Point2D> tail = getTail();
        Point2D head = getHead();
        for (int i = 1; i < tail.size(); i++) {
            Point2D point = tail.get(i);
            double distance = head.distance(point);
            if (distance < minDistance) {
                return true;
            }
        }return false;
    }

    /**
     * Vérifie la collision de la tête avec la nourriture.
     * @param food La nourriture à vérifier.
     * @return Vrai s'il y a collision avec la nourriture, sinon faux.
     */
    public boolean checkCollisionWithFood(Food food) {
        Point2D head = getHead();
        double distance = head.distance(food.getPos());
        if (distance < 10) {
            return true;
        }
        return false;
    }

    /**
     * Retourne la tête du serpent (premier point de la liste).
     */
    public Point2D getHead() {
        return tail.getFirst();
    }

    /**
     * Modifie l'état de mort du serpent.
     * @param b Le nouvel état de mort du serpent.
     */
    public void setMort(boolean b) {
        this.mort = b;
        this.tail.clear();
    }

    /**
     * Retourne si le serpent est mort ou non.
     */
    public boolean isMort() {
        return this.mort;
    }

    /**
     * Modifie le nom du serpent.
     * @param name Le nouveau nom du serpent.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le nom du serpent.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retourne si le serpent est un joueur ou non.
     */
    public boolean isPlayer() {
        return this.isPlayer;
    }

    /**
     * Retourne la liste des positions de la queue du serpent.
     */
    public LinkedList<Point2D> getTail() {
        return tail;
    }

}
