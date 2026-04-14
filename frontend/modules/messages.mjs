export function statusMessage(statusMessage) {
  let statusMessageArea = document.getElementById('statusMessageArea');
  statusMessageArea.innerHTML = `<p class="pico-color-cyan-100">${statusMessage}</p>`;
}

export function responseOutput(responseMessage) {
  let responseOutputArea = document.getElementById('responseOutputArea');
  responseOutputArea.innerHTML = `<p class="pico-color-cyan-100">${responseMessage}</p>`;
}

export function message(message, area) {
  let messageArea = document.getElementById(`messageArea${area}`);
  messageArea.innerHTML = `<h3 class="pico-color-cyan-100">${message}</h3>`;
}
