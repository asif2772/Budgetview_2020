function autoresize(textarea) {
    textarea.style.height = '24px';
    textarea.style.maxHeight = '50px';
    textarea.style.height = textarea.scrollHeight + 12 + 'px';
}