const apiUrl = 'http://localhost:7001';

function loadTime() {
  let time = document.getElementById('time');
  let csrfToken = getCookie('csrfToken');

  fetch(apiUrl + '/auth/session-cookie/info', {
      method: 'GET',
      credentials: 'include',
      headers: {
        'X-CSRF': csrfToken
      }
    })
    .then(res => {
      res.text().then(text => {
        time.innerHTML = text;
      });
    })
    .catch(error => console.error('Error getting time : ', error));
}

function getCookie(cookieName) {
  var cookieValue = document.cookie.split(';')
    .map(item => item.split('=')
      .map(x => decodeURIComponent(x.trim())))
    .filter(item => item[0] === cookieName)[0]

  if (cookieValue) {
    return cookieValue[1];
  }
}