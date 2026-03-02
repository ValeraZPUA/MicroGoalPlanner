# Architecture Blueprint

## Stack
- Platform: Android 8+
- Language: Kotlin
- Pattern: MVVM
- Storage: Room
- Navigation: AndroidX Navigation + BottomNavigation
- Background jobs: WorkManager
- Rating: In-App Review API

## Core flows
1. **Preloader** → initialize local dependencies, load theme/session.
2. **Onboarding (2 screens)** → explain micro-planning and progress tracking.
3. **Main app shell** → tabs: Home, Goals, Stages, Analytics, Settings.

## Validation policy
- Text: 2..64 chars, letters/digits/spaces.
- Number: positive, max 9 digits.
- Date: today or later.
- Selection: required.

## Offline-first
- Local persistence in Room.
- Background sync can be attached later.
