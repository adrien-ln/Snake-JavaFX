package snakebattle.view;

import java.util.function.BiConsumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Classe modélisant la vue du menu principal.
 */
public class MenuView extends VBox {
    private Button playButton;
    private ChoiceBox<String> modeChoice;
    private Slider aiCountSlider;

    private EventHandler<MouseEvent> playButtonHandler;
    private EventHandler<MouseEvent> onPlayButtonClicked;

    /**
     * Constructeur de la classe MenuView.
     */
    public MenuView() {
        // ChoiceBox pour le choix du mode de jeu
        Label labelPlayerModes = new Label("Modes de jeu");
        modeChoice = new ChoiceBox<>();
        modeChoice.getItems().addAll("1 joueur (souris)", "2 joueurs (souris + clavier ZQSD)", "2 joueurs (ZQSD + touches directionnelles)");
        modeChoice.setValue("1 joueur (souris)");

        // Slider pour le choix du nombre d'IA
        Label aiCountLabel = new Label("Nombre d'IA : 1");
        aiCountSlider = new Slider(0, 6, 1); // Les valeurs minimales, maximales et la valeur par défaut
        aiCountSlider.setShowTickLabels(true);
        aiCountSlider.setShowTickMarks(true);
        aiCountSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateAiCountLabel(newValue.intValue());
            }

            private void updateAiCountLabel(int value) {
                aiCountLabel.setText("Nombre d'IA : " + value);
            }
        });

        createPlayButton();
        setSpacing(20);
        // Ajout des composants au panneau principal
        getChildren().addAll(labelPlayerModes, modeChoice, aiCountLabel, aiCountSlider, playButton);
        setAlignment(Pos.CENTER);
    }

    /**
     * Crée le bouton pour lancer le jeu.
     */
    private void createPlayButton() {
        playButton = new Button("Jouer");
        playButton.getStyleClass().add("menu-button");
        playButtonHandler = event -> {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if (onPlayButtonClicked != null) {
                    onPlayButtonClicked.handle(event);
                }
            }
        };
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, playButtonHandler);
    }

    public void setOnPlayButtonClicked(EventHandler<MouseEvent> handler) {
        this.onPlayButtonClicked = handler;
    }

    /**
     * Retourne le mode de jeu choisi.
     */
    public int getModeChoice() {
        if (modeChoice.getValue().equals("1 joueur (souris)")) {
            return 1;
        } else if (modeChoice.getValue().equals("2 joueurs (souris + clavier ZQSD)")) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Retourne le nombre d'IA choisi.
     */
    public int getAiCount() {
        return (int) aiCountSlider.getValue();
    }
}
