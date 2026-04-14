import { responseOutput, statusMessage } from './messages.mjs';
export function getEngineers() {
  fetch('http://localhost:8080/api/v1/software-engineers')
    .then((res) => res.json())
    .then((data) => {
      console.log('data from json', data);
      let output = '<h2 class="pico-color-cyan-300">List of Engineers</h2>';
      data.forEach((user) => {
        output += `
                <ul>
                  <li class="pico-color-cyan-100">Id: ${user.id}</li>
                  <li class="pico-color-cyan-100">Name: ${user.name}</li>
                  <li class="pico-color-cyan-100">Email: ${user.techStack}</li>
                </ul>
              `;
      });
      // document.getElementById('responseOutputArea').innerHTML =
      // `${output} <hr>`;
      statusMessage('Data successfully retrieved');
      responseOutput(output);
    })
    .catch((error) => console.error({ error }));
}

export function getEngineersById(url, id) {
  url = 'http://localhost:8080/api/v1/software-engineers';
  fetch(`${url}/${id}`)
    .then((res) => res.json())
    .then((user) => {
      console.log('data from json', user);
      let output = `<h2 class="pico-color-cyan-300">Engineer with id: ${id}</h2>`;
      output += `
                <ul>
                  <li class="pico-color-cyan-100">Id: ${user.id}</li>
                  <li class="pico-color-cyan-100">Name: ${user.name}</li>
                  <li class="pico-color-cyan-100">Email: ${user.techStack}</li>
                </ul>
              `;

      // document.getElementById('responseOutputArea').innerHTML =
      // `${output} <hr>`;
      statusMessage('Data successfully retrieved');
      responseOutput(output);
    })
    .catch((error) => console.error({ error }));
}

export function requestAllEngineers(url) {
  const myHeaders = new Headers();
  myHeaders.append('Content-Type', 'application/json');

  const requestOptions = {
    method: 'GET',
    headers: myHeaders,
    redirect: 'follow'
  };

  fetch(url, requestOptions)
    .then((response) => response.text())
    .then(
      (text) => (document.getElementById('responseOutputArea').innerHTML = text)
    )
    // .then((text) => console.log({ text }))
    // .then((response) => response.json())
    // .then(
    //   (json) =>
    //     (document.getElementById('messageArea3').innerHTML =
    //       JSON.stringify(json))
    // )
    // .then((json) => console.log(JSON.stringify(json)))
    .catch((error) => console.error({ error }));
}
