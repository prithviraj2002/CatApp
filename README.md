

# Jet Cats 🐱

![20250618_1509_Playful Cat App_simple_compose_01jy16e0hgfdn8ftjeanrc50qg](https://github.com/user-attachments/assets/27aee7bd-af07-425d-bb79-0e3437ca7923)

Welcome aboard all cat lovers.

Research suggests that looking at cat images, especially cute ones, can reduce stress and improve mood. Studies have shown that viewing cat videos and pictures can lead to increased positive emotions and a decrease in negative feelings. This effect is so significant that some researchers even suggest that it could be used as a form of digital pet therapy or stress relief.

Being a cat lover myself, I thought why not create an app out of it.

This app also had its learning curves, as I was revisiting Jetpack Compose after a long time. Making api calls with Retrofit, implementing local storage with RoomDB, and using hilt for dependency injection, I have tried to use best practices as much as possible. 

## 🧰 Tech Stack

- **Jetpack Compose** – Modern UI toolkit
- **Hilt** – Dependency injection
- **Room** – Local database
- **Retrofit** – Networking
- **Kotlin Coroutines & Flow** – Asynchronous data handling
- **Google Fonts** – Dynamic font loading
- **Material3** – Latest design system components


## Screenshots

<p float="left">
  <img src="https://raw.githubusercontent.com/prithviraj2002/CatApp/refs/heads/main/screenshots/home.png" width="220" height="480"/>
  <img src="https://raw.githubusercontent.com/prithviraj2002/CatApp/refs/heads/main/screenshots/explore.png" width="220" height="480"/>
  <img src="https://raw.githubusercontent.com/prithviraj2002/CatApp/refs/heads/main/screenshots/funny.png" width="220" height="480"/>
</p>

<p float="left">
  <img src="https://raw.githubusercontent.com/prithviraj2002/CatApp/refs/heads/main/screenshots/fav.png" width="220" height="480"/>
  <img src="https://raw.githubusercontent.com/prithviraj2002/CatApp/refs/heads/main/screenshots/breed_details.png" width="220" height="480"/>
  <img src="https://raw.githubusercontent.com/prithviraj2002/CatApp/refs/heads/main/screenshots/image_details.png" width="220" height="480"/>
</p>


## ✨ Features

A modern Android app built using **Jetpack Compose**, **Room Database**, and **Hilt** that allows users to explore, search, and favorite cat breeds and images.

- 🔍 **Explore Tab**  
  View a grid of cat breeds and search by name with live results.

- 📥 **Favorites**  
  Save and view favorite cat images locally using Room Database.

- 🌐 **Network Integration**  
  Fetch cat images and breed data using Retrofit.

- 🔄 **Pull-to-Refresh**  
  Easily refresh data with swipe down gesture.

- 💾 **Offline Storage**  
  Favorites are stored locally using Room and persist across launches.

- 💅 **Material You Theming**  
  Dynamic color support with Google Fonts integration.
## 🔨 Structure

data/
- network/
- db/
- dao/
domain/
- repository/
- model/
presentation/
- viewmodel/
- ui/
- components/
di/
- NetworkModule.kt


- **ViewModel** manages UI state using `StateFlow`.
- **Repositories** abstract DB and network logic.
- **DAOs** handle local data persistence.
- **Retrofit** interfaces fetch data from API.


## 🗼 Architecture

Data --> Domain --> Presentation

Unidirectional reverse flow of data.

Cat Interface --> Cat Repo --> Cat ViewModel --> CatView

For Favourites

RoomDB --> CatDao --> SavedImageRepo --> ViewModel --> UI View
## ✅ Roadmap

 - Add pagination

 - Dark mode toggle

 - Share image functionality

 - Unit tests


## 🛠️ Run Locally

- Clone the project

```bash
  git clone https://github.com/prithviraj2002/CatApp
```

- Get the branch
   ```bash
   cd cat-app

- Open in Android Studio

- Run on emulator or device

- Make sure you’re connected to the internet to load API data.

#### 🔑 API
Uses TheCatAPI.
To use your own key, add it to your baseUrl in Endpoints if required.


## Acknowledgements

 - [The cat api](https://thecatapi.com/)
 - [Compose actors](https://github.com/RajashekarRaju/compose-actors?tab=readme-ov-file)
 - [Android docs](https://developer.android.com/compose)


## License

MIT License

Copyright (c) 2025 Prithviraj Kapil

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

