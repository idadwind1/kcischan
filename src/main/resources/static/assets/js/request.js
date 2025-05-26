async function postApi(url, formData, status, successMessage) {
  const res = await fetch(url, {
    method: 'POST',
    body: formData,
  });
  const json = await res.json();
  if (res.ok) {
    status.textContent = successMessage;
    status.style.color = "green";
    status.style.display = "block";
  } else {
    status.textContent = json.message;
    status.style.color = "red";
    status.style.display = "block";
  }
}
