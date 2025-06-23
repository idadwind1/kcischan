document.querySelectorAll('.post-form').forEach(el => el.addEventListener('submit', async function (e) {
  e.preventDefault();

  const captcha = document.getElementById('captcha');
  const status = document.getElementById('post-status');
  const form = e.target;
  const file = form.file.files[0];

  if (file && file.size > 200 * 1024 * 1024) { // 200MB in bytes
    e.preventDefault();
    status.style.display = 'block';
    status.textContent = 'Failed to post: File too big (> 200mb)';
    status.style.color = 'red';
  }

  const res = await fetch('/api/post', {
    method: 'POST',
    body: new FormData(form)
  });

  const json = await res.json();
  if (res.ok) {
    status.style.display = 'block';
    status.textContent = 'Posted! Post ID: ' + json.postId;
    status.style.color = 'green';
    setTimeout(() => location.reload(), 500);
    form.reset();
  } else {
    status.style.display = 'block';
    status.textContent = 'Failed to post: ' + json.message;
    status.style.color = 'red';
    captcha.src = '/captcha.jpg?rand' + Date.now();
  }
}));

const logout = document.getElementById('logout-admin')
if (logout)
  logout.addEventListener('click', async function (e) {
    e.preventDefault();
    await fetch('/api/admin/logout')
    window.location.reload();
  });
