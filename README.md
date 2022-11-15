# Team 2127- Tech Innovators

## Real-time person-to-person money transfer solution based on open source payment Platform on Azure.
Creating a payment application like Venmo, Zelle on Mojaloop, an open source payment platform, using Microsoft Azure.

# Release Notes

## Version: 0.4.0 Released: 11/14/2022
### Features

- [x] The Front-End and the Back-End are now connected.
- [x] The user can register an account through the actual application.
- [x] The user can log in through the actual application.
- [x] The user's name displays on the Main Page. No more Jane Doe
- [x] Friend page is implemented.
- [x] The users now can send friend requests to other users.
- [x] Once accepted, the friend list displays the friends who accepted the friend request.


### Known-Issues

- [ ] Mojaloop is still not working. (Cannot find a way to receive a response from the central-ledger service)
- [ ] Accepting/Declining a friend request is not implemented properly.
- [ ] The friend request should be accepted/declined through the database or postman.
- [ ] The friend list somtimes doesn't show up



### Bug Fixes
- [x] The friend button overlapping problem is fixed.
- [x] Hajin Having a branch issue is fixed
- [x] The database conflict is fixed. Now using the updated database won't hurt us
- [x] Fixed the not-responding back-end issue.
- [x] Mojaloop's pods being broken fixed.
- [x] No Mojaloop endpoint issues are resolved

    
## Version: 0.3.0 Released: 10/24/2022
### Features

- [x] The Back-End Spring Boot server is deployed and working.
- [x] H2 database is constructed and ready to store the user-related information.
- [x] Friend-related features are implemented. The user now can add/remove friends.
- [x] The transactions can be made, however, "Real" transactions cannot be made due to an issue with Mojaloop.
- [x] API Test tool kit is implemented to test the API calls and simulate transactions.
- [x] Payee Information, transaction page are now implemented.


### Known-Issues

- [ ] Front-End failed to call the APIs from Back-End
- [ ] Account Look-up Service of Mojaloop is not responding


### Bug Fixes
- [x] Central-Ledger Pod of Mojaloop was not deploying. =>
    All pods of Mojaloop is working now.
- [x] Some pages had an issue with layouts =>
    Now the layouts are squared away and displaying correctly. They are in the right spot now!

# Release Notes

## Version: 0.2.0 Released: 10/02/2022
### Features

- [x] The user now can access to the create account page.
- [x] The user can register and login.
- [x] Now the registration requires the phone number of the user.
- [x] The transitions between pages are implemented.
- [x] The user can now enter the Payee information.
- [x] The user can access the "help/instructions" page.


### Known-Issues

- [ ] transactions cannot be made yet.
- [ ] Email format, length restrictions are not implemented.


### Bug Fixes
- [x] App is not working properly. =>
    The app is now working
- [x] The layouts of certain pages were not displaying correctly on different devices. =>
    Now the layouts are squared away and displaying correctly. They are in the right spot now!
- [x] Screen transition is not functioning. =>
    The screen transition function is correctly performing.
- [x] Pressing sign-in button stopped the application and exited out of the applicaion. => Now the sign-in button is correctly functioning.
    

## New Version: 0.1.0 Released: 09/21/2022
  Re-built the whole application due to the critical app building failure issue.
  Changed the front-end language from React-Native to Java
  
### Features
- [x] Login Page is showing

### Known-Issues
- [ ] App is not working properly.

### Bug Fixes
- [ ] N/A (No bugs found!)


# Below is the previous release notes we used. Archived for recording purpose

## Release Notes

### Version: 0.2.0 Released: 09/11/2022
#### Features
- [x] Splash Screen showing up when boot
- [x] Email/Password Restrictions added
- [x] Exit instruction is shown when trying to exit the application.
- [x] "Back" button is functioning correctly
- [x] Removed Registration Page

#### Known-Issues
- [ ] Social Login is not correctly functioning

#### Bug Fixes
- [x] ~~Occasioanlly unable to build the app~~
    The app is now working

### Version: 0.1.0 Released: 05/28/2022

#### Features
- [x] Login Page is showing
- [x] Registration Page is showing

#### Known-Issues
- [ ] Occasioanlly unable to build the app

#### Bug Fixes
- [ ] N/A (No bugs found!)
