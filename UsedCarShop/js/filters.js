const searchInput = document.querySelector('.search input');
const objects = document.querySelectorAll('.object');

searchInput.addEventListener('input', function() {
  const searchText = this.value.toLowerCase();
  
  objects.forEach(function(object) {
    const title = object.querySelector('h2').textContent.toLowerCase();
    const description = object.querySelector('p').textContent.toLowerCase();
    
    if (title.includes(searchText) || description.includes(searchText)) {
      object.style.display = 'block';
    } else {
      object.style.display = 'none';
    }
  });
});

const durationInput = document.querySelector('.filter input[name = "duration"]');
const durationText = document.querySelector('.filter p:last-child');
durationInput.addEventListener('input', function(){
  const duration = this.value;
  //console.log(duration)
  if(duration == '24'){
    durationText.textContent = '24 месяца';
  }
  else{
    durationText.textContent = `${duration} - 24 месяца`;
  }
  objects.forEach(function(object) {
    
    
    const durationCourse = object.querySelectorAll('p')[1].textContent.toLowerCase();
    var number = durationCourse.match(/\d+/)[0]; 
    
    if (parseInt(duration) <= number) {
      object.style.display = 'block';
    } else {
      object.style.display = 'none';
    }
  });
});

const priceInput = document.querySelector('.filter select[name = "price"]');
priceInput.addEventListener('change', function(){
  const price = this.value;
  var min = 0;
  var max = 3870000;
  //console.log(duration)
  if(price == "low"){
    max = 1300000;
  }
  else if(price == "average"){
    min = 2130000;
    max = 2700000;
  }
  else if(price == "high"){
    min = 3870000;
  }
  objects.forEach(function(object) {
    
    
    const priceCourse = object.querySelectorAll('p')[2].textContent.toLowerCase();
    var number = priceCourse.match(/\d+/g).join(''); 
    console.log(number);
    
    if ((number >= min)&&(number <= max)) {
      object.style.display = 'block';
    } else {
      object.style.display = 'none';
    }
  });
});

const brandFilters = document.querySelectorAll('.filter input[name="category"]'); // Получаем все чекбоксы
const objects_ = document.querySelectorAll('.object');

function filterByBrand() {
  // Получаем все выбранные марки
  const selectedBrands = [];
  brandFilters.forEach(function(checkbox) {
    if (checkbox.checked) {
      selectedBrands.push(checkbox.id);
    }
  });

  // Проходим по всем объектам и скрываем или показываем их в зависимости от выбранных марок
  objects_.forEach(function(object) {
    const brand = object.querySelector('.brand').textContent.toLowerCase(); // Предположим, что марка машины указана в классе brand

    // Если объект соответствует хотя бы одной выбранной марке, то показываем его
    if (selectedBrands.length === 0 || selectedBrands.some(brandFilter => brand.includes(brandFilter.toLowerCase()))) {
      object.style.display = 'block';
    } else {
      object.style.display = 'none';
    }
  });
}

// Добавляем обработчик событий на каждый чекбокс
brandFilters.forEach(function(checkbox) {
  checkbox.addEventListener('change', filterByBrand);
});
