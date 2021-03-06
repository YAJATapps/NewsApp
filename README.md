# News App

This is an opensource news app written with Kotlin that runs on Android 5.0 and above.

It uses https://newsapi.org to fetch news. 

The app will use a default API key that is built in into the app but if that key reaches its limit, then it will ask the user to get a new key from https://newsapi.org and use it to fetch the news.

It uses the MVVM Design Pattern, Room database to cache recent news on homepage.

Retrofit library is used to fetch data from the API. Glide image loading library to load the images onto the image views.

It has option to use sensors to change the layout of the app, such as it can use shake detection to swap between various views such as list view, grid view etc. on news views only.

It can search news related to some topic, has option for news from specific categories and channels.

The v1 branch of this repo is for version 1 release of this app.

APK: https://github.com/YAJATapps/NewsApp/releases/download/v1/NewsApp_v1.apk

Developer email: yajatapps3@gmail.com

<!-- Images to show in github readme -->
<img src="https://github.com/YAJATapps/NewsApp/raw/main/screenshots/banner.png " width="500">

<img src="https://github.com/YAJATapps/NewsApp/raw/main/screenshots/home.png " height="500">

<img src="https://github.com/YAJATapps/NewsApp/raw/main/screenshots/categories.png " height="500">

<img src="https://github.com/YAJATapps/NewsApp/raw/main/screenshots/channels.png " height="500">

<img src="https://github.com/YAJATapps/NewsApp/raw/main/screenshots/search.png " height="500">
