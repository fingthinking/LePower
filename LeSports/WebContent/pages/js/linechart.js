var randomScalingFactor = function() {
	return Math.round(Math.random() * 100)
};
var lineChartData = {
	labels: ["0", "2",  "4", "6", "8","10", "12", "14", "16", "18", "20", "22","24"],
	datasets: [{
		label: "步数",
		fillColor: "rgba(198,110,83,0.2)",
		strokeColor: "rgba(198,110,83,1)",
		pointColor: "rgba(198,110,83,1)",
		pointStrokeColor: "#fff",
		pointHighlightFill: "#fff",
		pointHighlightStroke: "rgba(198,110,83,1)",
		data : [65,59,90,81,56,55,40,20,70,100,40,60,80]
	}]

}

window.onload = function() {
	var ctx = document.getElementById("linechart").getContext("2d");
	window.myLine = new Chart(ctx).Line(lineChartData, {
		responsive: true
	});
}
