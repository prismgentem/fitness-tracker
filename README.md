# Fitness Tracker

Учебный Maven-проект на Java 17 для расчета целевой калорийности.

## Требования

- JDK 17
- Maven 3.8+

## Команды

Запуск тестов:

```bash
mvn -q test
```

Сборка jar:

```bash
mvn package
```

Запуск приложения:

```bash
mvn -q exec:java
```

или

```bash
java -cp target/fitness-tracker-1.0-SNAPSHOT.jar ru.prisma.fitnesstracker.App
```

Запуск HTTP API:

```bash
mvn -q exec:java -Dexec.mainClass=ru.prisma.fitnesstracker.api.FitnessTrackerHttpServer
```

или

```bash
java -cp target/fitness-tracker-1.0-SNAPSHOT.jar ru.prisma.fitnesstracker.api.FitnessTrackerHttpServer
```

## Структура пакетов

- `ru.prisma.fitnesstracker.model` - доменные модели пользователя
- `ru.prisma.fitnesstracker.model.enums` - перечисления пола, активности и цели
- `ru.prisma.fitnesstracker.service` - валидация профиля и расчет калорий
- `ru.prisma.fitnesstracker.api` - локальный HTTP API для системного тестирования
- `src/test/java` - JUnit 5 тесты для валидатора и калькулятора
