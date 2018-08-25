# campspot-simple-gap-rule

# How to Run
  ## IDE (eg. Eclipse)
  1. Import project as Maven project
  2. Run maven install
  3. Click on Run -> Run Configurations
  4. Click on Arguments tab
  5. In Program Arguments section , Enter your arguments (e.g. "./src/assets/test-case.json" 1).
  6. Click Apply

# high-level description
  The first thing I did was figure out how to get the objects in the test case file to serialize easily into a POJO.
  After figuring that out I looked into a java library that would make dealing with java dates easier. I decided to use the 
  Joda time library which had many features that made tackling this problem much easier.
  

# Assumptions
1.	The reservations will be grouped by campsite, start and end date.
2.	If a search start date land on a reservation end date or a search end lands on a reservation start date that the campsite is able to be reserved.
3. The user know the location of the file they are passing in and the application should not have to search for it.
