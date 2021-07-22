# RewardPoints
This program calculates the amount of points a customer is rewarded for every dollar above two certain purchase amount (dollars) thresholds.  
# What Does My Program Do?  
For each case my program will create a customer, randomly generate purchases given a number and a purchase limit, display a customers entire purchase history, calculates the points rewarded for every purchase, and displays the total sum of points rewarded to a customer within a range of months in a customers purchase history.
# Problem Statement  
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.
Make up a data set to best demonstrate your solution.  

# My Approach  
In order to solve this problem I tried to break the problem down into the basic necessary pieces that I needed to construct a solution:  
* A function to generate a random collection of purchases  
* A way to link a collection of purchases to a certain month and store them as a history
* The lowest purchase amount for which single points can be rewarded for is $50
* The highest purchase amount for which double points can be rewarded for is $100
* A function to calculate the number of points rewarded for each purchase based on the dollar amount  

# Design Decisions  
  
## Object Oriented
One of the critical design decisions I made was making an object oriented solution. While I did not do this in my code, I wanted to encapsulate every customers purchase history and all customer methods, members, and attributes in a Customer object instantiated from a Customer class. This way, if I ever needed to store as many customers as I wanted in a data structure of my choice. The benefit to this is also that each customer's purchase history is confined to an object and seperate from every other customer's purchase history.    
## Input File for Program Parameters  
Originally I was using command line arguments when creating the most basic functions. At first it worked just fine and I didnt have to pass in many parameters to the java program. However, as I created more functions and tried to create my own data for this problem, I realized that I had to pretty much put in place my own constraints for this problem and how to solve it. This meant that I started required an increasing number of initial parameters for each case I tested my solution with. Instead of typing all these parameters as command line arguments, I chose to put all these arguments for each case in an input file to be read by my program.  
  
## LinkedHashMap vs. LinkedList for Purchase History  
In my program, every customer object has a purchase history. At first I decided to use a Linked List just becuase when I thought of a record I thought of it as a record where each month points to the next month's payment history and each . It was totally possible to create a custom linked list class from scratch and have each node have a month identifier and a list for purchases. Even though this would ensure the insertion order of months, I realized that Insertion and Lookup operations for each month would be O(N) in the worst cases. This would also be true on average if I wanted to add additional purchases to an already existing month or even delete a month's purchase history for a customer.
   
Instead, I decided to use a LinkedHashMap that is one of several of Java's Collections. LinkedHashMap is like a HashMap except it preserves the order of inserted elements. It also made more sense to use a HashMap<K,V> where each key is the month I want to map to and each value would be an ArrayList of integers that stored a dynamic array of purcahses. In comparison to a Linked List, Insertion and LookUp operations are both O(1) on average and O(log(n)) in the worst case for HashMap Collisions.  
  
 # Input File Parameters  
 This program uses an input file to read in certain parameters on a case by case basis. Every line of the input file contains certain definable parameters.  
   
 **Every case in the input file follows the following format:**  
```  
n
name
randPurchaseLimit upperLimit lowerLimit
numMonths
numPurchases month  
.  
.  
.  
monthFrom monthTo
```  
Where,  
```n``` is the number of cases to look at  
```name``` is the first name of a customer  
```randPurchaseLimit``` is the upper limit for which a random dollar amount between ```[0,upperLimit)``` will be generated  
```upperLimit``` is dollare amount for which double points for every dollar above this amount is rewarded  
```lowerLimit``` is dollare amount for which single points for every dollar above this amount is rewarded, after double points have been rewarded (if applicable)  
```numMonths``` is the number of months for which collections of purchases will be randomly generated  
```numPurchases``` is the number of random purchases to generate for a specific month  
```month``` is the name of the month for which random purchases will be generated, **First letter must be capitalized**  
```monthFrom``` is the starting month of a range for which a total sum of rewards points will be calculated  
```monthTo``` is the ending month of a range for which a total sum of rewards points will be calculated  
## Case Example  
```  
1
John  
500 100 50  
5  
5 March
12 August  
3 September  
31 October  
0 November 
March September
```  
## Output Example  
```  
 CASE #1: John

        -- JOHN's PURCHASE HISTORY --

        -- March --
        Purchase: $118
        Purchase: $454
        Purchase: $224
        Purchase: $103
        Purchase: $481
        --------------

        -- August --
        Purchase: $301
        Purchase: $149
        Purchase: $135
        Purchase: $263
        Purchase: $264
        Purchase: $95
        Purchase: $383
        Purchase: $292
        Purchase: $56
        Purchase: $164
        Purchase: $17
        Purchase: $325
        --------------

        -- September --
        Purchase: $220
        Purchase: $279
        Purchase: $308
        --------------

        -- October --
        Purchase: $454
        Purchase: $357
        Purchase: $207
        Purchase: $412
        Purchase: $353
        Purchase: $115
        Purchase: $178
        Purchase: $1
        Purchase: $354
        Purchase: $206
        Purchase: $390
        Purchase: $496
        Purchase: $84
        Purchase: $362
        Purchase: $1
        Purchase: $237
        Purchase: $286
        Purchase: $315
        Purchase: $377
        Purchase: $191
        Purchase: $247
        Purchase: $148
        Purchase: $389
        Purchase: $222
        Purchase: $378
        Purchase: $477
        Purchase: $165
        Purchase: $255
        Purchase: $71
        Purchase: $114
        Purchase: $426
        --------------

        -- November --
        No purchases...
        --------------

        ----- POINTS: MARCH - SEPTEMBER -----

        ------------- March -------------
        Purchase: $118,         points: 86
        Purchase: $454,         points: 758
        Purchase: $224,         points: 298
        Purchase: $103,         points: 56
        Purchase: $481,         points: 812

        Monthly points: 2010
        ------------------------------------

        ----- April ------
        No purchases...
        Monthly points: 0
        ------------------

        ----- May ------
        No purchases...
        Monthly points: 0
        ------------------

        ----- June ------
        No purchases...
        Monthly points: 0
        ------------------

        ----- July ------
        No purchases...
        Monthly points: 0
        ------------------

        ------------- August -------------
        Purchase: $301,         points: 452
        Purchase: $149,         points: 148
        Purchase: $135,         points: 120
        Purchase: $263,         points: 376
        Purchase: $264,         points: 378
        Purchase: $95,          points: 45
        Purchase: $383,         points: 616
        Purchase: $292,         points: 434
        Purchase: $56,          points: 6
        Purchase: $164,         points: 178
        Purchase: $17,          points: 0
        Purchase: $325,         points: 500

        Monthly points: 3253
        ------------------------------------

        ------------- September -------------
        Purchase: $220,         points: 290
        Purchase: $279,         points: 408
        Purchase: $308,         points: 466

        Monthly points: 1164
        ------------------------------------

        TOTAL POINTS: 2010 (March) + 0 (April) + 0 (May) + 0 (June) + 0 (July) + 3253 (August) + 1164 (September) = 6427
        ------------------------------------
 ```  
 ## Current Limitations  
 While the bulk of what I would like the program to do is done, there are numerous limitations on what this program can do given different parameters.  
 1. There is no option for storing more than a year's worth of purchases. Every customer can only have a maximum of 12 months of purchases history (will be added in the future most likely) 
 2. There are also no day or time identifiers for any purchase added to a month. This was also something I wanted to add, but just didnt get to becuase of time.
 3. ```monthFrom``` and ```monthTo``` must be months that were listed in the cases. To create a month with o purchase history you must specify it by typing ```0 month```  
 4. ```monthFrom``` and ```monthTo``` range selectors cannot be used to wrap around a year (haven't figured how to implement this yet). For instance, in the example above ```November March``` is invalid.  
 5. There are probably more, but I can't think of the rest right now (will updated as needed)  
 ## Steps to Run Program  
 
