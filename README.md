# Sliding Puzzle Game

Program Version: 1.0

This Java school project, developed as part of the first-year engineering end-of-year project, is an implementation of a modified
Sliding Puzzle Game. In this game, the player needs to rearrange numbered tiles on a grid of any size by making moves. 
The goal is to place the tiles in ascending numerical order using the fewest number of moves.

## Features

1. **Graphical User Interface**: The Sliding Puzzle Game features a user-friendly graphical interface that displays the game grid and allows players to interact using simple interactions.

2. **Tile Movements**: Players can move adjacent tiles to the empty space by clicking on a valid adjacent tile. Tiles are swapped with the empty space, allowing for the rearrangement of the tiles.

3. **Random Shuffle**: At the start of the game, the tiles are randomly shuffled to create an initial game state. This ensures a different experience for each playthrough.

4. **Victory Validation**: The game automatically checks if the player has successfully arranged the tiles in numerical order. When all tiles are correctly placed, the player is notified of their victory.

5. **Variable Grid Size**: The Sliding Puzzle Game allows for the specification of the grid size, enabling gameplay with different grid configurations. The grid size can be modified before starting the game.

6. **Multiple Empty Spaces Handling**: The Sliding Puzzle Game also handles configurations with multiple empty spaces. Players can move tiles to any adjacent empty space.

## Installation and Execution

1. Clone the GitHub repository of the project: `git clone https://github.com/sarahbahhar/SLIDE.git`

2. Import the project into your preferred Java development environment (Eclipse, IntelliJ, etc.).

3. Compile the project to generate the `.class` files.

4. Execute the `Main` class to start the Sliding Puzzle Game.

5. Follow the instructions displayed in the graphical interface to play the Sliding Puzzle Game. Use mouse clicks to move adjacent tiles.

6. To modify the grid size, modify the corresponding parameter in the `Main` class before launching the game.

## Project Structure

The project is organized within the `puzzle` package:

- `puzzle`: Contains all the classes related to the Sliding Puzzle Game, including game logic, grid management, graphical interface, and utility classes.

## Contributors

This project was created by:

- Abdellah HASSANI
- Sarah BAHHAR
- Frank ZUO
- Simon REN
- Romain CASTELAO

If you would like to contribute to this project, you can follow these steps:

1. Fork the project repository.
2. Create a branch for your modifications: `git checkout -b my-branch`
3. Make your modifications and commit them: `git commit -am "Add my modifications"`
4. Push your changes to the remote repository: `git push origin my-branch`
5. Open a pull request to propose your modifications.



