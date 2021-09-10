showMain();
function showMain() {
  for (let i = 0; i < 10; i++) {
    let name = data[i].name;
    let price = data[i].price;
    let duration = data[i].duration;
    let image = data[i].image;
    createCertificate(name, price, duration, image);
  }
}






