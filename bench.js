import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  // http.get('http://localhost:8080/users');

  const payload = JSON.stringify(
    {
      name: 'test:' + Math.random() * 1000000 ,
      email: 'test' + Math.random() * 1000000 + '@gmail.com'
    });

  const params ={
    headers: {
      'Content-Type': 'application/json',
    }
  }

  http.post('http://localhost:8080/user', payload, params);

  sleep(0.5);
}
