var anchorKeys = [
	"Create New Course",
	"Add/Drop Instructor",
	"Alter Term Schedule",
	"Update Grades"
];

window.onload = function() {
	var as = document.querySelectorAll("nav.top-nav > a");
	console.log("as.length: " + as.length + ", anchorKeys.length: " + anchorKeys.length);
	for(var i = 0; i < as.length && i < anchorKeys.length; i++) {
		console.log("  - In iteration: " + i);
		as[i].innerHTML = anchorKeys[i];
		console.log("    Key is set");
	}
	
	for(var i = 0; i < anchorKeys.length; i++) {
		var div = document.getElementById("div"+i);
		if(div) {
			div.style.display = "none";
		} else {
			console.log("ERROR: div" + i + " was not found!");
		}
	}
};

/**
 * Shows the appropriate view using the anchor's innerHTML as the key.
 * @param a Reference to an anchor tag.
 */
function showFunction(a) {
	var key = a.innerHTML;
	var ikey = -1;
	
	for(var i = 0; i < anchorKeys.length; i++) {
		if(key === anchorKeys[i]) ikey = i;
	}
	
	for(var i = 0; i < anchorKeys.length; i++) {
		var div = document.getElementById("div"+i);
		if(div) {
			if(i == ikey) {
				div.style.display = "block";
			} else {
				div.style.display = "none";
			}
		} else {
			console.log("ERROR: div" + i + " was not found!");
		}
	}
}

/**
 * Hides all hideable div's
 */
function hideFunction() {
	for(var i = 0; i < anchorKeys.length; i++) {
		var div = document.getElementById("div"+i);
		if(div) {
			div.style.display = "none";
		}
	}
}