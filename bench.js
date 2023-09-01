import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  http.get('http://localhost:8080/users');

  // const payload = JSON.stringify(
  //   {
  //     ame: 'test:' + Math.random() * 10000 ,
  //     email: 'test' + Math.random() * 10000 + '@gmail.com'
  //   });
  //
  // const params ={
  //   headers: {
  //     'Content-Type': 'application/json',
  //   }
  // }
  //
  // http.post('http://localhost:8080/user', payload, params);

}
