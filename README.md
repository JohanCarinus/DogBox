# DogBox
[![Build Status](https://app.bitrise.io/app/0df7e49f029e636c/status.svg?token=AAsNbxo1P-ZZpwi5rBPdOg&branch=dev)](https://app.bitrise.io/app/0df7e49f029e636c)

This is my technical assessment for the Android developer role at The Delta. It uses the following technologies to provide an app that can view images from the [Dog API](https://dog.ceo/dog-api/):

1. Jetpack LiveData (MVVM)
2. Jetpack Navigation (Navigation between fragments)
3. Jetpack View Binding
4. Android KTX (General Utility)
5. Hilt (Dependency injection)
6. Material Components (UI)
7. Retrofit (REST requests)
8. Coil (Image loading)

Initially the project was done with Jetpack compose, but I went back to XML based UI after some issues with data leaks in the lazy layouts. The app uses Jetpack Live Data to facilitate a MVVM architecture.

## Demo
The demo shown in this video was for an API 28 level device.

[![Watch the demo](https://img.youtube.com/vi/K9zbpQYtyUs/default.jpg)](https://www.youtube.com/watch?v=K9zbpQYtyUs)

## Miscellaneous notes
Logo "borrowed" from here: https://www.vectorstock.com/royalty-free-vector/dog-box-logo-vector-25336090
