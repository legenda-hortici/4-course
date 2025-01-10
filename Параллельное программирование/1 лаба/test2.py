import pandas as pd
import matplotlib.pyplot as plt

# Функция для загрузки данных из CSV файла
def load_csv(filename):
    data = pd.read_csv(filename)
    return data

# Загрузка данных
data = load_csv('results2.csv')

# Проверка структуры DataFrame
print("DataFrame structure:")
print(data.head())  # Отображаем первые несколько строк

# Проверка заголовков столбцов
print("Columns in DataFrame:")
print(data.columns)

# Убедимся, что названия столбцов соответствуют ожидаемым
data.columns = data.columns.str.strip()  # Удаляем пробелы в заголовках

# Проверка наличия необходимых столбцов
if 'Size' not in data.columns or 'Boost Windows Threads Time' not in data.columns or 'Boost OpenMP Time' not in data.columns:
    raise KeyError("Ожидаемые столбцы отсутствуют в данных.")

# Приведение типов данных
data['Size'] = pd.to_numeric(data['Size'], errors='coerce')
data['Boost Windows Threads Time'] = pd.to_numeric(data['Boost Windows Threads Time'], errors='coerce')
data['Boost OpenMP Time'] = pd.to_numeric(data['Boost OpenMP Time'], errors='coerce')

# Удаляем строки с пропущенными значениями
data.dropna(inplace=True)

# Используем значения из столбцов как ускорение
# Ускорение для Windows Threads
speedup_windows_threads = data['Boost Windows Threads Time']
# Ускорение для OpenMP
speedup_openmp = data['Boost OpenMP Time']

# Создание графика для ускорения
plt.figure(figsize=(10, 6))

# График ускорения для Windows Threads
plt.plot(data['Size'], speedup_windows_threads, marker='o', color='b', label='Ускорение PThreads Time')

# График ускорения для OpenMP
plt.plot(data['Size'], speedup_openmp, marker='o', color='g', label='Ускорение OpenMP')

# Настройки графика
plt.title('Ускорение реализации в зависимости от размера матриц')
plt.xlabel('Размерность матриц')
plt.ylabel('Ускорение')
plt.xticks(data['Size'])  # Установка значений по оси X
plt.grid()
plt.legend()

# Отображение графика
plt.tight_layout()
plt.show()