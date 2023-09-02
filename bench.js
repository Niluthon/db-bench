import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  // http.get('http://localhost:8080/users');
  const url = `http://38.242.250.192:8080/user/${Math.round(Math.random() * 95000)}`;

  // const payload = JSON.stringify(
  //   {
  //     login: 'test:' + Math.random() * 1000000 ,
  //     email: 'test' + Math.random() * 1000000 + '@gmail.com'
  //   });
  //
  // const params ={
  //   headers: {
  //     'Content-Type': 'application/json',
  //   }
  // }
  //
  // http.post('http://38.242.250.192:8080/user', payload, params);

  http.get(url);

  sleep(0.2);
}
