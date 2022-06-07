# 2dgameproject
2d game project
# What is this Project?
- a 2d person vs. person hide and seek game
- one device represents the hider, the other device represents the seeker
- the hider's goal is to reach one of two exits before encountering the seeker
- the seeker's goal is to touch/"catch" the hider before they escape

# Game Mechanics

## Game Loop
- No game specific tools were used for this project, we only imported some related graphics/image buffer libraries
- had to write the game loop from scratch
- Makes sure to draw and update motions at least 60 times a second (60 fps)
 
## Keyboard Input
- used the keyhandler class to accept W,A,S,D keyboard input for movement
- translated these movements with key events and updated the player's position accordingly

## Player Movement
- player class has an attribute "speed" which is an integer value
- adding speed to the player's x and y location would effectively be how fast the player moves per update
- x and y variables to track player's location in the global map

## GUI/graphics
- imported graphics 2d in order to draw the player
- used in the player paint method to redraw player's image 60 times per second
- Used imagebuffer to read in the player images and tile images to draw

## Object Collisions
- imported the java rectangle class to represent the area of a player or tile
- algorithm to find collisions (current player position and tile position and intersect function of rectangle)
- every tile/object has a iscollidable boolean that is true if it is a solid object and false if it isn't

# Networking/Two Player Aspect
- Used Apache Tomcat as a host server
- IP address of the host server/device is required
- Basic Gist: Each player's movement is packaged as a "MoveEvent" which is turned into a json 
- the json is sent to the host/client (depending on where the moveevent originated from) to notify the other player of this player's movement
- the players' screens are updated accordingly with the location of the players correctly displayed

# Other

## Data Structures
- Arrays (to store interactable objects, powerups, tile names, etc)
- Matrices (to store the 2d map data, each cell is mapped to a particular tile to form the map)
- TreeMap (for interactable objects, the "key" is the location and the value is a String name for the object)

## Pixel Art and other References
- used pixilart.com for most of the pixel arts
- basic game design elements based heavily on RyiSnow's 2d game tutorials
