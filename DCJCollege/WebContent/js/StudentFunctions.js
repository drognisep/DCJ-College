var anchorKeys = [ "Register for Courses", "Add/Drop a Course",
		"Request Transcript", "Pay Fees" ];

window.onload = function() {
	var as = document.querySelectorAll("nav.top-nav > a");
	for ( var i = 0; i < as.length && i < anchorKeys.length; i++) {
		console.log("  - In iteration: " + i);
		as[i].innerHTML = anchorKeys[i];
		console.log("    Key is set");
	}

	for ( var i = 0; i < anchorKeys.length; i++) {
		var div = document.getElementById("div" + i);
		if (div) {
			div.style.display = "none";
		} else {
			console.log("ERROR: div" + i + " was not found!");
		}
	}
};

/**
 * Shows the appropriate view using the anchor's innerHTML as the key.
 * 
 * @param a Reference to an anchor tag.
 */
function showFunction(a) {
	if (a) {
		var key = a.innerHTML;
		var ikey = -1;

		for ( var i = 0; i < anchorKeys.length; i++) {
			if (key === anchorKeys[i])
				ikey = i;
		}

		for ( var i = 0; i < anchorKeys.length; i++) {
			var div = document.getElementById("div" + i);
			if (div) {
				if (i == ikey) {
					div.style.display = "block";
				} else {
					div.style.display = "none";
				}
			} else {
				console.log("ERROR: div" + i + " was not found!");
			}
		}
	} else {
		console.log("Invalid parameter: a");
	}
}

/**
 * Hides all hideable div's
 */
function hideFunction() {
	for ( var i = 0; i < anchorKeys.length; i++) {
		var div = document.getElementById("div" + i);
		if (div) {
			div.style.display = "none";
		}
	}
}

/**
 * Creates an async ajax request to update registration.
 * 
 * @param form
 */
function updateRegistration(form) {
	if (form) {
		console.log("Starting ajax request");
		var xhr = new XMLHttpRequest();
		var fname = form.fname.value;
		var lname = form.lname.value;
		var street = form.street.value;
		var city = form.city.value;
		var state = form.state.value;
		var zip = form.zip.value;
		var phone = form.phone.value;
		var reqType = "AJAX_UpdateRegistration";
		var reqOrigin = "StudentFunctions.jsp";

		var parameters = "fname=" + fname + "&lname=" + lname + "&street="
				+ street + "&city=" + city + "&state=" + state + "&zip=" + zip
				+ "&phone=" + phone + "&reqType=" + reqType + "&reqOrigin="
				+ reqOrigin;
		xhr.open('POST', '/DCJCollege/StudentServicesServlet', true); // true for async
		xhr.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xhr.send(parameters);

		xhr.onreadystatechange = function() {
			var DONE = 4;
			var OK = 200;
			if (xhr.readyState === DONE) {
				if (xhr.status === OK) {
					console.log("Response type: "
							+ (xhr.responseType === "" ? "text (corrected)"
									: xhr.responseType));
					var res = xhr.responseText;
					if (res == "Success!") {
						var a = document.getElementById("AddDropDiv");
						showFunction(a);
					}
				} else {
					alert("An error occurred processing AJAX request");
				}
			}
		};
	} else {
		console.log("Invalid form parameter");
	}
}

function updateCourseSections(emitter, target_id) {
	if (emitter && target_id) {
		console.log("Starting ajax request");
		var xhr = new XMLHttpRequest();
		// Expects a drop down list of course_id's
		var course_id = emitter.options[emitter.selectedIndex].text;
		var reqType = "AJAX_GetCourseSections";
		var reqOrigin = "StudentFunctions.jsp";

		var parameters = "course_id=" + course_id + "&reqType=" + reqType + "&reqOrigin="
				+ reqOrigin;
		xhr.open('POST', '/DCJCollege/StudentServicesServlet', true);
		xhr.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");

		xhr.onreadystatechange = function() {
			var DONE = 4;
			var OK = 200;
			if (xhr.readyState === DONE) {
				if (xhr.status === OK) {
					console.log("Response type: "
							+ (xhr.responseType === "" ? "text (corrected)"
									: xhr.responseType));
//					alert(xhr.responseText);
					var res = JSON.parse(xhr.responseText);
					var target = document.getElementById(target_id);
					if(target && res) {
						while(target.options.length > 0) {
							target.remove(target.options.length - 1);
						}
						for(var i = 0; i < res.length; i++) {
							var opt = document.createElement('option');
							opt.text = res[i].section_id;
							opt.value = res[i].section_id;
							target.add(opt, null);
						}
					} else {
						alert("Coding error: target is undefined");
					}
				} else {
					alert("An error occurred processing AJAX request");
				}
			}
		};
		
		xhr.send(parameters);
	} else {
		console.log("Invalid parameters!");
	}
}