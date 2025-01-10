document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("modal");
    const modalImg = document.getElementById("modal-img");
    const closeBtn = document.querySelector(".close");
  
    // Обработчик клика на все изображения в секции product-images
    document.querySelectorAll(".image-sec img").forEach((img) => {
      img.addEventListener("click", () => {
        modal.style.display = "flex"; // Показать модальное окно
        modalImg.src = img.src; // Подставить изображение в модальное окно
      });
    });
  
    // Закрытие модального окна при клике на кнопку "X"
    closeBtn.addEventListener("click", () => {
      modal.style.display = "none";
    });
  
    // Закрытие модального окна при клике вне изображения
    modal.addEventListener("click", (event) => {
      if (event.target === modal) {
        modal.style.display = "none";
      }
    });
  });