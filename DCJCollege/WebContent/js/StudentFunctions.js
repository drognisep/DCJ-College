var anchorKeys = [
	"Register for Courses",
	"Add/Drop a Course",
	"Request Transcript",
	"Pay Fees"
];

window.onload = function() {
	var as = document.querySelectorAll("nav.top-nav > a");
	console.log("DEBUG: Attempting to traverse through anchors in top-nav");
	for(var i = 0; i < as.length && i < anchorKeys.length; i++) {
		console.log("  - In iteration: " + i);
		as[i].innerHTML = anchorKeys[i];
		console.log("    Key is set");
	}
	console.log("DEBUG: Out of loop");
	
	console.log("DEBUG: Hiding all hidden divs with keys");
	for(var i = 0; i < anchorKeys.length; i++) {
		var div = document.getElementById("div"+i);
		if(div) {
			console.log("DEBUG: Hiding div" + i);
			div.style.display = "none";
		} else {
			console.log("ERROR: div" + i + " was not found!");
		}
	}
	console.log("DEBUG: Done!");
};

/**
 * Shows the appropriate view using the anchor's innerHTML as the key.
 * @param a Reference to an anchor tag.
 */
function showFunction(a) {
	console.log("DEBUG: Running dummy showFunction(a) with parameter: " + a.innerHTML);
	// TODO: Implement showFunction(a) in StudentFunctions.js
	var key = a.innerHTML;
	var ikey = -1;
	
	for(var i = 0; i < anchorKeys.length; i++) {
		if(key === anchorKeys[i]) ikey = i;
	}
	
	for(var i = 0; i < anchorKeys.length; i++) {
		var div = document.getElementById("div"+i);
		if(div) {
			if(i == ikey) {
				console.log("DEBUG: Showing div" + i);
				div.style.display = "block";
			} else {
				console.log("DEBUG: Hiding div" + i);
				div.style.display = "none";
			}
		} else {
			console.log("ERROR: div" + i + " was not found!");
		}
	}
}