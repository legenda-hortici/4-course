public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();

        db.addSection("Основы Go", "Изучение базового синтаксиса и структуры Go-программ.");
        db.addSection("Работа с данными", "Типы данных, структуры и коллекции в Go.");

        db.addTopic("Переменные и типы", 26);
        db.addTopic("Функции", 26);
        db.addTopic("Условные операторы", 26);
        db.addTopic("Циклы", 26);
        db.addTopic("Указатели", 26);
        db.addTopic("Пакеты и модули", 26);
        db.addTopic("Массивы и срезы", 27);
        db.addTopic("Карты (map)", 27);
        db.addTopic("Структуры (struct)", 27);
        db.addTopic("Интерфейсы", 27);
        db.addTopic("Методы", 27);
        db.addTopic("Обработка ошибок", 27);

        db.getAllTopicsWithSections();
    }
}
