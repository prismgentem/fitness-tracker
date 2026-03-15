# Fitness Tracker

Учебный Maven-проект на Java 17 для расчета целевой калорийности.

## Требования

- JDK 17
- Maven 3.8+

## Команды

Запуск тестов:

```bash
mvn test
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

## Структура пакетов

- `ru.prisma.fitnesstracker.model` - доменные модели пользователя
- `ru.prisma.fitnesstracker.model.enums` - перечисления пола, активности и цели
- `ru.prisma.fitnesstracker.service` - валидация профиля и расчет калорий
- `src/test/java` - JUnit 5 тесты для валидатора и калькулятора
