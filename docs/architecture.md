# Architecture Blueprint

## Stack
- Platform: Android 8+
- Language: Kotlin
- Pattern: MVVM
- Storage: Room (`goals`, `stages`, `settings`)
- Navigation: AndroidX Navigation + BottomNavigation
- Background jobs: WorkManager (`ReminderWorker`)
- Rating: In-App Review API dependency added

## Implemented flows
1. **Preloader**: DB init + dependency readiness + retry on error.
2. **Onboarding (2 screens)**: intro + progress/reminders + completion persistence.
3. **Main shell**: tabs Home/Goals/Stages/Analytics/Settings.
4. **Goals**: list/search and quick creation.
5. **Stages**: list/search and creation with goal linkage.
6. **Analytics**: summary/projection from persisted data.
7. **Settings**: currency, notifications, local data reset.
8. **Notifications**: weekly reminder and stage reminder worker scheduling.

## Data layer
- `AppDatabase` + DAOs.
- `PlannerRepository` for business operations and flow aggregation.
- Domain models include status/progress/analytics structures.

## Validation policy
- Text: 2..64 chars, letters/digits/spaces.
- Number: positive, max 9 digits.
- Date: today or later.
- Selection: required.
- Image: JPEG/PNG and <=2MB.
