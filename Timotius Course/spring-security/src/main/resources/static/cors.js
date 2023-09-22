const apiUrl = 'http://localhost:7001';

function fetchPing() {
  fetch(apiUrl + '/cors/ping', {
      method: 'GET'
    })
    .then(res => {
      res.text().then(text => {
        display.innerHTML = text;
      });
    })
    .catch(error => console.error('Error : ', error));
}

function fetchPong() {
  fetch(apiUrl + '/cors/pong', {
      method: 'POST'
    })
    .then(res => {
      res.text().then(text => {
        display.innerHTML = text;
      });
    })
    .catch(error => console.error('Error : ', error));
}

function fetchMad() {
  fetch(apiUrl + '/cors/head', {
      method: 'GET'
    })
    .then(res => {
      res.text().then(text => {
        display.innerHTML = text;
      });
    })
    .catch(error => console.error('Error : ', error));
}

function fetchMax() {
  fetch(apiUrl + '/cors/tail', {
      method: 'POST'
    })
    .then(res => {
      res.text().then(text => {
        display.innerHTML = text;
      });
    })
    .catch(error => console.error('Error : ', error));
}