let currentSlide = 0;

function moveSlide(step) {
  const slides = document.querySelectorAll(".banner-slide");
  const totalSlides = slides.length;

  slides[currentSlide].style.display = "none";

  currentSlide = (currentSlide + step + totalSlides) % totalSlides;

  slides[currentSlide].style.display = "block";
}

document.addEventListener("DOMContentLoaded", () => {
  const slides = document.querySelectorAll(".banner-slide");
  slides.forEach((slide) => (slide.style.display = "none"));
  slides[currentSlide].style.display = "block";
});
