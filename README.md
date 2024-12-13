- Designed desktop text app that tracks a user’s experiences through an airport terminal, including tracking expenses, checking flight status, and airport weather
- Developed personal user adventures from arriving to boarding, and stored profiles based on past experiences
- Integrated multi-destination airport with unique shopping, dining, and luxury experiences

---
**Algorithm:**
1. Declare and initialize starting variables
a. Current time (int)
b. Variables for boarding pass (char for terminal, int for boarding time, char for
boarding class, int for seat number)
2. Display boarding pass of user and times
3. Determine airport weather information by reading from a text file and using random number
generators.
a. Airport weather information gets updated every hour
4. Determine incoming and outgoing flights to the airport by reading a text file and determine
delayed flights using random number generators
a. Information gets updated based on time of flight and current time (ex. Flight will no
longer be displayed 30 minutes after the take off time as it is no longer applicable to
the current passengers at the terminal)

5. Ask of user’s Aeroplan status (higher the status, the more the discount)
6. Give user options to spend time at airport (each option will take some time away from user
and move them closer to their boarding time)
a. Go shopping
i. User will have options for which store to shop at, and what items are
available to buy
ii. Track which items user bought at each location
iii. Track time spent at each specific store
b. Eat at a restaurant
i. User will have options for which restaurant to eat at, and prices for food
items
ii. Track and store expenses at restaurant
1. Extra if alcohol (ask age before)
iii. Track time spent at restaurant

c. Read magazines
i. User will have options for which magazine to read
ii. Option to buy magazine
iii. Track time spent
d. Program will track overall expenses of user and expenses at each location
e. Each activity can give the user some more Aeroplan points
7. While user is buying, expenses are getting updated in text file and airport gross income gets
updated
8. Closer to boarding time, give user the option if they want to upgrade their seat or check in a
carry on
a. Check in carry on
i. Discount for user if they wish to check in their carry on
b. Upgrade seat
i. Extra charge for upgrading
ii. Limited number of seats so read availability from text file and output seat
taken into text file for future passengers
c. Each option gives the user some more Aeroplan points
9. When it is time for boarding the plane, the user will get the option to view their time spent at
the terminal
a. User can view the changes they made to their plane and seat (ex. If they updated their
seat or checked in their carry on)
b. User can view expenses they made at the terminal and where they were made
c. User can view how their expenses at the airport helped the gross income of the airport
(i.e. how much percentage their expenses contributed to the gross income)
d. User has a tally of their accumulated Aeroplan points due to their expenses and time
spent at the terminal
