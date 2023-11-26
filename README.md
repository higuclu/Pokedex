# Pokedex
An application used dependency injection with clean architecture 

## Used Technologies
- Retrofit2
- Glide 
- Dagger Hilt
- Kotlin Coroutines
  
## Clean Architecture

This project follows clean architecture principles to ensure a separation of concerns and maintainability.

1. **Domain Layer**: Contains business logic and use cases, independent of the Android framework.

2. **Data Layer**: Manages data access, including repositories for handling local and remote data sources.

3. **Presentation Layer**: Responsible for UI logic and presentation, interacting with the domain layer via use cases.

4. **Dependency Injection**: Utilized Dagger Hilt for dependency injection, facilitating modularity and testability.

## Screenshots

### HomeScreen

<img src="https://github.com/higuclu/Pokedex/assets/53168447/f60195c6-838e-430e-ae7b-6d039bab9080.png" alt="image" width="25%" height="25%">

<img src="https://github.com/higuclu/Pokedex/assets/53168447/d1fdec65-1c11-437b-9f21-ae8031e4fc2e.png" alt="image" width="25%" height="25%">

<img src="https://github.com/higuclu/Pokedex/assets/53168447/fc6d11c2-5730-4d9f-99e4-c98f2b313afa.png" alt="image" width="25%" height="25%">

### DetailScreen

<img src="https://github.com/higuclu/Pokedex/assets/53168447/90d506a2-873d-45ac-802d-078bb9c1a7a2.png" alt="image" width="25%" height="25%">

<img src="https://github.com/higuclu/Pokedex/assets/53168447/b0fae8f9-062a-4d3c-9e56-980a5cc8427f.png" alt="image" width="25%" height="25%">

<img src="https://github.com/higuclu/Pokedex/assets/53168447/cdcf7003-b3e0-456d-9a88-d10331d56e4c.png" alt="image" width="25%" height="25%">


## TODOs

- [ ] Load to scroll pagination logic in listing pokemons
