# VKTetris by Victoria Tran and Kailie Chang
# CPSC 254-05
------------

# About this Project
•This is an open-source project in which we modified an existing Tetris program written in Java. Existing project link: (https://gist.github.com/DataWraith/5236083)

# Goals of the Project
- Add Start page
- Change controls
- Add options to adjust the difficulty of the game
- Add a game over

# Contents
- Readme
- *Documents folder*: Project Proposal
- *Source*: source code of project
- *License*: BSD License

# Setup & Installation
*IMAGE SETUP*\
• Download the following images: gameOver.gif, howTo.png, tet.gif\
• Go into the Source file --> VKTetris.java and change the location address of the images

Ex: ImageIcon gameOver = new ImageIcon("CHANGE ADDRESS OF IMAGE HERE/gameOver.gif");

•*Line 191: change address for gameOver.gif*\
•*Line 281: change address for tet.gif*\
•*Line 285: change address for howTo.png*

*LINUX SET UP*: If you want to run this on a Linux distro... \
•Download a Java JDK in the terminal with the command: 
sudo apt-get install openjdk-7-jdk\
•After downloading the Java JDK, compile the java file with the command: javac VKTetris.java\
•Lastly, run class files with command: java VKTetris
