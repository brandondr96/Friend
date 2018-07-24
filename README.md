# Friend
Simple attempt at making a virtual friend.

### Interactions
The text box is used both as the interface for talking to the bot, and also as a command line. The following commands are seen as valid:
* --clear  : Clears the screen of the text box
* --reset  : Clears the current memory of the bot, as well as the screen of the text box
* --init   : Initializes the simple bot's speech data
* --test   : Switches the bot to the developmental test mode
* --simple : Switches the bot to the default simple response mode
Any other input will be counted as speech. This implies that the bot will both respond to it and use it to learn your speech patterns.

### Parameters
It is possible to edit various parameters within the code to make the developmental bot more accurate, however these have not yet been made user-friendly and are in progress.
