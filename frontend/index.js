import { statusMessage, responseOutput } from './modules/messages.mjs';
import {
  getEngineers,
  getEngineersById,
  requestAllEngineers
} from './modules/fetch.mjs';

let getEngineersButton = document.getElementById('getEngineers');
getEngineersButton.addEventListener('click', getEngineers);

let getEngineerByIdButton = document.getElementById('getEngineerById');
getEngineerByIdButton.addEventListener('click', getEngineerId);

let resetButton = document.getElementById('reset');
resetButton.addEventListener('click', reset);

submitId.addEventListener('click', onSubmit);

const url = 'http://localhost:8080/api/v1/software-engineers';

statusMessage('Click a button to fetch data');
// window.onload = initPage;

function initPage() {
  statusMessage('Click a button to fetch data');
  //  requestAllEngineers(url);
  // getEngineers();
}

function reset() {
  statusMessage('Click a button to fetch data');
  document.getElementById('responseOutputArea').innerHTML = '';
  document.getElementById('getId').style.display = 'none';
  // responseOutput('');
}

function getEngineerId() {
  statusMessage('Enter an Id:');
  document.getElementById('getId').style.display = '';
  document.getElementById('inputId').focus();
}

function onSubmit(e) {
  e.preventDefault();
  // console.log(inputId.value);
  let id = inputId.value;
  // console.log({ id });
  getEngineersById(url, id);
  inputId.value = '';
}

function createForm() {
  const form = document.createElement('form');
  form.setAttribute('id', 'enterId');
  const field = document.createElement('fieldset');
  field.setAttribute('class', 'input');
  field.setAttribute('role', 'group');
  const inputId = document.createElement('input');
  inputId.setAttribute('type', 'number');
  inputId.setAttribute('id', 'inputId');
  inputId.setAttribute('name', 'inputId');
  const submitId = document.createElement('input');
  submitId.setAttribute('type', 'submit');
  submitId.setAttribute('id', 'submitId');
  submitId.setAttribute('value', 'Enter Id');
  field.appendChild(inputId);
  field.appendChild(submitId);
  form.appendChild(field);
  getId.appendChild(form);
}
