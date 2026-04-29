const hamburger = document.querySelector('.hamburger');
const sidePanel = document.getElementById('sidePanel');
const overlay   = document.getElementById('overlay');

function openPanel() {
  sidePanel.classList.add('open');
  overlay.classList.add('open');
  hamburger.classList.add('active');
  document.body.style.overflow = 'hidden';
}

function closePanel() {
  sidePanel.classList.remove('open');
  overlay.classList.remove('open');
  hamburger.classList.remove('active');
  document.body.style.overflow = '';
}

hamburger.addEventListener('click', () => {
  sidePanel.classList.contains('open') ? closePanel() : openPanel();
});

overlay.addEventListener('click', closePanel);

document.addEventListener('keydown', e => {
  if (e.key === 'Escape') closePanel();
});
