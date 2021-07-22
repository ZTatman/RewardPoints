import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap; 
import java.util.Random;
import java.util.Scanner;

// ---- CUSTOMER CLASS ---- //
class Customer {
  
  // Variables
  String name;
  LinkedHashMap<String, ArrayList<Integer>> purchaseHistory; // Preserves insertion order 
  List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
  
  // Constructor
  public Customer(String name) {
    this.name = name;
    this.purchaseHistory = new LinkedHashMap<>();
  }

  // ---- RECURSIVE SOLUTION --- //
  // Given a purchase, a lowerLimit, and an upperLimit calculate number
  // of points earned for every dollar above the upperlimit and every 
  // dollar above the lowerLimit. 
  public static int calcPointsRecursive(int purchase, int upperLimit, int lowerLimit) {
    
    // Purchase amount is 0
    if (purchase <= 0) return 0;
    
    // No. of dollars above the upperLimit
    int dollarsAboveUppper = purchase - upperLimit;
    
    // No. of dollars above the lowerLimit
    int dollarsAboveLower = purchase - lowerLimit;

    // Check for available dollars above upperLimit 
    if (dollarsAboveUppper > 0)
       return (2*dollarsAboveUppper) + calcPointsRecursive(purchase - dollarsAboveUppper, upperLimit, lowerLimit);
    
    // Check for available dollars above lowerLimit
    if (dollarsAboveLower > 0)
      return (1*dollarsAboveLower) + calcPointsRecursive(purchase - dollarsAboveLower, upperLimit, lowerLimit);
    
     return 0;
  }


  // ---- ITERATIVE SOLUTION ---- //
  // Given a purchase, a lowerLimit, and an upperLimit calculate number
  // of points earned for every dollar above the upperlimit and every 
  // dollar above the lowerLimit. 
  public static int calculatePoints(int purchase, int upperLimit, int lowerLimit) {
    
    // Purchase amount is 0
    if (purchase <= 0) return 0;
    
    // Calculate points until the purchase amount is > lowerLimit
    int points = 0;
    while (purchase > lowerLimit) {
      
      // No. of dollars above the upperLimit
      int dollarsAboveUppper = purchase - upperLimit;
    
      // No. of dollars above the lowerLimit
      int dollarsAboveLower = purchase - lowerLimit;

      // Check for available dollars above upperLimit 
      if (dollarsAboveUppper > 0) {
        points += 2*dollarsAboveUppper;
        purchase -= dollarsAboveUppper;
      }
      // Check for available dollars above lowerLimit
      else if (dollarsAboveLower > 0) {
        points += 1*dollarsAboveLower;
        purchase -= dollarsAboveLower;
      }
    }
    return points;
  }

  // Add purchases to a certain month and year of a customers purchase history
  public void addPurchasesToMonth(int[] purchases, String month) {
    
    // No purchases made this month
    if (purchases.length == 0) {
      // This month has no purchase history
      if (!purchaseHistory.containsKey(month)) {
        // Create an empty list of purchases, put it in history for future purchases
        ArrayList<Integer> purchaseList = new ArrayList<>();
        purchaseHistory.put(month, purchaseList);
      }
    }
    
    // Invalid month
    if (!months.contains(month)) throw new IllegalArgumentException("Error: You must enter a valid \'month\' in addPurchaseToMonth()");

    
    // This month has no purchase history
    if (!purchaseHistory.containsKey(month)) {
      // Create a new 
      ArrayList<Integer> purchaseList = new ArrayList<>();
      purchaseHistory.put(month, purchaseList);
      for (int purchase : purchases) {
        purchaseHistory.get(month).add(purchase);
      }  
    }
    else {
      for (int purchase : purchases) {
        purchaseHistory.get(month).add(purchase);
      }  
    }    
  }


  // Get a customers monthly total of rewards points
  public int pointsForMonth(String month, int upperLimit, int lowerLimit) {
    int total = 0;
    
    // Check if this month has purchases
    if (purchaseHistory.containsKey(month)) {
      
      System.out.println("\n\t------------- " + month + " -------------");
      
      for (int purchase : purchaseHistory.get(month)){
        // Calculate points for this purchase
        int sum = calcPointsRecursive(purchase, upperLimit, lowerLimit);
        System.out.println("\tPurchase: $" + purchase + ",\t\tpoints: " + sum);
        
        // Add points to monthly total
        total += calcPointsRecursive(purchase, upperLimit, lowerLimit);
      }
      System.out.println("\n\tMonthly points: " + total);
      System.out.println("\t------------------------------------");

    }
    // No purchase history for this month
    else 
      System.out.println("\n\t-- " + month + " -- " + 
                         "\n\tNo purchases..." + 
                         "\n\t--------------");
    
    return total;    
  }



  // Ranged monthly total of rewards points
  public void pointsForRange(String monthFrom, String monthTo, int upperLimit, int lowerLimit) {
    
    // Variables
    int total = 0;
    HashMap<String, Integer> monthlyPoints = new HashMap<>();
    int start = months.indexOf(monthFrom);
    int end = months.indexOf(monthTo);


    // Sum up the points for each month
    System.out.println("\n\t----- POINTS: " + monthFrom.toUpperCase() + 
                       " - " + monthTo.toUpperCase() + " -----");
    for (int month = start; month <= end; month++) {

      // Check if this month has purchases
      if (purchaseHistory.containsKey(months.get(month))) {
        
        // Calculate this months rewarded points
        int thisMonthsPoints = pointsForMonth(months.get(month), upperLimit, lowerLimit);
        monthlyPoints.put(months.get(month), thisMonthsPoints);
        
        // Add this months points to total
        total += thisMonthsPoints;
      } 
      else {
        monthlyPoints.put(months.get(month), 0);
        System.out.println("\n\t----- " + months.get(month) + " ------" + 
                           "\n\tNo purchases..." + 
                           "\n\tMonthly points: 0" + 
                           "\n\t------------------");
      }
    }

    // Print the total points earned for the range
    System.out.print("\n\tTOTAL POINTS: ");
    for (int month = start; month <= end; month++) {
      if (month == end)
        System.out.print(monthlyPoints.get(months.get(month)) + 
                         " (" + months.get(month) + ") = " + total + "\n");
      else
        System.out.print(monthlyPoints.get(months.get(month)) + 
                         " (" + months.get(month) + ") + ");
    }
    System.out.println("\t------------------------------------");

  }

  // Print a customers entire purchase history
  public void printHistory() {
    
    // Print purchase history
    System.out.println("\n\t-- " + name.toUpperCase() + "\'s PURCHASE HISTORY --");
    
    // For each month
    for (String month : purchaseHistory.keySet()) {
      
      // Print each purchase 
      System.out.println("\n\t-- " + month + " --");
      if (purchaseHistory.get(month).size() == 0)
        System.out.println("\tNo purchases...");
      else
        for (int purchase : purchaseHistory.get(month))
          System.out.println("\tPurchase: $" + purchase);
      System.out.println("\t--------------");
    }
  }
}



public class RewardsPoints {
  public static void main(String[] args) {

    // Variables 
    int numCases;
    String name;
    int numMonths;
    int purchaseLimit;
    int upperLimit;
    int lowerLimit;
    String monthFrom;
    String monthTo;
    HashMap<String, Integer> purchasesPerMonth = new HashMap<>();

    // Input object
    Scanner sc = new Scanner(System.in);

    // For every case, prints customers purchase history, and a random 3 month period of points rewarded
    numCases = sc.nextInt();
    for (int i = 1; i <= numCases; i++) {
      
      // Parameters for each case
      name = sc.next();
      purchaseLimit = sc.nextInt();
      upperLimit = sc.nextInt();
      lowerLimit = sc.nextInt();
      numMonths = sc.nextInt();
      
      // Create new customer 
      Customer cust = new Customer(name);

      // Add all monthly purchases to history
      System.out.println("CASE #" + i + ": " + name);
      for (int j = 0; j < numMonths; j++) {
        
        // Number of purchases for this month
        int numPurchases = sc.nextInt();
        String month = sc.next();
        purchasesPerMonth.put(month, numPurchases);

        // Generate random purchase amounts
        int[] randPurchases = generateRandomPurchases(numPurchases, purchaseLimit);
        
        // Add purchases for this month to purchase history
        cust.addPurchasesToMonth(randPurchases, month);
      }

      // Print entire History
      cust.printHistory();

      // Print points awarded for a range of months
      monthFrom = sc.next();
      monthTo = sc.next();
      cust.pointsForRange(monthFrom, monthTo, upperLimit, lowerLimit);
    }
  }

  // Given an amount n and an upper limit, generate random purchases
  // between [$0, $upperLimit)
  public static int[] generateRandomPurchases(int n, int upperLimit) {
    
    // 0 purchases, return an empty array
    if (n == 0) return new int[0];

    // upperLimit < 1
    if (upperLimit < 1) throw new IllegalArgumentException("Error: \'upperLimit\' must be an int greater than 0 in generateRandomPurchases()");

    // Variables
    int[] purchases = new int[n];
    Random rand = new Random();

    // Generate random purchase amounts
    for (int i = 0; i < n; i++) {
      purchases[i] = rand.nextInt(upperLimit);
    }

    return purchases;
  }
}
