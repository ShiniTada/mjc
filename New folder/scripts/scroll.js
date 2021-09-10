/*const container = document.querySelector('.container');
let i = 10;

window.addEventListener('scroll', () => {
    const { scrollHeight, scrollTop, clientHeight } = document.documentElement;
    if (scrollTop + clientHeight > scrollHeight - 5) {
        setTimeout(createPost, 1000)
    }
})

function createPost() {
    let count = i + 5;

    for (i; i < count; i++) {
        const post = document.createElement('div');
        post.className = 'certificate_card';
        let name = data[i].name;
        let price = data[i].price;
        let duration = data[i].duration;
        let image = data[i].image;
        post.innerHTML = `<img src="../assets/images/${image}" alt="" />
      <div class="text" id="name"> ${name}</div>
      <div class="text" id="price"> ${price}$</div>
      <div class="text" id="duration"> ${duration} days</div>`;

        container.appendChild(post);
    }
}*/
