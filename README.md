# PaybackChallenge


Code Architecture:
- A single Activity architecture, using the Android Jetpack Navigation Component
- A Presentation Layer that contains a fragment(View) and a ViewModel per screen(or feature).
- A data layer with a repository and two data sources(local db using Room and remote). The repository layer handles data operations.
- A domain layer that sits between the data layer and presentation layer. Business logic can be added to this layer.
- Using Kotlin Coroutines and Flow for asynchronous operations.
- Reactive UIs using LiveData and DataBinding.
- Dependency Injection using HILT.
- Unit testing of the Remote API Error Handling.


Important classes and their objectives:
- MainFragment - Show list of Images and captures user interactions.
- MainViewModel - Get the list of images and update different states.
- ImageRepositoryImpl - Request for images from remote, save it to the local database and make data available for the ViewModel via the Use Case.
- GetImageDetailsUseCase - In case business logic (filetering/sorting or marking image as favorite) to be achieved, it can be added here.
- NetworkHelper - All remote API calls done through this. All error handling can be added here.


What more I could have done:
- Handle Pagination.
- More Test coverage.
- Better UI - Styles and themes.
- Regular commits.


What could be tested:
- The saving of images to the local db. (ImageRepositoryImpl)
- The deleting of images from local db. (ImageRepositoryImpl)
- UI states in the ViewModel. (MainViewModel)

Assumptions/Features by design:
- Every app launch search using the query "fruits".
- The local db is cleared with every new search.
