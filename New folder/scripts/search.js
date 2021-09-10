
document.addEventListener('DOMContentLoaded', function () {
    search();
}, false);
function search() {
    let value = document.getElementById('searchWord').value.trim().toLowerCase();
    let param = document.getElementById('search').value;
    if (value == '') {
        for (let i = 0; i < 10; i++) {
            let name = data[i].name;
            let price = data[i].price;
            let duration = data[i].duration;
            let image = data[i].image;
            createCertificate(name, price, duration, image);
        }
    }
    if (value.length != '' && value.length >= 3) {
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
    } else {
        //alert('Enter data to search for more than 3 characters!')
    }
}

let listWithSearch = [];

function searchByName(value) {
    for (let i = 0; i < data.length; i++) {
        if (data[i].name.toLowerCase().includes(value, 0)) {
            listWithSearch.push(data[i]);
            //console.log(value);
        }
    }

    if (listWithSearch.length != 0) {
        for (let i = 0; i < listWithSearch.length; i++) {
            let name = listWithSearch[i].name;
            let price = listWithSearch[i].price;
            let duration = listWithSearch[i].duration;
            let image = listWithSearch[i].image;
            createCertificate(name, price, duration, image);

        }

    } else {
        const message = document.createElement('div');
        message.className = 'message';
        message.innerHTML = '<p> Not found certificates with this param. Go back and change them.</p><a href="main.html">Back</a>';
        const container = document.querySelector('.container');
        container.appendChild(message);
    }
}


function searchByDescription(value) {
    console.log(value + ' descr');
    for (let i = 0; i < data.length; i++) {
        if (data[i].description.includes(value, 0)) {
            console.log(data[i]);
        } else {

        }
    }
}

function searchByTag(value) {
    console.log(value + ' tag');
    for (let i = 0; i < data.length; i++) {
        if (data[i].tags.includes(value, 0)) {
            console.log(data[i]);
        } else {

        }
    }
}