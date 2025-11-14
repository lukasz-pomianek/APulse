# APulse Logging System

APulse oferuje teraz kompletny system logowania z interfejsem użytkownika, który integruje się z istniejącym systemem sesji i bazy danych.

## 🎯 Funkcjonalności

### ✅ **Ekrany UI**
- **Lista logów** - przegląd wszystkich logów aplikacji
- **Szczegóły logu** - pełny widok pojedynczego logu z możliwością kopiowania
- **Filtry** - po poziomie (DEBUG, INFO, WARN, ERROR), tagach, wyszukiwanie tekstowe
- **Nawigacja** - zintegrowana z główną aplikacją APulse

### ✅ **API w module apulse-core**

#### Podstawowa konfiguracja

```kotlin
import com.apulse.core.APulse

// Inicjalizacja APulse (zwykle w Application.onCreate())
APulse.initialize(applicationContext) {
    enableNotifications = true
    maxStorageSize = 100 * 1024 * 1024 // 100MB
}

// Utworzenie loggera
val logger = APulse.createLogInterceptor(context)
```

### Przykłady użycia

```kotlin
// Różne poziomy logowania
logger.d("MyTag", "Debug message")
logger.i("MyTag", "Info message")  
logger.w("MyTag", "Warning message")
logger.e("MyTag", "Error message", exception)

// Bezpośrednie użycie z priorytetem
logger.log(Log.INFO, "MyTag", "Custom message", null)
```

## Funkcjonalności

### 🏗️ Architektura
- **Singleton pattern** - jeden interceptor na aplikację
- **Factory pattern** - tworzenie przez APulse.createLogInterceptor()
- **Interface-based** - APulseLogInterface dla łatwego testowania

### 📊 Dane zapisywane w bazie
Każdy log zawiera:
- `priority` - poziom logowania (DEBUG, INFO, WARN, ERROR)
- `tag` - tag identyfikujący moduł/klasę
- `message` - treść wiadomości
- `error` - wiadomość błędu (jeśli jest wyjątek)
- `stackTrace` - pełny stack trace (jeśli jest wyjątek)
- `sessionId` - powiązanie z aktualną sesją APulse
- `timestamp` - czas utworzenia logu
- `threadName` - nazwa wątku
- `className`, `methodName`, `lineNumber` - informacje o miejscu wywołania

### 🔍 Indeksy bazy danych
Automatyczne indeksy dla szybkiego wyszukiwania:
- `sessionId` - filtrowanie po sesji
- `priority` - filtrowanie po poziomie
- `tag` - filtrowanie po module
- `timestamp` - sortowanie chronologiczne

### 🎯 Integracja z sesją
- Automatyczne powiązanie z aktualną sesją APulse
- Logi są grupowane według sesji w UI
- Możliwość eksportu logów razem z requestami HTTP

## Korzyści

✅ **Zunifikowane logowanie** - wszystkie logi w jednej bazie  
✅ **Kontekst sesji** - każdy log powiązany z sesją debugowania  
✅ **Rich metadata** - pełne informacje o miejscu wywołania  
✅ **Stack traces** - automatyczne przechwytywanie wyjątków  
✅ **Performance** - async saving, indeksowana baza  
✅ **Compatibility** - API zgodne z Android Log  

## Przykład w praktyce

```kotlin
class MyService(context: Context) {
    private val logger = APulse.createLogInterceptor(context)
    
    fun processData(data: String) {
        logger.i("DataProcessor", "Starting data processing")
        
        try {
            val result = complexOperation(data)
            logger.d("DataProcessor", "Processing completed: $result")
        } catch (e: Exception) {
            logger.e("DataProcessor", "Processing failed for: $data", e)
        }
    }
}
```

Wszystkie logi będą automatycznie zapisane w bazie danych i dostępne w UI APulse wraz z requestami HTTP z tej samej sesji debugowania.

## 📱 Interfejs użytkownika

### Nawigacja
W APulse znajdziesz nową zakładkę **"Logs"** w dolnej nawigacji obok:
- **Requests** - requesty HTTP  
- **Logs** ← NOWE!
- **Sessions** - sesje debugowania
- **Settings** - ustawienia

### Lista logów
- 📊 **Filtrowanie** - po poziomie logowania, tagach 
- 🔍 **Wyszukiwanie** - w treści wiadomości i tagach
- 🎨 **Kolorowanie** - według poziomów (DEBUG=niebieski, INFO=zielony, WARN=pomarańczowy, ERROR=czerwony)
- ⏱️ **Sortowanie** - chronologiczne od najnowszych
- 🔄 **Odświeżanie** - przycisk do przeładowania
- 🗑️ **Czyszczenie** - usuwanie wszystkich logów

### Szczegóły logu
- 📋 **Kopiowanie** - cały log do schowka  
- 📝 **Pełne dane** - priority, tag, message, error, stack trace
- 🔍 **Metadata** - timestamp, thread, class, method, line number
- 🎯 **Session ID** - powiązanie z sesją debugowania

## 🚀 Szybki start

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 1. Inicjalizacja APulse
        APulse.initialize(this)
        
        // 2. Utworzenie loggera
        val logger = APulse.createLogInterceptor(this)
        
        // 3. Użycie
        logger.i("App", "Application started")
    }
}
```

Teraz w aplikacji APulse będziesz mieć zakładkę **Logs** z pełnym interfejsem do przeglądania i filtrowania logów!