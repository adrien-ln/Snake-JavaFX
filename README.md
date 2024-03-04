# Présentation

Snake Battle est un programme inspiré du célèbre jeu Slither.io
Le but du jeu est d'avoir le serpent de la partie ayant la plus longue queue.
Les joueurs peuvent agrandir la queue de leur serpent en passant sur des points représentant de la nourriture.
Ils peuvent également tuer les serpents adverses en essayant d'effectuer des queues de poissons aux autres joueurs / IA.

### Déroulement

Lors du lancement du programme, vous avez le choix entre trois modes de jeu : 
- **mode solo** (serpent contrôlé par **la souris**),
- **mode deux joueurs** : serpents des joueurs contrôlés respectivement par **la souris** et les touches du clavier **ZQSD**,
- **mode deux joueurs** : serpents des joueurs contrôlés respectivement par les **touches directionnelles** et les touches du clavier **ZQSD**.

Vous pouvez également choisir le nombres de serpents IA.
Une fois les paramètres sélectionnés, l'utilisateur appuie sur le bouton "Jouer" pour lancer la partie.

### Choix d'implémentation

Ce programme utilise le patron MVC (Modèle-Vue-Contrôleur). Nous avons utilisé le toolkit javafx pour l'instance graphique du jeu (fenêtre, scènes du menu et du jeu, serpents, nourriture).
Lorsqu'une partie est instanciée et démarrée, des vérifications sont faites chaque nouvelle frame pour traiter les différents évènements agissant sur
le déroulement du jeu (déplacements, collisions etc...).

# Auteurs
- Adrien LE NINIVEN 
- CAGNION Romain

# Installation
 - Cloner le dépôt :
```
git clone https://gaufre.informatique.univ-paris-diderot.fr/leninive/projet-cpoo.git
```
```
git clone git@gaufre.informatique.univ-paris-diderot.fr:leninive/projet-cpoo.git
```

- Aller dans le répertoire du projet : `cd projet-cpoo`

- Exécuter gradle wrapper 
 :
```
./gradlew build
```
- Démarrer le jeu
```
./gradlew run
```