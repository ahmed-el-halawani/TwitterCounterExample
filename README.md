# Twitter Counter Example 🐦

A native Android application that implements Twitter's specific character counting logic. This project demonstrates the usage of the official **Twitter Text Library** to accurately parse and weigh characters according to Twitter's publishing rules.

<p align="center">
  <a href="https://appetize.io/embed/b_kzbtomvexrmro4wf4cdnrk5lgu?autoplay=true">
    <img src="https://github.com/user-attachments/assets/7424c364-94cd-4f6f-80ed-80cbe653a7ec" width="250" alt="Click to run app" title="Click to run app directly in browser">
  </a>
  <br>
  <em>Click the phone to run the app in your browser</em>
</p>

## 🚀 Features

- **Accurate Character Counting**: Uses the official `twitter-text` library to validate tweet length.
- **Smart Validation**: 
  - Standard characters count as 1.
  - Emojis and specific unicode characters count as 2.
  - URLs are automatically weighted according to Twitter's link shortening rules.
- **Visual Feedback**: Real-time indicator of remaining characters and valid/invalid state.

## 🛠️ Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Core Library**: [Twitter Text Library](https://github.com/twitter/twitter-text) (Java)
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI**: XML / ViewBinding

## 📝 Assignment Notes

### 1. Key Properties
The `key.properties` file was **intentionally** not added to `.gitignore`. This was done to facilitate immediate testing and building of the application without requiring additional configuration steps.

### 2. Twitter Text Library
I utilized the official **Twitter Text Library** provided by Twitter. 
* **Why?** Twitter employs specific criteria for text analysis that standard string length methods cannot replicate.
* **Detail**: Unlike standard methods, this library correctly handles weighted characters (e.g., emojis count as 2), URL shortening, and unicode normalization.

### 3. Architecture & Dependency Injection
* **Dependency Injection (DI)**: A full DI framework (like Hilt or Dagger) was **not implemented** as the project scope is relatively small.
* **Dependency Inversion**: However, the SOLID principle of **Dependency Inversion** has been strictly adhered to, ensuring the code remains testable and decoupled.

## 🧪 How to Run

1. Clone the repository:
   ```bash
   git clone [https://github.com/ahmed-el-halawani/TwitterCounterExample.git](https://github.com/ahmed-el-halawani/TwitterCounterExample.git)
