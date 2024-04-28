Country Listing Application

The Country Listing Application is designed to display a list of countries along with their details. It leverages various technologies to provide a seamless user experience.

Technologies Used

Kotlin: 
  Kotlin serves as the primary programming language for developing the Country Listing Application. It offers concise syntax, null safety, and interoperability with existing Java codebase.
  
MVVM Architecture: 
  The application follows the Model-View-ViewModel architectural pattern. MVVM separates the user interface logic from the business logic, making the codebase modular, maintainable, and testable.
  
Room Database: 
  Room is utilized as the local database solution. It provides an abstraction layer over SQLite database and offers compile-time SQL validation, ensuring efficient and reliable database operations.
  
Retrofit: 
  Retrofit is employed for networking calls to fetch country data from external APIs. It simplifies the process of making HTTP requests and processing responses asynchronously.
  
Coroutines: 
  Coroutines are used for background tasks such as network requests and database operations. Coroutines simplify asynchronous programming by providing a structured way to perform concurrent operations.
  
Hilt: 
  Hilt is utilized for dependency injection, facilitating the management of dependencies and promoting code modularity. It simplifies the setup of DI and enhances code readability and maintainability.

public api used: https://api.sampleapis.com/countries/countries


