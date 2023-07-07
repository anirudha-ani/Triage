# Triage: 2D Game Engine Using Java

---
### Demo of the game built on this engine: https://youtu.be/Yx9lxA2dsVA
---

### Some sample games made on top of the same engine
![youtube-video-gif (1)](https://github.com/anirudha-ani/Triage/assets/13461833/ff9c3a89-9c0d-4943-9a44-0a0f630c284d)![youtube-video-gif (2)](https://github.com/anirudha-ani/Triage/assets/13461833/d8d5cd13-5c78-49fe-bc02-92e5b2697e2d)

![youtube-video-gif (3)](https://github.com/anirudha-ani/Triage/assets/13461833/2302da2f-615b-490e-af0c-7a46ccaa6e93)![youtube-video-gif (4)](https://github.com/anirudha-ani/Triage/assets/13461833/525162e4-41b6-46f7-8e55-c807537f6ac8)



## Description 

The project involves the creation of a 2D game engine from scratch, which is designed to support various features, including graphics rendering, physics simulation, audio processing, input handling, and artificial intelligence. It provides the necessary infrastructure for building the final game.

This was designed to be modular and scalable, with different components built to handle specific tasks. Suitable design patterns were followed to ensure that the engine's components were well-structured, reusable, and easy to maintain.
Some of the key components of the game engine include:

**Graphics Rendering:** This component is responsible for rendering 2D graphics on the screen, including sprites, backgrounds, and visual effects. For some parts, JavaFX was used, but most of the UI components, even buttons and their corresponding behavior, were designed from scratch (not the JavaFX library version).

**Physics Simulation:** This component handles the simulation of physical interactions between game objects, including collisions, movement, and gravity. The physics simulation component uses mathematical models to calculate the behavior of objects in the game world.

**Audio Processing:** This component handles the playback of sound effects and music in the game. It handled background music and also situational sound effects.

**Input Handling:** This component is responsible for handling user input through the keyboard and mouse. It had an input buffer system to properly handle multiple button presses and make overall control more responsive.

**Artificial Intelligence:** This component is responsible for controlling non-player characters (NPCs) in the game world. The AI component uses algorithms and decision-making processes to simulate intelligent behavior.

### This project is tested on this configuration -

1. Oracle OpenJDK version 11.0.16
2. JavaFX SDK 19
3. MacOS 12.2.1 Monterey
4. IntelliJ Idea 2022.2.2 Build #IU-222.4167.29, built on September 13, 2022

### Running the Game -

1. Open the project and make sure all the dependency is setup correctly
2. Run the Main.java file located in <rootDirectory>/triage/.
3. You can skip the cut scene by pressing any button
4. Press start to play the game
5. Buy the shuriken from the store

### Movement -

6. You can use W to jump and A and D for lateral movement
7. If the player is on platform it can jump
8. If the player is not on platform it can not jump but lateral movements work.

### Shooting-

9. To shoot - first aim by pressing mouse right click, then release the button to shoot projectile
10. To slash  - press L for slash attack



Playtest report is added to another file.
