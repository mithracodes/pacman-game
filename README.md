# pacman-game

## Game Overview

The player controls PacMan, the infamous yellow main character, through an enclosed maze. To win, move around the walls, eat all the dots in the maze, and avoid the ghosts! In the second level, the player has the ability to attack the ghosts when they eat a pellet and the ability to gain bonus points when they eat a cherry.

The game features two levels: Level 0 and Level 1, both of which are mazes. In Level 0, the player will be able to control PacMan who has to move around the walls, eat the dots, and avoid the red ghosts, who are stationary. If the player collides with a ghost, the player will lose a life. The player has 3 lives in total. To finish the level, the player needs to eat (collide) with all the dots. If the player loses all 3 lives, the game ends. 

When the player finishes Level 0, Level 1 starts. To win the level and the game, the player must reach a score of 800. However, the player has to deal with 4 types of ghosts (red, blue, green & pink). The ghosts will be moving in different directions (as explained later). If the player collides with the pellet, the game goes into a frenzy mode, where the player can gain extra points when colliding with the ghosts and not lose any lives. The player can also gain more points if they collide with a cherry. Outside of the frenzy mode, if the player collides with a ghost, they will lose a life, and losing all 3 lives means the game will end.

## The Game Engine

The Basic Academic Game Engine Library (Bagel) is a game engine that was used to develop this game. You can find the documentation for Bagel [here](https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/).

### Coordinates

Every coordinate on the screen is described by an (x, y) pair. (0, 0) represents the top-left of the screen, and coordinates increase towards the bottom-right. Each of these coordinates is called a pixel. The Bagel Point class encapsulates this.

### Frames

Bagel will refresh the program’s logic at the same refresh rate as your monitor. Each time, the screen will be cleared to a blank state, and all of the graphics are drawn again. Each of these steps is called a frame. Every time a frame is to be rendered, the update() method in ShadowPac is called. 

## Game Entities

![oosdgit1](https://github.com/mithracodes/pacman-game/assets/95140934/a652562f-626c-4202-969b-bd526adbf3d6)
![oosdgit2](https://github.com/mithracodes/pacman-game/assets/95140934/16fdb573-8a01-43cd-b6d7-415fa80095cd)

## Screenshots from the game

![image](https://github.com/mithracodes/pacman-game/assets/95140934/ee2f4725-adc8-4cba-9a1d-0d6f8bdbab67)
![image](https://github.com/mithracodes/pacman-game/assets/95140934/28ea64d0-7b78-4899-b7bc-5a54c5cd3485)

** Note: This is my submission for *Project 1 & 2 of SWEN20003 Object Oriented Software Development in Sem 1 2023*. **
