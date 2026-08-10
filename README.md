# Sport Quizz — Android Native App

Native Android app (Kotlin + Jetpack Compose) matching the HTML version: sport trivia quiz and penalty kick mini-game.

## Features

- **Splash screen** — spinning soccer ball animation
- **Sport quiz** — 15 questions with progress bar, scoring, and answer feedback
- **Penalty kicks** — 5-shot shootout with keeper and ball animations
- **Dark theme** — same red accent styling as the HTML app

- **Push notifications** — OneSignal SDK integrated

## OneSignal setup

1. Create an app at [OneSignal](https://onesignal.com) and copy your **App ID** (Dashboard → Settings → Keys & IDs).
2. Configure **Google Android (FCM)** in OneSignal (Settings → Push & In-App) — upload Firebase **Service Account JSON** (the file you uploaded in the screenshot).
3. In **Firebase Console** (`championway-dad44`), add Android app with package **`com.championwayappsprtchapwayway`**, then download **`google-services.json`** and place it in the **`app/`** folder.
4. Replace the placeholder in `app/src/main/res/values/strings.xml`:

```xml
<string name="onesignal_app_id" translatable="false">YOUR_ONESIGNAL_APP_ID</string>
```

The SDK initializes in `SportQuizzApplication` on app launch.

### In-app message on startup

After a **6 second** loading screen, the app triggers a OneSignal in-app message.

In OneSignal Dashboard → **Messages → In-App → New In-App**:

1. Create your popup content
2. Under **Triggers**, add: `app_loaded` **is** `true`
3. Publish the message

The app pauses in-app messages during loading, then calls `OneSignal.InAppMessages.addTrigger("app_loaded", "true")` when loading finishes.

## Requirements

- Android Studio Ladybug (2024.2+) or newer
- JDK 17
- Android SDK 35

## Build & Run

1. Open the project folder in Android Studio
2. Sync Gradle
3. Run on an emulator or device (API 26+)

From the command line:

```bash
./gradlew assembleDebug
```

Install the debug APK:

```bash
./gradlew installDebug
```

## Project structure

```
app/src/main/java/com/championwayappsprtchapwayway/
├── MainActivity.kt          # Navigation & app state
├── SportQuizzApplication.kt # OneSignal initialization
├── data/QuizData.kt         # Questions & result messages
├── ui/
│   ├── components/          # Shared UI (cards, buttons, background)
│   ├── screens/             # Splash, Home, Quiz, Result, Penalty game
│   └── theme/               # Colors & Material theme
```

The original HTML prototype remains in `index.html` at the project root.
