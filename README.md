# Android Application implementing MVVM Architecture Pattern
An Experimental To Do List Android Application which implementing MVVM Architecture Pattern to obtain live data from database
 
## Explanation
### MVVM
An architecture pattern to ease maintain and scale code. The way it work is rather different than **MVC** and **MVP**, **MVVM** is binding it's component in one direction, e.g. _View_ bind _ViewModel_, but not vice versa. Then, how would _View_ get information from _Model_ if _ViewModel_ did not bind it's _View_? The answer is _View_ observing _ViewModel_, so then _View_ will get realtime update on data changed 

## Features & Notes:
- Simple CRUD operation on To Do List

## Tech Stack:
- Java Language
- Android SDK Version : 28
- UI : Card View, add following implementation to app build.gradle
```
def android_support_version = "28.0.0"

implementation "com.android.support:cardview-v7:$android_support_version"
implementation "com.android.support:design:$android_support_version"
```
- Architecture : Model View ViewModel (MVVM), implement following arch library to enable MVVM on app build.gradle 
```
def lifecycle_version = "1.1.1"
def room_version = "1.1.1"

implementation "android.arch.lifecycle:extensions:$lifecycle_version"
implementation "android.arch.persistence.room:runtime:$room_version"
annotationProcessor "android.arch.lifecycle:compiler:$lifecycle_version"
annotationProcessor "android.arch.persistence.room:compiler:$room_version"
```