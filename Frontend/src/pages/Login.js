import React, {useState} from 'react';
import api from '../api';
export default function Login({onLogin}) {
  const [username,setUsername]=useState(''); const [password,setPassword]=useState('');
  const submit = async ()=> {
    try {
      const res = await api.post('/auth/login', {username, password});
      onLogin(res.data.token);
    } catch (e) {
      alert('Login failed');
    }
  };
  return (
    <div>
      <h4>Login</h4>
      <input placeholder="username" value={username} onChange={e=>setUsername(e.target.value)}/><br/>
      <input placeholder="password" type="password" value={password} onChange={e=>setPassword(e.target.value)}/><br/>
      <button onClick={submit}>Login</button>
    </div>
  );
}
