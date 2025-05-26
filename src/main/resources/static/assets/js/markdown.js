document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll(".markdown-content").forEach(el => {
    const raw = el.textContent;
    const dirtyHtml = marked.parse(raw);
    const cleanHtml = DOMPurify.sanitize(dirtyHtml);
    el.innerHTML = cleanHtml;
  });
});
