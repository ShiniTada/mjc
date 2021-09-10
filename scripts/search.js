let index;
let currentPage = 1;
const numberPerPage = 10;
let certificates = [];
let flag = false;
let buttonShow = document.getElementById('show');
let mybuttonTop = document.getElementById("myBtnTop");
let mybuttonUp = document.getElementById("myBtnUp");
const container = document.getElementById("container")

loadData();

function loadData() {
    removeOldData();
    let value = document.getElementById('searchWord').value.trim().toLowerCase();
    let param = document.getElementById('search').value;

    if (value == '') {
        certificates = data.slice();
        show(certificates, currentPage);
    }

    if (value != '') {
        switch (param) {
            case ('by name'):
                searchByName(value);
                break;
            case ('by description'):
                searchByDescription(value);
                break;
            case ('by tag'):
                searchByTag(value);
                break;
        }
    }
}

function removeOldData() {
    currentPage = 1;
    if (certificates.length != 0) {
        for (let i = (certificates.length - 1); i >= 0; i--) {
            certificates.splice(i, 1);
        }
    }
    while (container.firstChild) {
        container.firstChild.remove()
    }
}

function show(list, page) {

    const numberOfItems = list.length;
    if (numberOfItems < 11) { buttonShow.style.display = "none"; flag == false }

    const numberOfPages = Math.ceil(numberOfItems / numberPerPage);
    const trimStart = (page - 1) * numberPerPage;
    let trimEnd;

    if ((trimStart + numberPerPage) < list.length) {
        trimEnd = trimStart + numberPerPage;
    } else {
        trimEnd = list.length;
    }

    for (index = trimStart; index < trimEnd; index++) {
        let title = list[index].name;
        let price = list[index].price;
        let duration = list[index].duration;
        let image = list[index].image;
        createCertificate(title, price, duration, image);
    }

    if (numberOfPages > 1 && flag == false) {
        buttonShow.style.display = "block";
    }
    if (index > (list.length - 2)) {
        buttonShow.style.display = "none";
    }
}

function addShowButton() {
    show(certificates, (++currentPage));
}

function searchByName(value) {
    for (let i = 0; i < data.length; i++) {
        if (data[i].name.toLowerCase().includes(value, 0)) {
            certificates.push(data[i]);
        }
    }

    if (certificates.length != 0) {
        show(certificates, currentPage);
    } else {
        showMessageNotFoundCertificates();
    }
}

function searchByDescription(value) {

    for (let i = 0; i < data.length; i++) {
        if (data[i].description.toLowerCase().includes(value, 0)) {
            certificates.push(data[i]);
        }
    }

    if (certificates.length != 0) {
        show(certificates, currentPage);
    } else {
        showMessageNotFoundCertificates();
    }
}

function searchByTag(value) {
    for (let i = 0; i < data.length; i++) {
        for (let j = 0; j < data[i].tags.length; j++) {
            if (data[i].tags[j].name.toLowerCase().includes(value, 0)) {
                certificates.push(data[i]);
            }
        }
    }
    if (certificates.length != 0) {
        show(certificates, currentPage);
    } else {
        showMessageNotFoundCertificates();
    }
}

function showMessageNotFoundCertificates() {
    buttonShow.style.display = "none";
    const message = document.createElement('div');
    message.className = 'message';
    message.innerHTML = '<p> Not found certificates with this param.</p>';
    const container = document.querySelector('.container');
    container.appendChild(message);
}

window.onscroll = function () {
    scrollFunction();
};

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
    window.scrollBy(0, -440);
}

//const body = document.querySelector('.container');
