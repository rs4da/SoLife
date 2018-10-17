Disclaimer: this repository is a copy of a private Mobile App Development course repo and is for demonstration purposes only.  


CS 4720 - S18 - Final Project

Device Name: Nexus 7		Platform: Android

Name:  Roman Sharykin		Computing ID: rs4da

Name:  Andrew Shi			Computing ID: as4ac

App Name: SoLife

Project Description:  

Our app, SoLife, allows social organisations to easily host and manage events and gives students the ability to organise and attend their social outings in a comprehensive manner. Most students at some point in their college career get invited to events that are being hosted in difficult to find locations or students have a free night and would like to attend a social event, but do not know what events are being held that day. Organisations, on the other hand, often have to deal with massive and very clunky attendee lists while checking admission at the door. 

We set out to create an app that:

Allows students to create a password-secured account;
Shows students a map of events happening nearby within the next 24 hours;
Displays the title, start times, and end times of events;
Allows students to create and manage organizations;
Allows organizations to create events, both public and private;
Allow organizations to create invitation lists for events;

Features:

GPS/Location - Display the locations of public and invited private events
Camera QR scanner - The bouncer (or event manager) can scan the QR codes sent to private event invitees for quick authentication 
Firebase - Store events, event details (location, title, times), invite lists
Consume a pre-built web service - Geocode API to convert human-readable addresses into map-usable latitude and longitude coordinates
Data storage using key/value pair storage (SharedPreferences) - Stores user login information

Platform Justification: 
The benefits of choosing Android as our app’s platform come primarily in the flexibility we are allowed in the app’s design. We can define new and slightly modify existing layouts to match whatever we need without having to follow preset guidelines. In addition, Android offers a more appealing developer platform, since the fees are once for a lifetime and less than those of iOS.

Major Features/Screens
On startup, the user is greeted with a login screen. Here you can enter login information, or if you’ve already logged in before, your information will be autocompleted. There are two buttons, one which allows login, and one which takes you to an account registration screen. The account registration screen looks similar, but with a single button that registers the account with the database. Once logged in/registered, the user is taken to the Maps tab of the home screen. Here, all events that the signed in user is invited to (meaning on invite list or public) will appear as red indicators that can be clicked on for more information. Clicking on one of these and then long holding the box that appears takes you the focus screen, where the event details and related QR code can be seen.
Alternatively, at the bottom of the home screen are also two other tabs: List and Camera. Tapping on List takes you to the events list screen, which displays all the events listed but in a compact, easily readable list format. The name of the event, the host, and the date/time information can be seen at a glance. Tapping on Camera takes you to the scanner screen, which has a single button which can be used to verify the QR codes of incoming guests.

Optional Features
GPS/Location: The button on the top right of the map home screen centers on the user’s location, relative to the events happening so they can easily find their way there.
Camera: Tap the camera tab and then scan to access the camera. It looks for a QR code of an event. Once found, it should display the event name.
Firebase: Logins are authenticated from Firebase; events are also pulled from Firebase. You can’t really see this in the app unless multiple people are using it, but you can check the code of the MapsActivity and EventsActivity.
SharedPreferences: The log in details a user that previously signed in to or registered with the app are stored in shared preferences and the Login screen is autofilled when a user opens the app again. To test this, register a new account, load into the home screen, then close the app and reopen it.
Consume a pre-built web service: Check the maps home screen. The points that appear have their latitude and longitude given from a Geocoding API. Again, you can’t really see this in the app unless you look at the code, but it is in the code.
QR code generator and scanner: Every time a user clicks on event details (Focus Activity) a QR code is automatically generated for that specific event. This QR code can then be scanned using the Scanner Activity.  
Testing Methodologies
We ran our app on multiple Android devices (phones and tablets) and used the emulator. Logcat and Log.d() were used extensively. We also used multiple devices to upload and download firebase data to ensure that the database was being updated properly and in real time. We also manually changed some of the values in firebase to ensure the correctness of our methods. 

Usage
Register a new account to log in. Otherwise, one account is as4ac@virginia.edu, with the password “desudesuX3”.
To access the detail screen from the maps, first tap the red pointer, then long hold the box that appears.
The floating action button at the top allows you to create a new event.
The bottom navigation bar has three tabs: the map tab which is the default, the list tab which displays all the events a user is invited to, and a scanner tab which lets a host authenticate users by scanning their barcode. 
To get you barcode, either use instructions in 2) or navigate to the list tab and tap on the eye icon beside the event. This will take you to the event details screen. 


Lessons Learned
The main lesson learned was that app development is hard! However we learned that the android community is very helpful and most issues can be resolved with the help of external libraries. Location after Android API 21 requires jumping through way more hoops than it used to; instead of a single line it became a whole new file and 3 new methods. Nav bars are really difficult and seem to work best with fragment as opposed to activities. Using Google Maps on an emulator is a bit tricky; using an actual device is easiest.


