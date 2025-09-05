import React from 'react';
import api from '../api';
export default function Cart({cart, setCart}) {
  const remove = (id) => setCart(cart.filter(c=>c.id!==id));
  const checkout = async () => {
    const items = cart.map(c=>({productId:c.id, quantity:c.qty}));
    try {
      const res = await api.post('/orders/checkout', {items});
      alert('Payment success. Tx: ' + res.data.transactionId);
      setCart([]);
    } catch (e) {
      alert('Checkout failed: ' + (e.response?.data?.error || e.message));
    }
  };
  return (
    <div style={{marginTop:20}}>
      <h3>Cart</h3>
      {cart.length===0 ? <div>No items</div> : (
        <>
          {cart.map(c=>(
            <div key={c.id} style={{borderBottom:'1px solid #eee', padding:8}}>
              <div>{c.name} x {c.qty}</div>
              <div>₹{c.price}</div>
              <button onClick={()=>remove(c.id)}>Remove</button>
            </div>
          ))}
          <button onClick={checkout} style={{marginTop:10}}>Checkout (dummy)</button>
        </>
      )}
    </div>
  );
}
