# Movie-DB
The Movie DB API implementation With clean architechture

<div align="center">
  <sub>Built with ❤︎ by
  <a>Muhammad Salman Aziz</a>
</div>
<br/>

## Features
* Home Screen showing list of Categories and movies.
* Detail Screen to show more information about a Movie.

## Architecture
* Built with MVVM
* Utilized Usecase, Repository pattern for data.
* Includes valid Unit tests for Repository and Usecases.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying data changes.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [MockK](https://mockk.io) - For Mocking and Unit Testing.
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android.

## Modules
* Network Module to utilize TMDB API and all network related opeations are in this module
* App Module contains the interaction and utilization of above modules

## Observations:
- Recycler view can be used on home to make it dynamic.


## Improvements:
- Add more Unit/UI Tests
- Build UI with Compose

## 👨 Developed By
**Muhammad Salman Aziz**

[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/muhammad-salman-0ab02326/)
