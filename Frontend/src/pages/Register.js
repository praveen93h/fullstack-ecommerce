import React, {useState} from 'react';
import api from '../api';
export default function Register() {
  const [username,setUsername]=useState(''); const [email,setEmail]=useState(''); const [password,setPassword]=useState('');
  const submit = async ()=> {
    try {
      const res = await api.post('/auth/register', {username, email, password});
      alert('Registered. You can login now.');
    } catch (e) {
      alert('Register failed: ' + (e.response?.data?.error || e.message));
    }
  };
  return (
    <div style={{marginTop:20}}>
      <h4>Register</h4>
      <input placeholder="username" value={username} onChange={e=>setUsername(e.target.value)}/><br/>
      <input placeholder="email" value={email} onChange={e=>setEmail(e.target.value)}/><br/>
      <input placeholder="password" type="password" value={password} onChange={e=>setPassword(e.target.value)}/><br/>
      <button onClick={submit}>Register</button>
    </div>
  );
}
