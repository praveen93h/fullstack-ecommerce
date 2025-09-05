import React, {useState, useEffect} from 'react';
import api from '../api';
export default function ProductList({cart, setCart}) {
  const [products, setProducts] = useState([]);
  useEffect(()=>{ fetchProducts(); }, []);
  const fetchProducts = async () => {
    const res = await api.get('/products');
    setProducts(res.data.content || res.data);
  };
  const add = (p) => {
    const existing = cart.find(c=>c.id===p.id);
    if (existing) {
      setCart(cart.map(c=>c.id===p.id?{...c, qty:c.qty+1}:c));
    } else {
      setCart([...cart, {id:p.id, name:p.name, price:p.price, qty:1}]);
    }
  };
  return (
    <div>
      <h3>Products</h3>
      <div style={{display:'grid', gridTemplateColumns:'repeat(2,1fr)', gap:10}}>
        {products.map(p=>(
          <div key={p.id} style={{border:'1px solid #ddd', padding:10}}>
            <h4>{p.name}</h4>
            <p>{p.description}</p>
            <div>₹{p.price}</div>
            <button onClick={()=>add(p)}>Add to cart</button>
          </div>
        ))}
      </div>
    </div>
  );
}
