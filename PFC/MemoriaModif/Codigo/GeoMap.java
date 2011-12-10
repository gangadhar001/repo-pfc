JXMapKit jXMapKit = new JXMapKit();
// Configure maps provider
jXMapKit.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
jXMapKit.setAutoscrolls(true);
jXMapKit.setZoomButtonsVisible(false);

......

// Get the coordinates from server and set the wayPointer in the map
Coordinates coor;
coor = GeoCoder.getGeoCoordinates(company.getAddress());
double latitude = Double.parseDouble(coor.getLatitude());
double longitude = Double.parseDouble(coor.getLongitude());			        
position = new GeoPosition(latitude, longitude);

jXMapKit.setAddressLocation(position);    
Set<Waypoint> waypoints = new HashSet<Waypoint>();
waypoints.add(new Waypoint(latitude, longitude));			        

WaypointPainter<?> painter = new WaypointPainter();
painter.setWaypoints(waypoints);

jXMapKit.getMainMap().setOverlayPainter(painter);
jXMapKit.getMainMap().setZoom(2);
jXMapKit.setAddressLocationShown(true);
        