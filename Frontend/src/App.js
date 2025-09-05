import React, { useState } from 'react';
import ProductList from './pages/ProductList';
import Login from './pages/Login';
import Register from './pages/Register';
import Cart from './pages/Cart';

export default function App(){
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [cart, setCart] = useState([]);
  return (
    <div style={{padding:20}}>
      <h1>Fullstack E-commerce (Starter)</h1>
      <div style={{display:'flex', gap:20}}>
        <div style={{flex:1}}>
          <ProductList cart={cart} setCart={setCart}/>
        </div>
        <div style={{width:320}}>
          {!token ? (
            <>
              <Login onLogin={(t)=>{localStorage.setItem('token', t); setToken(t);}}/>
              <Register/>
            </>
          ) : (
            <div>
              <button onClick={()=>{localStorage.removeItem('token'); setToken(null);}}>Logout</button>
            </div>
          )}
          <Cart cart={cart} setCart={setCart} />
        </div>
      </div>
    </div>
  );
}
