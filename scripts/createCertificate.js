function createCertificate(name, price, duration, image) {
  const container = document.querySelector('.container');
  const div = document.createElement('div');
  div.className = 'certificate_card';
  div.innerHTML = `<img src="../assets/images/${image}" alt="" />
      <div class="name" id="name"> ${name}</div>
      <div class="text" id="price"> ${price}$</div>
      <div class="text" id="duration"> ${duration} days</div>`;
  container.appendChild(div);
}