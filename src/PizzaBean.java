import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ManagedBean
@RequestScoped
public class PizzaBean  implements Serializable{
	
	//initializing required parameters
	static JSONArray AllRestaurants;
	static JSONObject Restaurant;
	static JSONArray Menu;
	
	private static String restaurantName;
	private List<String> restaurantNames= new ArrayList<String>(); 
	private static List<String> menuItems = new ArrayList<String>(); 
	static List<String> selectedItems = new ArrayList<String>();
	static Map<String,Long> itemAndPriceMap = new HashMap<String,Long>();
	static int total;	
	static Random randomGenerator = new Random();
	static int orderId;	
	static Map<Integer,Map> orderIdMap = new HashMap<Integer,Map>();
	
	/**
	 * constructor method 
	 * Read Restaurant json which contains all the available restaurants and its menu.
	 * catch and print exception if in case reading the json file.
	 * display all the restaurants
	 */
	public PizzaBean() {
		
		 JSONParser parser = new JSONParser();
		 
		//read json file
		 try {
			  AllRestaurants = (JSONArray) parser.parse(new FileReader("E:\\lab\\eclipseWorkspace\\PizzaApp\\WebContent\\Restaurant.json"));
			  
			  for(Object o : AllRestaurants) {
					 JSONObject tempRestaurant = (JSONObject) o;
					 restaurantNames.add((String) tempRestaurant.get("name"));					 					 
			  }		  
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	      }       
    }
	
	/**
	 * displayMenu method 
	 * get the details of restaurant with given id and store it in the Restaurant object
	 * get the details of menu with given restaurant id and store it in the Menu object
	 */
	public static void displayMenu() {
		//check whether AllRestaurants was already populated
		 if(AllRestaurants == null) {
			 JSONParser parser = new JSONParser();
			 try {
				AllRestaurants = (JSONArray) parser.parse(new FileReader("E:\\lab\\eclipseWorkspace\\PizzaApp\\WebContent\\Restaurant.json"));
			} catch (Exception e) {
	            e.printStackTrace();
	        }
		 }
		 
		//get the selected restaurant details based on the restaurantid selected
		for(Object o : AllRestaurants) {
			 JSONObject tempRestaurant = (JSONObject) o;
			 if(tempRestaurant.get("name").toString().equals(restaurantName)) {
				 Restaurant = tempRestaurant;
			 }
		 }
		
		//get the menu details of the selected restaurant
		 Menu = (JSONArray) Restaurant.get("menu");
		 //check if that restaurant doesnt have any menu to show
		 if(Menu != null) {
			 for(Object o : Menu) {
				 JSONObject tempMenu = (JSONObject) o;
				 menuItems.add((String) tempMenu.get("name"));
			 }
		 }
	 }
	
	/**
	 * createOrder method 
	 * get the customer selected items and calculate the total
	 */
	public static void createOrder() {
		total = 0;
		
		//iterate customer selected items name
		//compare that with Menu object to get that menu details
		for (String item : selectedItems) {
			for(Object o : Menu) {
				 JSONObject tempMenu = (JSONObject) o;
				 if(tempMenu.get("name").toString().equals(item)) {
					 Long itemPrice = (Long) tempMenu.get("price");
					 itemAndPriceMap.put(tempMenu.get("name").toString(), itemPrice);
					 //calculate the total price of the selected items
					 total= (int) (total+itemPrice);
			}
		  }
		}		
	}
	
	/**
	 * confirmOrder method 
	 * generate orderid
	 * add orderid and its corresponding itemAndPriceMap to a map
	 */
	public static void confirmOrder() {
		
		orderId = randomGenerator.nextInt(100);
		orderIdMap.put(orderId, itemAndPriceMap);
	}
	
	
	
//All getter and setter methods

	public String getRestaurantName() {
		return restaurantName;
	}


	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}


	public List<String> getRestaurantNames() {
		return restaurantNames;
	}


	public void setRestaurantNames(List<String> restaurantNames) {
		this.restaurantNames = restaurantNames;
	}
	
	public List<String> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<String> menuItems) {
		this.menuItems = menuItems;
	}
	
	public List<String> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<String> selectedItems) {
		this.selectedItems = selectedItems;
	}
	 
	public Map<String, Long> getItemAndPriceMap() {
		return itemAndPriceMap;
	}

	public void setItemAndPriceMap(Map<String, Long> itemAndPriceMap) {
		this.itemAndPriceMap = itemAndPriceMap;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		PizzaBean.orderId = orderId;
	}
}
