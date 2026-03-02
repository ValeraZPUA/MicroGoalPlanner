# MicroGoal Planner

Android-приложение для микропланирования финансовых целей (Kotlin, MVVM, Room, WorkManager).

## Реализовано (MVP)
- Прелоадер с инициализацией локальной БД и обработкой ошибок + Retry.
- Онбординг из 2 экранов с сохранением флага завершения.
- Главный shell с `BottomNavigation` (Home, Goals, Stages, Analytics, Settings).
- Локальное хранение данных: `Room` (`goals`, `stages`, `settings`).
- Полноценные CRUD-операции MVP-уровня для целей/этапов (создание, просмотр, поиск, обновление статусов через ViewModel + Repository).
- Базовая аналитика по целям и этапам (кол-во, завершение, прогноз).
- Настройки: валюта, уведомления, очистка локальных данных.
- Напоминания через `WorkManager` (weekly + stage reminder worker).
- Валидация текстов/чисел/дат/изображений.

## Технологии
- AndroidX Lifecycle/ViewModel
- AndroidX Navigation
- Room
- WorkManager
- Material Components
- In-App Review dependency (готово к интеграции в UI)

## Статус
Проект доведен до рабочего архитектурного MVP. Следующий шаг — расширение UI-форм (редактирование/детали), диаграммы аналитики и UX-полировка.
