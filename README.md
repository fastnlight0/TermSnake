# TermSnake
## Setup

At the top of the TermSnake.java file, there are some variables you can edit to modify your game. Here are the settings you can modify:

|     Variable      |                     What it controls                      |    Default value     |
| :---------------: | :-------------------------------------------------------: | :------------------: |
|  startingLength   |                 Starting length of snake                  |          5           |
| startingPosition  | Starting position of snake on board (see [Board](#board)) | Middle of board (44) |
| startingDirection |    Starting direction for snake (see [Snake](#snake))     |      Right (3)       |
|    numOfApples    |           Number of apples on screen at a time            |          3           |


## Starting position info
### Board
The board looks like this. Each square is represented by a number. These numbers are used for the starting position of the snake (see [Setup](#setup)). Here is the board:
```
 0  1  2  3  4  5  6  7  8  9
10 11 12 13 14 15 16 17 18 19
20 21 22 23 24 25 26 27 28 29
30 31 32 33 34 35 36 37 38 39
40 41 42 43 44 45 46 47 48 49
50 51 52 53 54 55 56 57 58 59
60 61 62 63 64 65 66 67 68 69
70 71 72 73 74 75 76 77 78 79
80 81 82 83 84 85 86 87 88 89
90 91 92 93 94 95 96 97 98 99
```
### Snake
The snake has 4 starting directions. They are represented by numbers. These numbers are used for the starting direction of the snake (see [Setup](#setup)). Here are the directions:
| Direction | Number |
| :-------: | :----: |
|    Up     |   0    |
|   Down    |   1    |
|   Left    |   2    |
|   Right   |   3    |

## Playing
### Running the program
To run with default settings, execute the jar file with:
```
java -jar TermSnake.jar
```
If you modified the settings, run (or compile) TermSnake.java.

### Controls
The controls are simple. Arrow keys to control movement, escape to end the game. You can also end the game from the "Game" menu in the menu bar.

## Bugs
If you experience, please open an issue so I can get it resolved.