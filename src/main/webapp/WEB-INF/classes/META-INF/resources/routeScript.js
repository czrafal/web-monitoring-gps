var map = L.map('map').setView([48.517,18.255], 5);

L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

var url = window.location.search.split('selectedIdRoute=')[1];
console.log(url)

var iconWaypoint = L.icon({
    iconUrl: 'http://localhost:8080/images/icon_black.png',
    iconSize: [16, 16]
});

var geojsonMarkerOptions = {
	    radius: 5,
	    color: "#000",
	    opacity: 2,
	    width: 5
};

var jsonLayer;
$.getJSON("http://localhost:8080/rest/route/"+url, function(data){
		jsonLayer = L.geoJson(data, {
		    //style: myStyle,
		    onEachFeature: onEachDot,
		    pointToLayer: function(feature, latlng) {
		    	//marker = L.circleMarker(latlng, geojsonMarkerOptions);
		    	marker = L.marker(latlng, {icon: iconWaypoint, rotationAngle: feature.properties.id});
		    	/*marker.bindPopup('<table style="width:180px"><tbody><tr><td><div><b>Pojazd:</b></div></td><td><div>' + feature.properties.Name + '</div></td></tr><tr class><td><div><b>Status:</b></div></td><td><div>' + feature.properties.status + '</div></td></tr><tr class><td><div><b>Współrzędne:</b></div></td><td><div>' + feature.geometry.coordinates + '</div></td></tr></tbody></table>');
		        marker.on('mouseover', function (e) {
		            this.openPopup();
		        });
		        marker.on('mouseout', function (e) {
		            this.closePopup();
		        });*/
				return marker;
			    },
	   })
	   map.fitBounds(jsonLayer.getBounds());
	   jsonLayer.addTo(map);
	   
	   spiralCoords = connectTheDots(jsonLayer);
	   L.polyline(spiralCoords).addTo(map)
	   
});

function onEachDot(feature, layer) {
    layer.bindPopup('<table style="width:180px"><tbody><tr><td><div><b>Pojazd:</b></div></td><td><div>' + feature.properties.Name + '</div></td></tr><tr class><td><div><b>Status:</b></div></td><td><div>' + feature.properties.status + '</div></td></tr><tr class><td><div><b>Współrzędne:</b></div></td><td><div>' + feature.geometry.coordinates + '</div></td></tr></tbody></table>');
}

function connectTheDots(data){
    var c = [];
    for(i in data._layers) {
        var x = data._layers[i]._latlng.lat;
        var y = data._layers[i]._latlng.lng;
        c.push([x, y]);
    }
    return c;
}

var myStyle = {
		 color: 'red',
         weight: 10,
         opacity: .7,
         dashArray: '20,15',
         lineJoin: 'round'
};