# How to start the game
This is a command line game

To start the game: run main from Simulator class

Afterwards, simply follow the instruction from console

1. enter the name of each player
2. input player name who wins the point each time, the score will be printed
3. game will continue if there is no winner, game will end if there is a winner

# Implementation run through
Simulator class is the console application which is responsible for interaction with player and run through the game

Match class has most of the business logic, exposes two pointWonBy and score features which will be used by Simulator class. 

# How to run unit test
Need to add hamcrest-2.2.har and junit-4.13.jar to dependency

Run MatchTest under test directory
