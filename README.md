ALC4.0 Project 2
================

### Travelmantics

This App shows a list of Travel Deals to various locations, first time you login you are presented with a login screen with two forms of
authentication: Google SignIn & Email and Password.

After the Login two things can happen:
### Authentication

If you are a regular user: you will be presented with a list of travel deals and their prices.
If you authenticate as Administrator, you will be granted authorization to not only Read but Insert, Delete, and Update the Travel Deals. 

### Data Storage
Authentication is managed through Firebase Authentication.
All data and pictures are stored using Firebase's Firestore and Firebase Storage.

### Regular user side

A regular user only has READ authorization, he can view the Travel Deals.

![1](https://user-images.githubusercontent.com/38020305/62665882-343f6380-b981-11e9-9795-5da4bccd51bb.png)
![2](https://user-images.githubusercontent.com/38020305/62665887-3a354480-b981-11e9-802d-ac91b0af6aae.png)
![4](https://user-images.githubusercontent.com/38020305/62665891-3bff0800-b981-11e9-8102-8fe0eef1bcec.png)
![5](https://user-images.githubusercontent.com/38020305/62665893-3e616200-b981-11e9-8c1d-37aa68887993.png)

### Administrator side

The Administratod has authorization to perform CRUD on all of the Travel Deals.

![6](https://user-images.githubusercontent.com/38020305/62665974-add75180-b981-11e9-94b2-575aa0a18bff.png)
![10](https://user-images.githubusercontent.com/38020305/62431041-2ccf4e80-b724-11e9-9517-63ecfa306ba9.png)
![11](https://user-images.githubusercontent.com/38020305/62431042-2d67e500-b724-11e9-850e-8bb4e4ad1ed7.png)

## Built With

* [Android Jetpack](https://developer.android.com/jetpack/?gclid=Cj0KCQjwhJrqBRDZARIsALhp1WQBmjQ4WUpnRT4ETGGR1T_rQG8VU3Ta_kVwiznZASR5y4fgPDRYFqkaAhtfEALw_wcB) - Suite of libraries, tools, and guidance to help developers write high-quality apps easier. 
* [Kotlin](https://kotlinlang.org/) - Cross-platform, statically typed, general-purpose programming language with type inference.
* [Firebase](https://firebase.google.com/) - Firebase mobile and web application development platform. 
* [Picasso](https://http://square.github.io/picasso/) - Image downloading and caching library.
* [Material](https://material.io/) - Material design system.

### Prerequisites

To run this code you will need:

```
Android Studio 3.2,
Kotlin 1.3.41,
A stable internet connection.
```

## Authors

* **Doilio A. Matsinhe**  - *Contact me on* - [Twitter](https://twitter.com/DoilioMatsinhe) , [Instagram](https://www.instagram.com/doiliomatsinhe/) , [LinkedIn](https://www.linkedin.com/in/doilio-matsinhe)
