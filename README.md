# Reversi-Game-MiniMax-AI

## Overview

This repository contains a Java implementation of the classic board game Othello (Reversi). The game features a player-versus-computer mode where the AI opponent is powered by the MiniMax algorithm with Alpha-Beta pruning. This project demonstrates the application of game AI in a traditional board game setting.

## Features

- **Player vs AI**: Challenge an AI opponent that uses advanced algorithms to make strategic moves.
- **MiniMax Algorithm**: The AI utilizes the MiniMax algorithm enhanced with Alpha-Beta pruning to efficiently evaluate possible game states and choose the optimal move.
- **Customizable AI Difficulty**: Players can set the depth of the AI's search, adjusting the level of challenge.
- **Heuristic Evaluation**: The AI assesses board states based on piece count, corner control, and edge control to make decisions.

## Project Structure

### Main.java
- The entry point of the game. Manages game flow, user interactions, and alternates turns between the player and the AI.

### Board.java
- Represents the game board as an 8x8 grid. Handles game state management, move validation, and board updates after each move.

### Computer.java
- Implements the AI player using the MiniMax algorithm with Alpha-Beta pruning. Determines the best move by evaluating possible future states.

### Move.java
- A simple class representing a move in the game, including its coordinates and evaluated value.

## How to Play
- Choose whether you want to play first and set the depth for the AI's search algorithm (1 to 10).
- Play by making moves on the board. The AI will make its moves in response.
- The game ends when no more valid moves are available for either player.

