// Функция для открытия модального окна
function openOrderModal() {
  const modal = document.getElementById("orderModal");
  modal.style.display = "block"; // Показать модальное окно
}

// Функция для закрытия модального окна
function closeOrderModal() {
  const modal = document.getElementById("orderModal");
  modal.style.display = "none"; // Скрыть модальное окно
}

// Функция для отправки заявления
function submitOrder(event) {
  event.preventDefault(); // Отменить стандартную отправку формы

  // Сбор данных из формы
  const userName = document.getElementById("user-name").value.trim();
  const email = document.getElementById("email").value.trim();
  const country = document.getElementById("country").value;
  const citySelect = document.getElementById("city");
  const city = citySelect.options[citySelect.selectedIndex].textContent.trim(); // Получаем отображаемый текст города
  const userPhone = document.getElementById("user-phone").value.trim();
  const orderCheck = document.getElementById("order-check").checked;

  // Проверка данных
  if (
    !validateForm(userName, email, country, city, userPhone, orderCheck)
  ) {
    return;
  }

  // Формируем строку с данными заказа
  const orderSummary = `
    <strong>Имя:</strong> ${userName}<br>
    <strong>Email:</strong> ${email}<br>
    <strong>Страна:</strong> ${country === "russia" ? "Россия" : ""}<br>
    <strong>Город:</strong> ${city}<br>
    <strong>Телефон:</strong> ${userPhone}<br>
  `;

  // Показываем подтверждение заказа
  document.getElementById("order-summary").innerHTML = orderSummary;
  closeOrderModal(); // Закрываем модальное окно
  document.getElementById("orderConfirmation").style.display = "block"; // Показываем окно с подтверждением
}

// Функция для закрытия окна подтверждения
function closeConfirmation() {
  document.getElementById("orderConfirmation").style.display = "none"; // Закрыть окно подтверждения
}

// Функция для обновления городов на основе выбранной страны
function updateCities() {
  const country = document.getElementById("country").value;
  const citySelect = document.getElementById("city");

  // Очистка списка городов
  citySelect.innerHTML = "<option value=''>Выберите город</option>";

  if (country === "russia") {
    const cities = [
      { value: "Москва", name: "Москва" },
      { value: "Санкт-Петербург", name: "Санкт-Петербург" },
      { value: "Самара", name: "Самара" },
    ];
    cities.forEach((city) => {
      const option = document.createElement("option");
      option.value = city.name;
      option.textContent = city.name;
      citySelect.appendChild(option);
    });
  } else if (country === "belarus") {
    const cities = [
      { value: "Минск", name: "Минск" },
      { value: "Гомель", name: "Гомель" },
      { value: "Брест", name: "Брест" },
    ];
    cities.forEach((city) => {
      const option = document.createElement("option");
      option.value = city.name;
      option.textContent = city.name;
      citySelect.appendChild(option);
    });
  }
}

// Функция для валидации данных
function validateForm(
  userName,
  email,
  country,
  city,
  userPhone,
  orderCheck
) {
  // Проверка имени
  const nameRegex = /^[a-zA-Zа-яА-ЯёЁ\s]+$/;
  if (!nameRegex.test(userName) || userName.length < 2) {
    alert("Имя должно содержать только буквы и быть не менее 2 символов.");
    return false;
  }

  // Проверка email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    alert("Введите корректный email.");
    return false;
  }

  // Проверка страны
  if (country !== "russia" && country !== "belarus") {
    alert("Заказы доступны только для России и Беларуси.");
    return false;
  }

  // Проверка города
  if (!city) {
    alert("Выберите город из списка.");
    return false;
  }
  // Проверка телефона
  const phoneRegex = /^\+7\d{10}$/;
  if (!phoneRegex.test(userPhone)) {
    alert("Введите номер телефона в формате +7XXXXXXXXXX.");
    return false;
  }

  // Проверка чекбокса
  if (!orderCheck) {
    alert("Подтвердите отправку данных.");
    return false;
  }

  return true; // Все проверки пройдены
}