var randomScalingFactor = function() {
	return Math.round(Math.random() * 100)
};

var barChartData = {
	labels: ["1", "2", "3", "4", "5", "6", "7"],
	datasets: [{
		fillColor: "rgba(198,110,83,1)",
		strokeColor: "rgba(220,220,220,0.8)",
		highlightFill: "rgba(220,220,220,0.75)",
		highlightStroke: "rgba(220,220,220,1)",
		data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
	}, {
		fillColor: "rgba(151,187,205,0.5)",
		strokeColor: "rgba(151,187,205,0.8)",
		highlightFill: "rgba(151,187,205,0.75)",
		highlightStroke: "rgba(151,187,205,1)",
		data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
	}]

}
window.onload = function() {
	var ctx = document.getElementById("barchart").getContext("2d");
	window.myBar = new Chart(ctx).Bar(barChartData, {
		responsive: true
	});
}