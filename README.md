# campspot-simple-gap-rule

# How to Run
  ## IDE (e.g. Eclipse)
  1. Import project as Maven project
  2. Run maven install
  3. Click on Run -> Run Configurations
  4. Click on Arguments tab
  5. In Program Arguments section , Enter your arguments (e.g. "./src/assets/test-case.json" 1). If no gapRule size is passed in then it will default to 1;.
  6. Click Apply
  
  ## Command Line
   1. you need to have the maven cli or maven wrapper installed on your machine Here is a guide if you don't have it installed: (https://maven.apache.org/install.html)
   2. run a maven build
   3. java -jar target/campspot-0.0.1-SNAPSHOT-jar-with-dependencies.jar testFilePath gapSize (e.g. /test-case.json 1). If no gapRule size is passed in then it will default to 1.

# high-level description
The first thing I did was figure out how to get the objects in the test case file to serialize easily into a POJO.
After figuring that out I looked into a java library that would make dealing with java dates easier. I decided to use the 
Joda time library which had many features that made tackling this problem much easier. I started by creating my domain models for the objects specified in the test file. Then I started writing a unit test that would easily simulate a valid campsite and a campsite that breaks the gap rule. From there I needed to make a way to get each reservation for a specific campsite. Initally I had this method in the `ReservationManager` class but quickly realized that it would be much easier if this         method lived in the `Campsite` class. Then it was a matter of removing any campsite that broke the gap rule. I first did this with `for` loops but again realized after a bit of googling that it would be much easier if I changed the `for` loops into a `stream` and used the `filter` method. I then just needed to check if any reservations were returned and if none were returned I would add that campsite to the list of available campsites. After doing this and making sure my unit tests covered all scenarios I discovered that I needed to create a method that would check if a search overlapped with a reservation.
  
# Assumptions
1.	he reservations will be grouped by campsite, start date, and end date.
2.	If a search start date lands on a reservation end date or a search end lands on a reservation start date that the campsite is able to be reserved.
3. The user knows the location of the file they are passing in and the application should not have to search for it.
4. If the gap rule size is 3 then all gaps less than that are also invalid. (e.g. gap size is 3 and the gap returned is 2)        this campsite is unavailable.
