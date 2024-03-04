# Presentation

Snake Battle is a program inspired by the famous games Snakes and Slither.io. The goal of the game is to have the longest tail among all the snakes in the game. Players can increase the length of their snake's tail by consuming food pellets. They can also eliminate opponent snakes by attempting to encircle them.

### Gameplay

Upon launching the program, players have the choice between three game modes:
- **Solo mode** (snake controlled by **mouse**),
- **Two-player mode**: each player controls their snake respectively with **mouse** and **ZQSD** keys,
- **Two-player mode**: each player controls their snake respectively with **arrow keys** and **ZQSD** keys.

Players can also choose the number of AI snakes. Once the parameters are selected, the user presses the "Play" button to start the game.

### Implementation Choices

This program follows the MVC (Model-View-Controller) design pattern. JavaFX toolkit is used for the graphical interface of the game (window, menu and game scenes, snakes, food). When a game is instantiated and started, checks are performed every frame to handle various events affecting the gameplay (movements, collisions, etc.).

# Installation
- Clone the repository:
```
git clone https://github.com/adrien-ln/snake-javafx.git
```
```
git clone git@github.com:adrien-ln/snake-javafx.git
```

- Navigate to the project directory: `cd snake-javafx`

- Execute gradle wrapper:
```
./gradlew build
```
- Start the game:
```
./gradlew run
```
