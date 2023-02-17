// Resize is used to define some values related to resizing
enum Resize {
	BOTTOMRIGHT, // Can Resize from Bottom Right Corner 
	BOTTOMLEFT, // Can Resize from Bottom Left Corner
	TOPRIGHT, // Can Resize from Top Right Corner
	TOPLEFT, // Can Resize from Top Left Corner
	TOP, // Can Resize from the Top Border
	BOTTOM, // Can Resize from the Bottom Border
	RIGHT, // Can Resize from the Right Border
	LEFT, // Can Resize from the Left Border
	INVALID // Cannot Resize from this position
}