<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="corpo" class="container" style="background-color: #FFF;">
    <div>
        <div class="col-md-12">
            <br />
            <h1>Dashboard</h1>
        </div>
        <div class="row">
            <div class="col-md-4">
                <canvas id="myChart" width="300" height="200"></canvas>
            </div>
            
            <div class="col-md-4">
                <canvas id="myChart2" width="300" height="200"></canvas>
            </div>
            
            <div class="col-md-4">
                <canvas id="myChart3" width="300" height="200"></canvas>
            </div>
        </div>
        
        <div class="row"></div>
    </div>
</div>

<script>
    var ctx = document.getElementById("myChart").getContext("2d");
    var ctx2 = document.getElementById("myChart2").getContext("2d");
    var ctx3 = document.getElementById("myChart3").getContext("2d");
    
    var data = [
    {
        value: 300,
        color:"#F7464A",
        highlight: "#FF5A5E",
        label: "Red"
    },
    {
        value: 50,
        color: "#46BFBD",
        highlight: "#5AD3D1",
        label: "Green"
    },
    {
        value: 100,
        color: "#FDB45C",
        highlight: "#FFC870",
        label: "Yellow"
    },
    {
        value: 40,
        color: "#949FB1",
        highlight: "#A8B3C5",
        label: "Grey"
    },
    {
        value: 120,
        color: "#4D5360",
        highlight: "#616774",
        label: "Dark Grey"
    }

];
    
    var data2 = {
    labels: ["January", "February", "March", "April", "May", "June", "July"],
    datasets: [
        {
            label: "My First dataset",
            fillColor: "rgba(255,0,0,0.3)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(255,0,0,0.3)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
            label: "My Second dataset",
            fillColor: "rgba(151,187,205,0.5)",
            strokeColor: "rgba(151,187,205,1)",
            pointColor: "rgba(151,187,205,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(151,187,205,1)",
            data: [28, 48, 40, 19, 86, 27, 90]
        }
    ]
};

var data3 = {
    labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
    datasets: [
        {
            label: "My First dataset",
            fillColor: "rgba(255,0,0,0.3)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(255,0,0,0.3)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [65, 59, 90, 81, 56, 55, 40]
        },
        {
            label: "My Second dataset",
            fillColor: "rgba(151,187,205,0.5)",
            strokeColor: "rgba(151,187,205,1)",
            pointColor: "rgba(151,187,205,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(151,187,205,1)",
            data: [28, 48, 40, 19, 96, 27, 100]
        }
    ]
};
    
    var myNewChart = new Chart(ctx).PolarArea(data);    
    var myLineChart = new Chart(ctx2).Line(data2);
    var myRadarChart = new Chart(ctx3).Radar(data3);
</script>