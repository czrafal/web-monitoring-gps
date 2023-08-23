var map = L.map('map').setView([48.517,18.255], 7);
var trackCarRegNum = null;
var jsonLayer = null;
var line = null;

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '<a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

var shipLayer = L.layerGroup();
var routeMarkersLayer = L.layerGroup();
var routeLineLayer = L.layerGroup();

var markerToTracking = null;
var group2;
var group1;
var realtime1;

var iconCarGreen = L.icon({
    iconUrl: 'http://localhost:8080/images/car.jpg',
    iconSize: [30, 30]
});

var iconWaypoint = L.icon({
    iconUrl: 'http://localhost:8080/images/icon_black.png',
    iconSize: [16, 16]
});

var geojsonMarkerOptions = {
	    radius: 5,
	    color: "#000"
};
	
var realtime = L.realtime({
    url: 'http://localhost:8080/realMap/',
    crossOrigin: true,
    type: 'json'
}, {
    interval: 5 * 1000,
    getFeatureId: function(featureData){
    	return featureData.properties.Name;
    },
    pointToLayer: function (feature, latlng) {
      marker = L.marker(latlng, {icon: iconCarGreen});
      marker.bindPopup('Name: ' + feature.properties.Name);
      marker.bindTooltip(feature.properties.Name, {permanent: true, direction: 'bottom', className: 'regNumLabel'});
      marker.addTo(shipLayer);
      return marker;
    }
});

realtime.addTo(map);

$(window).bind('resize', function(){
    console.log('resized');
    //window.setTimeout(function() { // centruje obiekt ale puste elementy przez sekunde
        map.invalidateSize(true);
       // shipLayer.resize();
    //}, 1000);
});

//map.fitBounds(realtime.getBounds());

function viewLastRoute(lat, lon, idRoute, watch) {
	if (Boolean(watch) == false) {
		resetMap();
		setTimeout(function() {}, 2000);
	} else if (realtime1) {
		resetMap();
		setTimeout(function() {}, 2000);
		addRouteToLayer(idRoute);
	} else {
		console.log("add new route");
		addRouteToLayer(idRoute);
	}
	map.setView([lat, lon], 14);
}

function moveMap(lat, lon) {
	map.setView([lat, lon], 14);
}

function resetMap() {
	realtime1.stop();
	//group1.clearLayers();
	realtime1.clearLayers();
	map.removeLayer(group2);
	//map.removeLayer(group1);
}

function addRouteToLayer(idRoute) {
    console.log("addRouteToLayer url :"+'http://localhost:8080/rest/route/'+idRoute);
	realtime1 = L.realtime({
	    url: 'http://localhost:8080/rest/route/'+idRoute,
	    crossOrigin: true,
	    type: 'json'
	}, {
	    interval: 5 * 1000,
	    getFeatureId: function(f) {
	    	console.log("update feature properties:"+f.properties.id);
	        return f.properties.id;
	    },
	    pointToLayer: function (feature, latlng) {
	        //marker = L.circleMarker(latlng, geojsonMarkerOptions);
	    	marker = L.marker(latlng, {icon: iconWaypoint});
	    	//marker.setRotationAngle(180);
	    	marker.bindPopup('<table style="width:180px"><tbody><tr><td><div><b>Pojazd:</b></div></td><td><div>' + feature.properties.Name + '</div></td></tr><tr class><td><div><b>Status:</b></div></td><td><div>' + feature.properties.status + '</div></td></tr><tr class><td><div><b>Współrzędne:</b></div></td><td><div>' + feature.geometry.coordinates + '</div></td></tr></tbody></table>');
	        marker.on('mouseover', function (e) {
	            this.openPopup();
	        });
	        marker.on('mouseout', function (e) {
	            this.closePopup();
	        });
	        return marker;
	      },

	     /* onEachFeature: function(feature, layer) {
	    	  console.log("point:"+feature.geometry.coordinates);
	      }   */	
	}).addTo(map);

	realtime1.on('update',function(e){
		var features = [];
		var c = [];

		if (group2) {
			map.removeLayer(group2);
		}

		Object.keys(e.features).sort().forEach(function(key) {
			  features.push(e.features[key]);
		});
		 
		 for (i = 0; i < features.length; i += 1) {
		    	console.log("connectDots loop i:"+i);
		        feature = features[i];
		        // Make sure this feature is a point.
		        if (feature.geometry.type === "Point") {
		        	console.log("connectDots push point:"+feature.geometry.coordinates[1] +" "+feature.geometry.coordinates[0]);
		            c.push([feature.geometry.coordinates[1], feature.geometry.coordinates[0]]);
		        }
		    }
		group2 = L.polyline(c).addTo(map).bringToBack();
		//L.polyline(c).addTo(routeLineLayer).bringToBack();
	});

	//group1 = realtime1.addTo(map);

/*	group2 = L.featureGroup([
		routeLineLayer
	]).addTo(map);
	group1.bringToFront();*/

}

/*	L.control.layers(null, {
	    'Earthquakes 2.5+': realtime1
	}).addTo(map);
*/