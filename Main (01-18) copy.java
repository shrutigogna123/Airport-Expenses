/*
    Name: Shruti Gogna
    Starting date: Monday, December 19, 2022.
    Course code: ICS3U1
    Description: tracks user's expenses as they go through an airport from checking in their luggage to boarding. The database will collect how much the user spends at each location and how much time they spent there.
*/

import java.util.*;
import java.io.*;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws IOException{
        final String ANSI_RED_BACKGROUND = "\u001B[41m";
        final String ANSI_RESET = "\u001B[0m";
        String userName;
        int [] aeroplanPoints = new int [3];
        String aeroplanStatus;
        String [] boardingPass = new String [14];
        int localTime;
        int timer = 0;
        String activity;
        Scanner input = new Scanner (System.in);
        System.out.print ("⌨  Please enter your name to boot up the program: ");
        userName = input.next();
        int age = userStartUp ();
        intro(userName);
        System.out.print ("\nBefore starting any activity, please enter your Aeroplan points to have the chance to obtain points and discounts. \n\t⌨  Enter your Status Qualifying Miles: ");
        aeroplanPoints [0] = input.nextInt();
        System.out.print ("\t⌨  Enter your Status Qualifying Segments: ");
        aeroplanPoints [1] = input.nextInt();
        System.out.print ("\t⌨  Enter your Status Qualifying Dollars: ");
        aeroplanPoints [2] = input.nextInt();
        aeroplanStatus = aeroplanStatus (aeroplanPoints);
        System.out.println();
        boardingPass = boardingPass (false, boardingPass);
        System.out.println ();
        localTime = currentTime ();
        int boardingTime = Integer.parseInt(boardingPass[1]);
        boolean firstTimer = true;
        timer = timer (boardingTime, localTime, timer, firstTimer);
        while (timer > 30) {
            System.out.println ("Your boarding time is " + boardingPass[1]);
            System.out.println ("Please be at your gate 30 minutes prior to your boarding time.");
            timer = timer (boardingTime, localTime, timer, false);
            if (timer > 30) {
                activity = displayActivities(localTime, boardingPass);
                if (activity.equals ("flight status")) {
                    localTime = flightStatus (localTime, boardingPass, boardingTime, timer);  
                }
                else if (activity.equals ("airport weather")) {
                    localTime = airportWeather (localTime, timer, boardingTime);
                }
                else if (activity.equals ("restaurant")) {
                    localTime = display (localTime, age, aeroplanStatus, "restaurants", "dining", "Tim Hortons", "Boston Pizza", "Wahlburger's", boardingTime, timer);
                }
                else if (activity.equals ("shopping")) {
                    localTime = display (localTime, age, aeroplanStatus, "stores", "shopping", "Chanel", "Duty Free", "Lindt", boardingTime, timer);
                }
                else if (activity.equals ("reading")) {
                    localTime = display (localTime, age, aeroplanStatus, "books", "reading", "Hans Christian Anderson", "Grimm Brothers", "Jane Austen", boardingTime, timer);
                }
                localTime = printTime (localTime, "");
            }
        }
        if (timer <= 30 || timer < 1) {
            System.out.println ("TIME FOR CARRY ON");
            carryOn ();
            boardingPass = upgradeSeat (boardingPass[9], boardingPass[11], boardingPass);
            lastReceipt (userName);
            displayTime ();
            System.out.println ("\t" + boardingTime + ": BOARDING TIME");
        }
        System.out.println ();
        System.out.println ("Thank you for spending time at the Toronto Pearson International Airport. We hope you enjoy your flight and hope to see you soon!");
        ArrayList <String> data = new ArrayList <String> ();
        data.clear ();
        outputTxt (data, "timeBreakdown");
        outputTxt (data, "userExpenses");
    }

    /*
        Purpose: used to confirm that a user is present and would like to start the program. Function for user-friendliness
        Post: an int parameter for the user’s age
    */
    public static int userStartUp () {
        int userAge = 0;
        Scanner input = new Scanner (System.in);
        System.out.print ("⌨  Enter your age (between 0 and 120): ");
        userAge = input.nextInt();
        while  (userAge < 0 || userAge > 120) {
            System.out.print ("Invalid input. Please try again: ");
            userAge = input.nextInt();
        }
        if (userAge > 18)
            System.out.println ("You will be able to drink alcohol at restaurants.");
        else if (userAge >= 12)
            System.out.println ("You will not be able to drink alcohol at restaurants.");
        else {
            while (userAge < 12 || userAge > 120){
                System.out.print ("You are under 12 years of age and cannot use this program. Please allow your parent/guardian to use this program on your behalf and input their age: ");
                userAge = input.nextInt();
            }
            if (userAge >= 12 && userAge < 18)
                System.out.println ("You will not be able to drink alcohol at restaurants.");
        }
        //input.close();
        return userAge;
    }

    /*
        Purpose: display instructions and welcome screen for user
        Pre: one String parameter for the user's name
    */
    public static void intro (String name){
        String userIn;
        String plane = "🛫";
        Scanner input = new Scanner (System.in);
        System.out.println ("\n\n\n");
        System.out.println ("Hello " + name);
        System.out.println ("✈ ✈ ✈ \tWELCOME TO AIRPORT EXPENSES!\t ✈ ✈ ✈");
        System.out.print ("⌨  Please enter 'alpha' if you would like to view the program overview or 'bravo' if you would like to start your airport adventure: ");
        userIn = input.next();
        if (userIn.equalsIgnoreCase("alpha") || userIn.equalsIgnoreCase("a")) {
            System.out.println ("\tIn this program, you will be at the Toronto Pearson International Airport and need to travel throughout the airport terminal from checking in your luggage to boarding your plane. \n\tYou will then go through a series of activities (similar to a choose your own adventure activity). \n\tThese activities are shopping, reading a book, or eating at a restaurant. \n\tThroughout your journey, the program will track how long each activity takes and how much money you spend at each activity, to keep you on track until your boarding time.");
            while (!userIn.equalsIgnoreCase("bravo")) {
                System.out.print ("⌨  Enter 'bravo' to start the program: ");
                userIn = input.next();
            }
        }
        else if (userIn.equalsIgnoreCase("bravo")) {}
        else {
            while (!userIn.equalsIgnoreCase("alpha") && !userIn.equalsIgnoreCase("bravo")) {
                System.out.print ("Invalid input. Please try again: ");
                userIn = input.next();
            }
            if (userIn.equalsIgnoreCase("alpha")) {
                System.out.println ("In this program, you will be at an airport and need to travel throughout the airport terminal from checking in your luggage to boarding your plane. You will then go through a series of activities (similar to a choose your own adventure activity). These activities are shopping, reading a book, or eating at a restaurant. Throughout your journey, the program will track how long each activity takes and how much money you spend at each activity, to keep you on track until your boarding time.");
            }
            else if (userIn.equalsIgnoreCase("bravo")) {}
        }
        System.out.println ("\nYour airport adventure is starting in: ");
        for (int i = 3; i > 0; i--) {
            for (int j=i ; j > 0; j--) {
                System.out.print (plane + " ");
            }
            System.out.println(i);
        }
        System.out.println ("0");
    }

    /*
        Purpose: determine the user's Aeroplan Status
        Pre: one int array parameter that stores all of the types of Aeroplan points
        Post: one String paramter for the status
    */
    public static String aeroplanStatus (int [] points) {
        String status = "no status";
        if (points [0] >= 1000000 || points [1] >= 100) {
            if (points [2] >= 20000)
                status = "Super Elite";
            else if (points [2] >= 9000)
                status = "75K";
            else if (points [2] >= 6000)
                status = "50K";
            else if (points [2] >= 4000) {
                status = "35K";
            }
            else if (points [2] >= 3000) {
                status = "25K";
            }
        }
        else if (points [0] >= 75000 || points [1] >= 75) {
            if (points [2] >= 9000)
                status = "75K";
            else if (points [2] >= 6000)
                status = "50K";
            else if (points [2] >= 4000) {
                status = "35K";
            }
            else if (points [2] >= 3000) {
                status = "25K";
            }
        }
        else if (points [0] >= 50000 || points [1] >= 50) {
            if (points [2] >= 6000)
                status = "50K";
            else if (points [2] >= 4000) {
                status = "35K";
            }
            else if (points [2] >= 3000) {
                status = "25K";
            }
        }
        else if (points [0] >= 350000 || points [1] >= 35) {
            if (points [2] >= 4000) {
                status = "35K";
            }
            else if (points [2] >= 3000) {
                status = "25K";
            }
        }
        else if (points [0] >= 25000 || points [1] >= 25) {
            if (points [2] >= 3000) {
                status = "25K";
            }
        }
        if (status.equals("no status"))
            System.out.println ("You are not yet eligible for any Aeroplan status.");
        else
            System.out.println ("Your Aeroplan status is " + status);
        return status;
    }

    /*
        Purpose: determine and display the boarding pass of the user
        Pre: one boolean parameter for whether the user passed the upgrading their seat portion or not, one String array for the current boarding pass
        Post: one String array parameter for the boarding pass
    */
    public static String [] boardingPass (boolean upgradeSeat, String [] boarding) throws IOException {
        Scanner input = new Scanner (System.in);
        String [] passes = new String [5];
        String userIn;
        String [] boardingTitle = {"Departure date:\t\t", "Boarding time:\t\t", "Departure time:\t\t", "Departure airport:\t", "Departure code:\t\t", "Arrival date:\t\t", "Arrival time:\t\t", "Arrival airport:\t", "Arrival code:\t\t", "Class:\t\t\t\t", "Zone:\t\t\t\t", "Seat:\t\t\t\t", "Flight number:\t\t", "Company:\t\t\t"};        
        if (upgradeSeat == false) {
            int num = 0;
            Scanner in = new Scanner (new File ("boardingPass.txt"));
            for (int i = 0; i < 5; i++) {
                passes [i] = in.nextLine();
            }
            num = (int) (Math.random () * (5-1+1) + 0);
            boarding = (passes [num]).split (", ");
            in.close();
        }
        System.out.println ("🎫Here is your boarding pass:🎫");
        for (int i = 0; i < boarding.length; i++) {
            System.out.println (boardingTitle[i] + "\t" + boarding[i]);
        }
        System.out.print ("⌨  Please enter 'alpha' if you would like to continue: ");
        userIn = input.next();
        while (!userIn.equalsIgnoreCase("alpha")) {
            System.out.print ("\nPlease try again to continue: ");
            userIn = input.next();
        }
        return boarding;
    }

    /*
        Purpose: determine the current time at the airport
        Post: one int parameter for the time
    */
    public static int currentTime () throws IOException{
        int timeLocal;
        int timeUTC;
        int hours = (int) (Math.random () * (24-1 + 1) + 1);
        int min = (int) (Math.random () * (59-0 + 1) + 0);
        timeLocal = hours*100 + min;
        timeLocal = timeBreakdown ("Start up", "N/A", timeLocal);
        if ((hours+5) > 24)
            timeUTC = (24-hours+5)*100 + min;
        else
            timeUTC = (hours+5)*100 + min;
        timeUTC = timeBreakdown ("Start up", "UTC", timeUTC);
        timeLocal = printTime (timeLocal, "local");
        timeUTC = printTime (timeUTC, "UTC");
        return timeLocal;
    }

    /*
        Purpose: determine the time to the boarding time
        Pre: one int parameter for the boarding time, one int parameter for the local time, one int parameter for the timer, one boolean parameter for whether this is the first timer or not
        Post: one int parameter for the timer
    */
    public static int timer (int boarding, int time, int timer, boolean firstTimer) {
        //int timer = 0;
        int timerHours = 0;
        int timerMin = 0;
        int currentHours = time/100;
        int currentMin = time-(currentHours*100);
        int boardingHours = boarding/100;
        int boardingMin = boarding - (boardingHours*100);
        if (boardingMin <= currentMin) {
            timerHours-=1;
            timerMin = timerMin+60 + (boardingMin-currentMin);
        }
        else
            timerMin = boardingMin-currentMin;
        if ((boardingHours <= currentHours && firstTimer == true) || (boardingHours <= currentHours && timer > 60)) 
            timerHours = 24-(currentHours-boardingHours) + timerHours;
        else
            timerHours = boardingHours-currentHours + timerHours;
        timer = timerHours*100 + timerMin;
        System.out.println ("You have " + timerHours + " hours and " + timerMin + " minutes to boarding time.\n");
        return timer;
    }

    /*
        Purpose: display and allow user to choose from choice of activities
        Pre: one int parameter for the time, one String parameter for the boarding pass
        Post: one String parameter for the activity that the user wishes to do
    */
    public static String displayActivities (int time, String [] boarding) {
        Scanner input = new Scanner (System.in);
        String userIn = "null";
        String emoji = "";
        String activity = "";
        if (time <= 30) {}
        else {
            System.out.println ("Here are the choice of activities that you can choose from: ");
            System.out.println ("\ta. ✈\tFlight status \n\t\tHave the chance to video all of the flight statuses to see whether flights are delayed or on schedule \n\t\t⌚Takes a minimum of 15 minutes. \n\t\t⌨  Enter 'alpha' to continue with this activity");
            System.out.println ("\tb. 🌡\tAirport weather \n\t\tBe able to view the weather at CYYZ \n\t\t⌚Takes a minimum of 10 minutes. \n\t\t⌨  Enter 'bravo' to continue with this activity");
            System.out.println ("\tc. 🍽\tRestaurant \n\t\tHave the chance to eat at a variety of restaurants from all cuisines \n\t\t⌚Takes a minimum of 30 minutes. \n\t\t⌨  Enter 'charlie' to continue with this activity");
            System.out.println ("\td. 🛒\tShopping \n\t\tBe able to shop for gifts for your loved ones or yourself at the terminal shops. \n\t\t⌚Takes a minimum of 30 minutes. \n\t\t⌨  Enter 'delta' to continue with this activity");
            System.out.println ("\te. 📚\tReading \n\t\tHave the chance to read from a wide selection of the terminal's books and be able to buy a couple. \n\t\t⌚Takes a minimum of 30 minutes. \n\t\t⌨  Enter 'echo' to continue with this activity");
            userIn = input.next();
            if (userIn.equalsIgnoreCase("alpha")) {
                System.out.println ("You chose the option 'Flight status'");
                emoji = "✈";
                activity = "flight status";
            }
            else if (userIn.equalsIgnoreCase("bravo")) {
                System.out.println ("You chose the option 'Airport weather'");
                emoji = "🌡";
                activity = "airport weather";
            }
            else if (userIn.equalsIgnoreCase("charlie")) {
                System.out.println ("You chose the option 'Restaurant'");
                emoji = "🍽";
                activity = "restaurant";
            }
            else if (userIn.equalsIgnoreCase("delta")) {
                System.out.println ("You chose the option 'Shopping'");
                emoji = "🛒";
                activity = "shopping";
            }
            else if (userIn.equalsIgnoreCase("echo")) {
                System.out.println ("You chose the option 'Reading'");
                emoji = "📚";
                activity = "reading";
            }
            else {
                while (!userIn.equalsIgnoreCase("alpha") && !userIn.equalsIgnoreCase("bravo") && !userIn.equalsIgnoreCase("charlie") && !userIn.equalsIgnoreCase("delta") && !userIn.equalsIgnoreCase("echo")) {
                    System.out.println ("Invalid input. ⌨  Please enter 'alpha', 'bravo', 'charlie', 'delta', or 'echo'");
                    userIn = input.next();
                }
                if (userIn.equalsIgnoreCase("alpha")) {
                    System.out.println ("You chose the option 'Flight status'");
                    emoji = "✈";
                    activity = "flight status";
                }
                else if (userIn.equalsIgnoreCase("bravo")) {
                    System.out.println ("You chose the option 'Airport weather'");
                    emoji = "🌡";
                    activity = "airport weather";
                }
                else if (userIn.equalsIgnoreCase("charlie")) {
                    System.out.println ("You chose the option 'Restaurant'");
                    emoji = "🍽";
                    activity = "restaurant";
                }
                else if (userIn.equalsIgnoreCase("delta")) {
                    System.out.println ("You chose the option 'Shopping'");
                    emoji = "🛒";
                    activity = "shopping";
                }
                else if (userIn.equalsIgnoreCase("echo")) {
                    System.out.println ("You chose the option 'Reading'");
                    emoji = "📚";
                    activity = "reading";
                }
            }
        }
        System.out.println ("Your activity is starting soon...");
        for (int i = 3; i > 0; i--) {
            for (int j=i ; j > 0; j--) {
                System.out.print (emoji + " ");
            }
            System.out.println ();
        }
        System.out.println ();
        return activity;
    }

    /*
        Purpose: display the flight status of outgoing flights
        Pre: one int parameter for the current time at the airport, one String array parameter for the boarding pass of the user, one int parameter for the boarding time, one int parameter for the timer
        Post: one int parameter for the updated current time based on how long the user spent watching flight statuses
    */
    public static int flightStatus (int time, String [] boarding, int boardingTime, int timer) throws IOException{
        int departureTimes [] = new int [5];
        String arrivalCode [] = new String [5];
        String flightNum [] = new String [5];
        String flights [] = new String [5];
        int boardingTimes [] = new int [5];
        String status [] = new String [5];
        String userIn = "";
        int countTime = 0;
        Scanner in2 = new Scanner (new File ("boardingPass.txt"));
        for (int i = 0; i < 5; i++) {
            flights [i] = in2.nextLine();
        }
        in2.close();
        for (int i = 0; i < 5; i++) {
            String [] flight = (flights[i]).split (", ");
            departureTimes [i] = Integer.parseInt (flight [2]);
            arrivalCode [i] = flight [8];
            flightNum [i] = flight [12];
            boardingTimes [i] = Integer.parseInt (flight [1]);
            int num = (int) (Math.random () * (3-1+1) + 1);
            if (num == 1)
                status [i] = BLUE_BACKGROUND + "on time" + RESET;
            else if (num == 2)
                status [i] = YELLOW_BACKGROUND + "delayed" + RESET;
            else if (num == 3)
                status [i] = RED_BACKGROUND + "cancelled" + RESET;
            if (arrivalCode [i].equals(boarding[8]))
                status [i] = BLUE_BACKGROUND + "on time" + RESET;
        }
        while (!userIn.equalsIgnoreCase("alpha")) {
            //timer = timer (boardingTime, time, timer);
            if (timer < 30)
                break;
            else
                timer = timer (boardingTime, time, timer, false);
            System.out.println ("OUTGOING FLIGHT STATUS:");
            System.out.println ("FLIGHT NUMBER\tDEPARTURE TIME\tDESTINATION CODE\tSTATUS");
            for (int i = 0; i<5; i++) {
                if (time >= boardingTimes [i] && time <= departureTimes[i]) 
                    status [i] = PURPLE_BACKGROUND + "boarding" + RESET;
                if (status[i].equals(PURPLE_BACKGROUND + "boarding" + RESET) && time >= departureTimes[i])
                    status [i] = CYAN_BACKGROUND + "departed" + RESET;
                if (status[i].equals(CYAN_BACKGROUND + "departed" + RESET) && time >= (departureTimes[i]+100))
                    status [i] = BLUE_BACKGROUND + "on time" + RESET;
                if (departureTimes[i] < 1000) 
                    System.out.println (flightNum [i] + "\t\t\t0" + departureTimes [i] + "\t\t\t" + arrivalCode [i] + "\t\t\t\t\t" + status[i]);
                else
                    System.out.println (flightNum [i] + "\t\t\t" + departureTimes [i] + "\t\t\t" + arrivalCode [i] + "\t\t\t\t\t" + status[i]);  
            }
            time = time + 10;
            countTime++;
            if (((time/10) % 10) >= 6) 
                time = time+100-60;
            if (time > 2400) 
                time = time-2400;
            if (time < 1000) {
                if (time < 100) {
                    if (time < 10)
                        System.out.println ("\nCurrent local time: 000" +time);
                    else
                        System.out.println ("\nCurrent local time: 00" +time);
                }  
                else
                    System.out.println ("\nCurrent local time: 0" +time);
            }
            else
                System.out.println ("\nCurrent local time: " +time);
            System.out.println ("Would you like to continue viewing flight statuses or choose another activity ('alpha')?");
            Scanner input = new Scanner (System.in);
            userIn = input.next();
            
            System.out.println();
        }
        time = timeBreakdown ("Flight status", " ", time);
        return time;
    }

    /*
        Purpose: display the weather at the airport
        Pre: ont int parameter for the current time at the airport, one int parameter for the timer to the boarding time, one int parameter for the timer
        Post: one int parameter for the updated current time at the airport
    */
    public static int airportWeather (int time, int timer, int boarding) throws IOException{
        System.out.println ("METEOROLOGICAL TERMINAL AIR REPORT FOR TORONTO PEARSON INTERNATIONAL AIRPORT");
        String [] titles = {"METAR\t\t", "LOCATION\t", "DATE-TIME\t", "WIND\t\t", "VISIBILITY\t", "WEATHER\t\t", "CLOUDINESS\t", "TEMP/DEWPOINT", "ALTIMETER\t"};
        String [] data = new String [9];
        String userIn = "null";
        int timeUTC = time+500;
        if (timeUTC>2400)
            timeUTC = timeUTC-2400;
        int countTime = 0;
        Scanner inWeather = new Scanner (new File ("airportWeather.txt"));
        for (int i = 0; i < 9; i++) {
            data [i] = inWeather.nextLine ();
        }
        inWeather.close();
        while (!userIn.equals("alpha")) {
            //timer = timer (boarding, time, timer);
            if (timer < 30)
                break;
            else
                timer = timer (boarding, time, timer, false);
            if ((Integer.parseInt(data[2].substring(19))+100 > timeUTC) && (Integer.parseInt(data[2].substring(19)) < timeUTC)) {
                System.out.println ("There is no update to the weather reports. METAR's are updated every hour.");
            }
            else { //METARs are updated every hour
                data [0] = "METAR";
                data [1] = "CYYZ - TORONTO/LESTER B. PEARSON INTL/ON";
                if (timer > 2400) 
                    data [2] = "20 DECEMBER 2022 -\t";
                else
                    data [2] = "21 DECEMBER 2022 -\t";
                if (timeUTC < 1000) 
                    data [2] = data[2] + "0" + (time+500);
                else
                    data [2] = data [2] + (time+500);
                int wind = (int) (Math.random () * (360-0 + 1) + 0);
                int gust = (int) (Math.random () * (10-0 + 1) + 0);
                data [3] = wind + " TRUE @ " + gust + " KNOTS";
                data [4] = ((int) (Math.random () * (15-0 + 1) + 0)) + " STAT. MILES";
                int num = (int) (Math.random () * (6-1 + 1) + 1);
                int cloudiness = (int) (Math.random () * (100-5 + 1) + 5);
                if (num == 1) {
                    data [5] = "LIGHT RAIN";
                    data [6] = "CLEAR " + cloudiness + "00 FT";
                }
                else if (num == 2) {
                    data [5] = "HEAVY SNOW";
                    data [6] = "FEW " + cloudiness + "00 FT";
                }
                else if (num == 3) {
                    data [5] = "MODERATE RAIN";
                    data [6] = "SCATTERED " + cloudiness + "00 FT";
                }
                else if (num == 4) {
                    data [5] = "BLOWING SNOW";
                    data [6] = "BROKEN " + cloudiness + "00 FT";
                }
                else if (num == 5) {
                    data [5] = "MIST";
                    data [6] = "OVERCAST " + cloudiness + "00 FT";
                }
                else 
                    data [5] = "SNOW PELLETS";
                int temp = (int) (Math.random () * (20-0+1) + 0);
                int dew = (int) (Math.random () * (temp - 0 + 1) + 0);
                data [7] = temp + " C / " + dew + " C";
                data [8] = "29.98 IN HG";
                PrintWriter printWeather = new PrintWriter ("airportWeather.txt");
                for (int i = 0; i < 9; i++) {
                    printWeather.println (data[i]);
                }
                printWeather.close();
            }
            System.out.println ("METAR written in plain language:");
            for (int i = 0; i < 9; i++) {
                System.out.println (titles[i] + "\t" + data[i]);
            }
            time = time + 10;
            countTime++;
            timeUTC = time + 500;
            if (((time/10) % 10) >= 6) 
                time = time+100-60;
            if (((timeUTC/10) % 10) >= 6) 
                timeUTC = timeUTC+100-60;
            Scanner input = new Scanner (System.in);
            if (timeUTC < 1000)
                System.out.println ("The current time in UTC is 0" + timeUTC + ". Would you like to complete another activity ('alpha') or view an updated report?");
            else
                System.out.println ("The current time in UTC is " + timeUTC + ". Would you like to complete another activity ('alpha') or view an updated report?");
            userIn = input.next();
            System.out.println();
        }
        time = timeBreakdown ("Airport weather", " ", time);
        return time;
    }

    /*
        Purpose: determine and store expense of item at restaurant/store
        Pre: one String parameter for item name, one double parameter for item price, one int parameter for user's age, one String parameter for user's aeroplan status, one String parameter for restaurant name, one int parameter for number of items currently in receipt, one String parameter for the activity, one int parameter for the time
        Post: one boolean parameter for whether the user wants to continue in the restaurant
    */
    public static boolean locationExpense (String itemName, double itemPrice, int age, String aeroPlan, String location, int num, String activity, int time) throws IOException{
        boolean userContinues = true;
        int quantity = 0;
        double subTotal = 0;
        double totalDis = 0;
        double total = 0;
        double discount = 0;
        String userIn = "yes";
        ArrayList <String> expenses = new ArrayList <String> ();
        expenses = inputTxt("userExpenses");
        if (aeroPlan.equals("25K"))
            discount = 0.03;
        else if (aeroPlan.equals ("35K"))
            discount = 0.05;
        else if (aeroPlan.equals("50K"))
            discount = 0.07;
        else if (aeroPlan.equals("75K"))
            discount = 0.10;
        else if (aeroPlan.equals("Super Elite"))
            discount = 0.15;
        Scanner input = new Scanner (System.in);
        if (activity.equals("reading")) {
            System.out.println ("Would you like to buy " + itemName + " (enter 'yes') or just read the book?");
            userIn = input.next();
        }
        if (userIn.equalsIgnoreCase("yes")) {
            System.out.println ("How many quantities of " + itemName + " would you like? If you would like to cancel this order, please enter '0'.");
            quantity = input.nextInt();
            if (quantity != 0) {
                subTotal = quantity*itemPrice;
                totalDis = subTotal-(subTotal*discount);
                total = totalDis*1.13;
                total*=100;
                total = Math.round(total);
                total/=100;
                System.out.println ("Your total price, after discounts and HST, is $" + total + ". Would you like to purchase this item?");
                userIn = input.next();
                if (userIn.equalsIgnoreCase("yes")) {
                    expenses.add(location + ", " + itemName + ", " + total);
                    outputTxt (expenses, "userExpenses");
                    System.out.println ("Your order of " + quantity + " quantities of " + itemName + " has been added to your receipt.");
                    if (location.equals("Boston Pizza") || location.equals("Wahlburger's")) {
                        System.out.println ("Would you like to purchase alcohol along with your order for a price of $5?");
                        userIn = input.next();
                        if (userIn.equalsIgnoreCase("yes")) {
                            if (age < 18) {
                                System.out.println ("Unfortunately, you are underage and cannot drink.");
                            }
                            else {
                                System.out.println ("Alcohol has been added to your order.");
                                expenses.add (location + ", Alcohol, 5.00");
                                outputTxt (expenses, "userExpenses");
                            }
                        }
                    }
                }  
            }
        }
        else if (activity.equals("reading") && !userIn.equalsIgnoreCase ("yes")) {
            int numTime = (int) (Math.random () * (100-30+1) + 30);
            time = time + numTime;
            time = timeBreakdown (activity, location, time);
            time = printTime (time, "");
            System.out.println ();
        }
        if (activity.equals("reading"))
            System.out.println ("Would you like to continue " + activity + " novels by " + location + "?");
        else
            System.out.println ("Would you like to continue " + activity + " at " + location + "?");
        userIn = input.next();
        if (userIn.equalsIgnoreCase ("yes")) {}
        else if (userIn.equalsIgnoreCase("no") || quantity == 0) {
            ArrayList <String> receipt = new ArrayList <String> ();
            int count = counter("userExpenses");
            for (int i = num; i < count; i++) {
                receipt.add(expenses.get(i));
            }
            if ((receipt.isEmpty()) == false) {
                time = receipt (location, activity, receipt, time);
            }
            else {
                if (activity.equals("reading"))
                    System.out.println ("Thank you for " + activity + " novels by " + location + ".");
                else
                    System.out.println ("Thank you for " + activity + " at " + location + ".");
            }
            userContinues = false;
        }
        else {
            System.out.println ("Invalid input.");
            userContinues = false;
        }
        return userContinues;
    }

    /*
        Purpose: determine the number of items currently in a text file
        Post: one int parameter for the number of items, one String parameter for the text file name
    */
    public static int counter (String txt) throws IOException{
        int count = 0;
        ArrayList <String> expenses = new ArrayList <String> ();
        Scanner inputExpense = new Scanner (new File ("" + txt + ".txt"));
        while (inputExpense.hasNextLine()) {
            expenses.add(inputExpense.nextLine());
        }
        inputExpense.close();
        count = expenses.size();
        return count;
    }

    /*
        Purpose: display the receipt of the user
        Pre: one String parameter for the location, one String parameter for the activity, one ArrayList for the items in the receipt, one int parameter for the time
        Post: one int parameter for the time
    */
    public static int receipt (String location, String activity, ArrayList <String> receipt, int time) throws IOException{
        int numTime = 0;
        System.out.println ("Here is your new receipt for " + location + ":");
        System.out.println ("\n" + location.toUpperCase() + " receipt");
        double totalRes = 0;
        for (int i = receipt.size()-1; i >= 0; i--) {
            String [] item = receipt.get(i).split (", ");
            totalRes = totalRes + Double.parseDouble(item[2]);
            totalRes*=100;
            totalRes = Math.round(totalRes);
            totalRes/=100;
            System.out.println ("\t"+item[1] + "\t$" + item[2]);
        }
        System.out.println ("\tTotal: $" + totalRes);
        System.out.println ();
        numTime = (int) (Math.random () * (5-1+0) +1);
        numTime = numTime + (5 * receipt.size());
        if (activity.equals("reading")) {
            for (int i = 0; i < receipt.size(); i++) {
                int num = (int) (Math.random() * (100-30+0) + 30);
                numTime = numTime + num;
            }
        }
        if (numTime > 60) {
            int num = numTime/60;
            numTime = (numTime-60*num) + (num*100);
        }
        time = time + numTime;
        time = timeBreakdown (activity, location, time);
        time = printTime (time, "");
        System.out.println ();
        return time;
    }

    /*
        Purpose: input data from a text file and store data in a String ArrayList
        Pre: one String parameter for the name of the text file
        Post: one ArrayList String parameter for the data from the text file
    */
    public static ArrayList <String> inputTxt (String txt) throws IOException{
        ArrayList <String> expenses = new ArrayList <String> ();
        Scanner inputFile = new Scanner (new File ("" + txt + ".txt"));
        while (inputFile.hasNextLine()) {
            expenses.add(inputFile.nextLine());
        }
        return expenses;
    }


    /*
        Purpose: output data to a text file if data is stored in an ArrayList
        Pre: one String ararylist parameter which stores the data, one String parameter for the text file name
    */
    public static void outputTxt (ArrayList <String> data, String txt) throws IOException {
        PrintWriter printTxt = new PrintWriter (txt+ ".txt");
        for (int i = 0; i < data.size(); i++) {
            printTxt.println (data.get(i));
        }
        printTxt.close();
    }

    /*
        Purpose: display item options for restaurants/stores/books and allow user to choose an item option
        Pre: one String parameter for the store/restaurant name, one 2D String array parameter for the item options of the location, one int parameter for the user's age, one String parameter for the user's aeroplan status, one int parameter for the number of items currently in the receipt, one boolean parameter if user decides to continue in the store/restaurant, one String parameter for the activity, one int parameter for the time, one int parameter for the boarding time, one int parameter for the timer
        Post: one boolean parameter if user decides to continue in the store/restaurant
    */
    public static boolean chooseItem (String location, String [] [] options, int age, String aeroPlan, int countNumOfItems, boolean userContinueInLocation, String activity, int time, int boardingTime, int timer) throws IOException{
        ArrayList <String> expenses;
        Scanner input = new Scanner (System.in);
        String userIn;
        timer = timer (boardingTime, time, timer, false);
        if (timer <= 30) {
            userContinueInLocation = false;
            return userContinueInLocation;
        }
        System.out.println();
        System.out.println (location + " items: ");
        if (location.equals("Duty Free")) {
            if (age < 18) {
                for (int k = 1; k < 3; k++) {
                    String [] item = options[0] [k].split (", ");
                    options [1] [k] = item[0].substring (0, item[0].indexOf(" (enter")); //item name
                    options [2] [k] = item [1]; //item price
                    System.out.println (item[0] + "\t" + options[2][k]);
                }
                options [1] [0] = "null";
                options [2] [0] = "null";
            }
            else {
                for (int k = 0; k < 3; k++) {
                    String [] item = options[0] [k].split (", ");
                    options [1] [k] = item[0].substring (0, item[0].indexOf(" (enter")); //item name
                    options [2] [k] = item [1]; //item price
                    System.out.println (item[0] + "\t" + options[2][k]);
                }
            }
        }
        else {
            for (int i = 0; i < 3; i++) {
                String []item = options[0][i].split (", ");
                options [1] [i] = item[0].substring (0, item[0].indexOf(" (enter")); //item name
                options [2] [i] = item [1]; //item price
                System.out.println (item[0] + "\t" + options [2][i]);
            }
        }
        System.out.println ("\nWhat option would you like? Please enter 'exit' if you would like to exit " + location + ".");
        userIn = input.next();
        if (!userIn.equalsIgnoreCase ("exit")) {
            boolean userInLocation = false;
            for (int i = 0; i < options [1].length; i++) {
                if (userIn.length() < 3) {} 
                else if (options[1][i].toLowerCase().contains(userIn) && userInLocation == false) {
                    userContinueInLocation = locationExpense (options [1][i], Double.parseDouble (options [2][i]), age, aeroPlan, location, countNumOfItems, activity, time);
                    userInLocation = true;
                }
            }
            while (userInLocation == false) {
                System.out.println ("Invalid input. Enter 'exit' to leave " + location + ".");
                userIn = input.next();
                if (userIn.equalsIgnoreCase("exit")) {
                    System.out.println ("Thank you for spending time in " + location + ". We hope to see you soon!");
                    userContinueInLocation = false;
                }
                userInLocation = true;
            }
        }
        else {
            userContinueInLocation = false;
            expenses = inputTxt("userExpenses");
            ArrayList <String> receipt = new ArrayList <String> ();
            int count = counter("userExpenses");
            for (int i = countNumOfItems; i < count; i++) 
                receipt.add(expenses.get(i));
            if ((receipt.isEmpty()) == false)
                time = receipt (location, activity, receipt, time);
            else {
                int num = (int) (Math.random () * (10-5+1) + 1); //fix here
                time = timeBreakdown (activity, location, time);
                //printTimeTxt (time);
            }
        }
        return userContinueInLocation;
    }

    /*
        Purpose: display restaurants/stores
        Pre: one int parameter for the time, one int parameter for the user's age, one String parameter for the aeroplan status, one String parameter for the activity (restaurant/store), one String parameter for the activity (dining/shopping), one String parameter for the first location, one String parameter for the second location, one String parameter for the third location, one int parameter for the boarding time, one int parameter for the timer
        Post: one int parameter for the time spent
    */
    public static int display (int time, int age, String aeroPlan, String topic, String activity, String location1, String location2, String location3, int boardingTime, int timer) throws IOException{
        timer = timer (boardingTime, time, timer, false);
        if (timer < 30)
            return time;
        System.out.println (topic.toUpperCase());
        String userIn = "null";
        int countNumOfItems = 0; //counts number of current and old items in receipt
        String options [] = new String [3];
        if (topic.equals("restaurants")) {
            Scanner charges = new Scanner (new File ("restaurantCharges.txt"));
            for (int i = 0; i < 3; i++) {
                options [i] = charges.nextLine();
            }
            charges.close();
        }
        else if (topic.equals("books")) {
            Scanner charges = new Scanner (new File ("bookCharges.txt"));
            for (int i = 0; i < 3; i++) {
                options [i] = charges.nextLine();
            }
            charges.close();
        }
        else {
            Scanner charges = new Scanner (new File ("shoppingCharges.txt"));
            for (int i = 0; i < 3; i++) {
                options [i] = charges.nextLine();
            }
            charges.close();
        }
        
        Scanner input = new Scanner (System.in);
        if (activity.equals("dining"))
            System.out.println ("You can choose to eat from three " + topic + " :");
        else if (activity.equals("reading"))
            System.out.println ("You can choose to read books from three authors:");
        else
            System.out.println ("You can choose to shop at three " + topic + " :");
        while (!userIn.equalsIgnoreCase ("alpha") && !userIn.equalsIgnoreCase ("bravo") && !userIn.equalsIgnoreCase ("charlie") && !userIn.equalsIgnoreCase("exit")) {
            System.out.println ("1. " + location1 + " ('alpha')");
            System.out.println ("2. " + location2 + " ('bravo')");
            System.out.println ("3. " + location3 + " ('charlie')");
            System.out.println ("Enter 'exit' to exit " + activity + ".");
            userIn = input.next();
            if (userIn.equalsIgnoreCase ("alpha")) {
                userIn = startLocation (location1, age, aeroPlan, countNumOfItems, activity, time, options, boardingTime, timer);
            }
            else if (userIn.equalsIgnoreCase ("bravo")) {
                userIn = startLocation (location2, age, aeroPlan, countNumOfItems, activity, time, options, boardingTime, timer);
            }
            else if (userIn.equalsIgnoreCase ("charlie")) {
                userIn = startLocation (location3, age, aeroPlan, countNumOfItems, activity, time, options, boardingTime, timer);
            }
            else if (userIn.equalsIgnoreCase("exit")) {
                System.out.println ("Thank you for spending time " + activity + ".");
            }
            else {
                System.out.println ("Invalid input. Please try again.");
            }
        }
        ArrayList <String> allTimes = new ArrayList <String> ();
        allTimes = inputTxt ("timeBreakdown");
        String [] timeLocal = allTimes.get(allTimes.size()-1).split ("; ");
        time = Integer.parseInt (timeLocal [2]);
        return time;
    }

    /*
        Purpose: determine the options for a location
        Pre: one String array for the location, one int parameter for the age, one String parameter for the aeroplan status, one int parameter for the current num of items in receipt, one String parameter for the activity, one int parameter for the time, one String array parameter for the charges at the restaurants, one int parameter for the boarding time, one int parameter for the timer
        Post: one String array for the user's input
    */
    public static String startLocation (String location, int age, String aeroPlan, int countNumOfItems, String activity, int time, String locations [], int boardingTime, int timer) throws IOException {
        String [] [] options = new String [3] [3]; //first row is the item name, price ; second row is just item name ; third row is just item price ; each column is each item
        String userIn = "null";
        if (location.equals("Tim Hortons") || location.equals("Chanel") || location.equals("Hans Christian Anderson"))
            options [0] = locations [0].split ("; ");
        else if (location.equals("Boston Pizza") || location.equals("Duty Free") || location.equals ("Grimm Brothers"))
            options [0] = locations [1].split ("; ");
        else
            options [0] = locations [2].split ("; ");
        boolean userContinueInLoc = true;
        countNumOfItems = counter("userExpenses");
        while (userContinueInLoc == true) {
            timer = timer (boardingTime, time, timer, false);
            if (timer < 30)
                break;
            userContinueInLoc = chooseItem (location, options, age, aeroPlan, countNumOfItems, userContinueInLoc, activity, time, boardingTime, timer);
            userIn = "null";
        }
        return userIn;
    }
    /*    
        Purpose: output the amount of time spent at a location to text file
        Pre: one String parameter for the activity, one String parameter for the location, one int parameter for the time (4 digits in 24 hour format)
        Post: one int parameter for the time
    */
    public static int timeBreakdown (String activity, String location, int time) throws IOException{
        if ((time%100) >= 60) {
            time = time+100-60;
        }
        Scanner inputTimes = new Scanner (new File ("timeBreakdown.txt"));
        ArrayList <String> times = new ArrayList <String> ();
        while (inputTimes.hasNextLine()) {
            times.add (inputTimes.nextLine());
        }
        //inputTimes.close();
        times.add (activity + "; " + location + "; " + time);
        outputTxt (times, "timeBreakdown");
        return time;
    }

    /*
        Purpose: print out time
        Pre: one int parameter for the time, one String parameter for the type
        Post: one int parameter for the time
    */
    public static int printTime (int time, String type) {
        if ((time%100) >= 60) {
            time = time+100-60;
        }
        if (time > 2400)
            time = time-2400;
        if (time < 1000) {
            if (time < 100) {
                if (time < 10) {
                    if (type.equals(""))
                        System.out.println ("The current time is 000" + time);
                    else
                        System.out.println ("The current " + type + " time is 000" + time);
                }
                else {
                    if (type.equals(""))
                        System.out.println ("The current time is 00" + time);
                    else
                        System.out.println ("The current " + type + " time is 00" + time);
                }
            }
            else {
                if (type.equals(""))
                    System.out.println ("The current time is 0" + time);
                else
                    System.out.println ("The current " + type + " time is 0" + time);
            }
        }
        else {
            if (type.equals(""))
                System.out.println ("The current time is " + time);
            else
                System.out.println ("The current " + type + " time is " + time);
        }
        return time;
    }

    /*
        Purpose: allow the user to check in their carry on
    */
    public static void carryOn () throws IOException {
        Scanner input = new Scanner (System.in);
        String userIn;
        ArrayList <String> expenses = new ArrayList <String> ();
        expenses = inputTxt ("userExpenses");
        int num = (int) (Math.random () * (500-50+1) + 50);
        System.out.println ("As the time is approaching for boarding, would you like to check in your carry on to receive a discount of $" + num + "?");
        userIn = input.next();
        if (userIn.equalsIgnoreCase("yes")) {
            expenses.add ("Check in carry on, , -" + num);
            outputTxt (expenses, "userExpenses");
            System.out.println ("Your carry on has been checked in. Thank you for your help!");
        }
        else
            System.out.println ("Thank you for allowing us to consider checking in your carry on.");
        System.out.println();
    }

    /*
        Purpose: allow the user to upgrade their seat for the flight
        Pre: one String parameter for the current class of the user, one String parameter for the current seat of the user, one String array for the boarding pass
        Post: one String array parameter for the boarding pass
    */
    public static String [] upgradeSeat (String className, String seatOld, String boardingPass []) throws IOException {
        Scanner input = new Scanner (System.in);
        String userIn;
        String newGroup [] = new String [3];
        String newSeat [] = new String [3];
        int newPrice [] = new int [3];
        String status = "null";
        int currentPrice;
        if (className.equals("first class"))
            currentPrice = (int) (Math.random () * (7500-5000+1) + 5000);
        else if (className.equals("business"))
            currentPrice = (int) (Math.random() * (4999-2500+1) + 2500);
        else
            currentPrice = (int) (Math.random () * (2499-950+1) + 950);
        ArrayList <String> expenses = new ArrayList <String> ();
        expenses = inputTxt ("userExpenses");
        System.out.println ("You have the chance to upgrade your seat for your current flight!");
        System.out.println ("Here are a few available seats: ");
        for (int i = 0; i < 3; i ++) {
            int seatRow;
            int num = (int) (Math.random () * (6-1+1) + 1);
            char seatAisle;
            int price = 0;
            if (num==1)
                seatAisle = 'A';
            else if (num==2)
                seatAisle = 'B';
            else if (num==3)
                seatAisle = 'C';
            else if (num ==4)
                seatAisle = 'D';
            else if (num ==5)
                seatAisle = 'E';
            else
                seatAisle = 'F';
            if (num==1 || num==2) {
                newGroup [i] = "first class";
                seatRow = (int) (Math.random () * (5-1+1) + 1);
                price = (int) (Math.random () * (7500-5000+1) + 5000);
            }
            else if (num == 3||num == 4) {
                newGroup [i]= "business";
                seatRow = (int) (Math.random () * (17-6+1) + 6);
                price = (int) (Math.random() * (4999-2500+1) + 2500);
            }
            else {
                newGroup [i]= "economy";
                seatRow = (int) (Math.random () * (25-18+1) + 18);
                price = (int) (Math.random () * (2499-950+1) + 950);
            }
            newSeat [i] = Integer.toString(seatRow) + seatAisle;
            newPrice[i] = price - currentPrice;
            if (newPrice[i] < 0) {
                status = "downgrade";
                System.out.println ((i+1) + ". " + newGroup [i] + ": " + newSeat [i]+ " (downgrade and receive refund of: $" + (-1*newPrice[i]) + ")");
            }
            else if (newPrice[i] > 0) {
                status = "upgrade";
                System.out.println ((i+1) + ". " + newGroup [i] + ": " + newSeat [i]+ " (additional charge of: $" + newPrice[i] + ")");
            }
            else
                System.out.println ((i+1) + ". " + newGroup [i] + ": " + newSeat [i]+ " (no charge)");
        }
        System.out.println ();
        if (className.equals("first class")) {
            System.out.println ("Would you like to upgrade your current seat of " + seatOld + " in " + className + "? ('yes'/'no')");
        }
        else
            System.out.println ("Would you like to upgrade your current seat of " + seatOld + " in " + className + " class? ('yes'/'no')");
        userIn = input.next();
        while (!userIn.equalsIgnoreCase("yes") && !userIn.equalsIgnoreCase("no")) {
            System.out.println ("Invalid input. Please try again.");
            userIn = input.next();
        }
        while (!userIn.equalsIgnoreCase("alpha") && !userIn.equalsIgnoreCase("bravo") && !userIn.equalsIgnoreCase("charlie") && !userIn.equalsIgnoreCase("no")) {
            System.out.println ("Which option would you like? 'alpha' for option 1, 'bravo' for option 2, or 'charlie' for option 3? If you would not like any options, please enter 'no'.");
            userIn = input.next();
            if (!userIn.equalsIgnoreCase("alpha") && !userIn.equalsIgnoreCase("bravo") && !userIn.equalsIgnoreCase("charlie") && !userIn.equalsIgnoreCase("no"))
                System.out.println ("Invalid input. Please try again.");
        }
        String seatPrice = "null";
        String seatGroup = "null";
        String seat = "null";
        if (!userIn.equalsIgnoreCase("no")) {
            if (userIn.equalsIgnoreCase("alpha")) {
                seatPrice = Integer.toString(newPrice[0]);
                seatGroup = newGroup [0];
                seat = newSeat[0];
            }
            else if (userIn.equalsIgnoreCase("bravo")){
                seatPrice = Integer.toString(newPrice[1]);
                seatGroup = newGroup [1];
                seat = newSeat[1];
            }
            else if (userIn.equalsIgnoreCase("charlie")) {
                seatPrice = Integer.toString(newPrice[2]);
                seatGroup = newGroup [2];
                seat = newSeat[2];
            }
            if (status.equals("downgrade"))
                System.out.println ("Would you like to receive $" + (Integer.parseInt(seatPrice)*-1) + " to have a downgraded seat of " + seat + "?");
            else if (status.equals("upgrade"))
                System.out.println ("Would you like to spend $" + seatPrice + " to have an upgraded seat of " + seat + "?");
            else
                System.out.println ("Would you like to have an upgraded seat of " + seat + "?");
            userIn = input.next();
            if (userIn.equalsIgnoreCase("yes")) {
                expenses.add ("Upgrade seat, " + seatGroup + " " + seat + ", " + seatPrice);
                outputTxt (expenses, "userExpenses");
                System.out.println ("Your upgraded seat has been added to your receipt.");
                boardingPass [9] = seatGroup;
                boardingPass [11] = seat;
                System.out.println ();
                boardingPass = boardingPass (true, boardingPass);
            }
            else
                System.out.println ("Thank you for considering upgrading your seat.");
        }
        else {
            System.out.println ("Thank you for considering upgrading your seat.");
        }
        System.out.println ();
        return boardingPass;
    }

    /*
        Purpose: display the final receipt of the user
        Pre: one String parameter for the user's name
    */
    public static void lastReceipt (String name) throws IOException{
        ArrayList <String> items = new ArrayList <String> ();
        items = inputTxt ("userExpenses");
        String receiptName [] = new String [items.size()];
        String receiptItem [] = new String [items.size()];
        double receiptPrice [] = new double [items.size()];
        int receiptOrder [] = new int [items.size()];
        for (int i = 0; i < items.size(); i++) {
            String [] line = items.get(i).split (", ");
            if ((items.get(i).startsWith("Tim Hortons")) || (items.get(i).startsWith("Boston Pizza")) || (items.get(i).startsWith("Wahlburger's")))
                receiptOrder[i] = 1;
            else if ((items.get(i).startsWith("Chanel")) || (items.get(i).startsWith("Duty Free")) || (items.get(i).startsWith("Lindt")))
                receiptOrder[i] = 2;
            else if ((items.get(i).startsWith("Hans Christian Anderson")) || (items.get(i).startsWith("Grimm Brothers")) || (items.get(i).startsWith("Jane Austen"))) 
                receiptOrder[i] = 3;
            else if (items.get(i).startsWith("Check in carry on")) 
                receiptOrder[i] = 4;
            else
                receiptOrder[i] = 5;
            receiptName [i]= line [0];
            receiptItem [i] = line[1];
            receiptPrice [i] = Double.parseDouble (line[2]);
        }
        System.out.println ("💲 FINAL RECEIPT");
        for (int k = 1; k < 6; k++) {
            boolean userCompletedActivity = false;
            if (k == 1)
                System.out.println ("🍽  Restaurants:");
            else if (k == 2)
                System.out.println ("🛒 Stores:");
            else if (k == 3)
                System.out.println ("📚 Books: ");
            else if (k == 4)
                System.out.println ("🧳 Checking in the carry on");
            else
                System.out.println ("💺 Changing the seat: ");
            for (int i = 0; i < receiptOrder.length; i++) {
                if (receiptOrder[i] == k) {
                    if (k==4) {
                        if (receiptPrice [i] < 0)
                            System.out.println ("\t " + receiptName[i].toUpperCase() + "\t-$" + Math.abs(receiptPrice[i]));
                        else
                            System.out.println ("\t " + receiptName[i].toUpperCase() + "\t$" + Math.abs(receiptPrice[i]));
                    }
                    else {
                        if (receiptPrice [i] < 0)
                            System.out.println ("\t " + receiptName[i].toUpperCase() + "\t" + receiptItem[i] + "\t-$" + Math.abs(receiptPrice[i]));
                        else
                            System.out.println ("\t " + receiptName[i].toUpperCase() + "\t" + receiptItem[i] + "\t$" + Math.abs(receiptPrice[i]));
                    }
                    userCompletedActivity = true;
                }
            }  
            if (userCompletedActivity == false)
                System.out.println ("\t No expense.");
        }
        System.out.println();
        double userTotal = 0;
        for (int i = 0; i < receiptPrice.length; i++)
            userTotal = userTotal + receiptPrice[i];
        userTotal*=100;
        userTotal = Math.round(userTotal);
        userTotal/=100;
        System.out.println ("TOTAL: $" + userTotal);
        System.out.println ();
        grossIncome (name, userTotal);
    }
    
    /*
        Purpose: determine how the user helped the airport's gross income
        Pre: one String parameter for the user's name, one double parameter for the total expenses of the user
    */
    public static void grossIncome (String name, double userTotal) throws IOException{
        ArrayList <String> airportGrossIncome = inputTxt ("airportIncome");
        airportGrossIncome.add (name + ", " + userTotal);
        outputTxt (airportGrossIncome, "airportIncome");
        double grossIncome = 0;
        double airportIncome [] = new double [airportGrossIncome.size()];
        for (int i = 0; i < airportGrossIncome.size(); i++) {
            String income [] = airportGrossIncome.get(i).split (", ");
            airportIncome[i] = Double.parseDouble (income [1]);
            grossIncome = airportIncome[i] + grossIncome;
        }
        double percentage = userTotal/grossIncome * 100;
        percentage*=100;
        percentage = Math.round(percentage);
        percentage/=100;
        grossIncome*=100;
        grossIncome = Math.round(grossIncome);
        grossIncome/=100;
        if (userTotal < 0) {
            System.out.println ("You helped the airport gain -$" + Math.abs(userTotal) + ".");
        }
        else {
            System.out.println ("You helped the airport gain $" + userTotal + ".");
        }
        System.out.println ("This means that you are responsible for " + percentage + "% of the airport's current gross income ($" + grossIncome + ")!");
        System.out.println ();
    }

    /*
        Purpose: display the time breakdown of the user
    */
    public static void displayTime () throws IOException{
        ArrayList <String> times = inputTxt ("timeBreakdown");
        String [] activity = new String [times.size()];
        String [] location = new String [times.size()];
        String [] time = new String [times.size()];
        System.out.println ("⌚ Here is the breakdown of your times: ");
        for (int i = 0; i < times.size(); i++) {
            String [] line = times.get(i).split ("; ");
            activity [i] = line [0];
            location [i] = line [1];
            time [i] = line[2];
            if (Integer.parseInt(time[i]) < 1000)
                time [i] = "0" + time[i];
            if (activity[i].equals ("Start up") && (location[i].equals("UTC"))) {}
            else if (location[i].equals(" ") || location[i].equals("N/A"))
                System.out.println ("\t" + time[i] + ": " + activity[i].toUpperCase());
            else {
                System.out.println ("\t" + time[i] + ": " + activity[i].toUpperCase() + "\t" + location[i]);
            }
        }
    }

    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String RESET = "\u001B[0m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
}
