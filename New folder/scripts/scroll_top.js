mybuttonTop = document.getElementById("myBtn");
mybuttonUp = document.getElementById("myBtnUp");
buttonShow = document.getElementById("show");

let i = 10;
let flag = false;
const body = document.querySelector('.container');

window.addEventListener('scroll', () => {
  const { scrollHeight, scrollTop, clientHeight } = document.documentElement;
  if (scrollTop + clientHeight > scrollHeight - 5) {
    if (flag == false) {
      setTimeout(createShow, 500);
    }
  }
})

window.onscroll = function () {
  scrollFunction();
};

function createShow() {
  buttonShow.style.display = "block";
  flag = true;
}

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybuttonTop.style.display = "block";
    mybuttonUp.style.display = "block";
  } else {
    mybuttonTop.style.display = "none";
    mybuttonUp.style.display = "none";
  }
}

function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}

function upFunction() {
  if (i === 20) {
    document.documentElement.scrollTop = 0;
    document.body.scrollTop = 0;
  }
  if (i > 28) {
    document.documentElement.scrollTop = 550;
    document.body.scrollTop = 550;
  }
}

function show() {
  let count = i + 10;
  for (i; i < count; i++) {
    const certificate = document.createElement('div');
    certificate.className = 'certificate_card';
    let name = data[i].name;
    let price = data[i].price;
    let duration = data[i].duration;
    let image = data[i].image;
    certificate.innerHTML = `<img src="../assets/images/${image}" alt="" />
      <div class="text" id="name"> ${name}</div>
      <div class="text" id="price"> ${price}$</div>
      <div class="text" id="duration"> ${duration} days</div>`;

    container.appendChild(certificate);

    if (i === (data.length - 1)) {
      buttonShow.style.display = "none";
    }
  }
}
