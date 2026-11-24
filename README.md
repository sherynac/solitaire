# Klondike Solitaire Solver

## About the Project
Solitaire is a singleplayer game wherein the player has to build ascending suit sequences in the foundation zone.
This program serves as an autosolver of classic Solitaire with draw 1 or draw 3 rules with a winrate of 44.2% for Perfect In Faro Shuffles

## Key Terms

### Talon
Refers to the group of piles used for drawing and cycling through cards, located in the upper-left area of the Solitaire table. 

### Stock
Part of the talon, refers to the face-down draw pile

### Waste
Part of the talon, refers to the pile containing cards drawn from the Stock

### Foundation
Refers to the four piles each representing a suit. Cards are to be placed here in ascending order according to suit

### Maneuver
Refers to the main playing area consisting of seven columns where cards are arranged in descending rank order and alternating colors.

## Installation
1. Clone the repository
2. Run `mvn clean package` to generate a .jar file
3. Change directory to where the .jar file was generated
4. Run .jar file using `java -jar solitaire-1.0-SNAPSHOT.jar`

## Mechanics
1. Game has 7 maneuver stacks of increasing length, 4 foundation stacks and talon
2. In the Maneuver zone, form descending sequences of alternating color. Multiple cards forming a run can be moved at once
3. At any time, flip cards from the talon and where possible move them into the maneuver or foundation zone.
4. If a maneuver stack becomes empty, a King can be moved to it
5. Unlimited talon cycles
6. Draw One and Draw Three options for how many cards are flipped from the talon at a time

## Algorithm Guide
*Followed a greedy approach to execute the best possible move at the current game state. Priority of Moves was based on the following:*

1. Place cards to foundation if it is safe
     - Considered safe if both cards of opposite color and one rank lower is in the foundation
2. Moves that reveal a hidden card
     - Stacks with more hidden cards are prioritized
3. Moves that create empty columns
4. Adding cards from the talon
      - Bonus is given for cards that have follow-up moves in the board
5. Moving unsafe cards from talon to foundation
6. Placing unsafe cards with no reveal from maneuver to foundation
7. Draw from stock
8. Recycle talon