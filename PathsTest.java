import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class PathsTest{

	@Test
	public void addCities_adds_all_cities_to_paths_hashMap(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		for(String city: cities) {
			assertEquals(p.pathMap.containsKey(city), true);
			assertEquals(p.pathMap.get(city), new ArrayList<String>());
		}
	}
	
	@Test
	public void setPath_adds_all_direct_paths_to_map(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		String [] routes = {"Bangalore","Singapore"};
		Path p = new Path(cities);
		p.setPath(routes[0],routes[1]);
		p.setPath(routes[1],routes[0]);

		assertEquals(p.pathMap.get("Bangalore").get(0), "Singapore");
		assertEquals(p.pathMap.get("Singapore").get(0), "Bangalore");
	}

	@Test
	public void addPaths_adds_all_paths_to_pathMap_from_with_both_side_flights(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);

		Map<String,List<String>> pathMap = p.pathMap;
		assertEquals(pathMap.get("Bangalore").get(0),"Singapore");
		assertEquals(pathMap.get("Singapore").get(0),"Bangalore");
		assertEquals(pathMap.get("Singapore").get(1),"Seoul");
		assertEquals(pathMap.get("Singapore").get(2),"Dubai");
		assertEquals(pathMap.get("Seoul").get(0),"Singapore");
		assertEquals(pathMap.get("Seoul").get(1),"Beijing");
	}

	@Test
	public void areCitiesValid_returns_true_when_both_cities_exists_in_database(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		assertEquals(p.areCitiesValid("Singapore","Bangalore"),1);
	}

	@Test
	public void areCitiesValid_returns_false_when_one_city_doesnt_exist_in_database(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		assertEquals(p.areCitiesValid("Singapore","Stockholm"),3);
		assertEquals(p.areCitiesValid("Chennai","Singapore"),2);
	}

	@Test
	public void isDirectPathBetween_returns_true_when_direct_path_between_BLR_SGP_(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		assertTrue(p.isDirectPathBetween("Bangalore","Singapore"));
	}

	@Test
	public void isDirectPathBetween_returns_false_when_direct_path_between_BLR_TKY(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		assertFalse(p.isDirectPathBetween("Bangalore","Tokyo"));
	}

	@Test
	public void isDirectPathBetween_returns_false_when_direct_path_between_any_invalid_city(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		assertFalse(p.isDirectPathBetween("Bangalore","Chennai"));
	}

	@Test
	public void checkForAnyPath_returns_1_for_direct_path_between_2_cities(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		List<String> pathFinder = new ArrayList<String>();
		assertEquals(p.checkForAnyPath("Bangalore","Singapore",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_1_for_indirect_path_between_BLR_and_TKY(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Tokyo",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_1_for_indirect_path_between_TKY_and_BLR(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
						{"Singapore","Seoul"}, {"Singapore","Dubai"},
						{"Seoul","Beijing"}, {"Beijing","Tokyo"}};
		p.addPaths(routes);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Tokyo","Bangalore",pathFinder),1);
	}

	@Test
	public void checkForAnyPath_returns_0_for_path_between_BLR_and_BJNG(){
		String [] cities = {"Bangalore","Beijing","Singapore","Seoul"};
		Path p = new Path(cities);
		String [][] routes = {{"Bangalore","Singapore"},
							{"Singapore","Seoul"},
							{"Beijing","Tokyo"}};
		p.addPaths(routes);
		List<String> pathFinder = new ArrayList<String>();
		
		assertEquals(p.checkForAnyPath("Bangalore","Beijing",pathFinder),0);
		assertEquals(p.checkForAnyPath("Beijing","Bangalore",pathFinder),0);
	}
	// @Test
	// public void givePathMapStatus_gives_No_city_named_Chennai_in_database_when_no_direct_flight_from_Chennai_available(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	Path p = new Path(path,"Chennai","Singapore");
	// 	assertEquals(p.isPath(), 2);
	// 	assertEquals(p.givePathStatus(),"No city named Chennai in database");
	// }

	// @Test
	// public void givePathStatus_gives_No_city_named_Stockholm_in_database_when_no_flight_to_Stockholm_available(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	Path p = new Path(path,"Bangalore","Stockholm");
	// 	assertEquals(p.isPath(), 3);
	// 	assertEquals(p.givePathStatus(), "No city named Stockholm in database");

	// }
	// @Test
	// public void isPath_returns_true_for_direct_flight_between_Singapore_Bangalore(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	Path p = new Path(path,"Bangalore","Singapore");
	// 	assertEquals(p.isPath(), 1);
	// 	assertEquals(p.givePathStatus(), "true");
	// }

	// @Test
	// public void isPath_returns_false_when_no_direct_flight_between_Bangalore_Tokyo(){
	// 	Map<String,String> path = new HashMap<String,String>();
	// 	path.put("Bangalore","Singapore");
	// 	path.put("Beijing","Tokyo");
	// 	Path p = new Path(path,"Bangalore","Tokyo");
	// 	assertEquals(p.isPath(), 0);
	// 	assertEquals(p.givePathStatus(), "false");
	// }
}