document.addEventListener("DOMContentLoaded", () => {
  const captcha = document.getElementById('captcha');
  captcha.addEventListener('click', () => {
    captcha.src = '/captcha.jpg?rand' + Date.now();
  });
});
