import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  // http.get('http://localhost:8080/users');
  const url = `http://38.242.250.192:8080`;
  // const url = `http://localhost:8080`;

  const payload = JSON.stringify(
    {
      login: 'test:' + Math.random() * 1000000 ,
      email: 'test' + Math.random() * 1000000 + '@gmail.com',
      password: 'test + ' + Math.random() * 1000000,
    });

  const params ={
    headers: {
      'Content-Type': 'application/json',
    }
  }

  http.post(`${url}/user`, payload, params);

  // http.get(url);

  sleep(0.2);
}
