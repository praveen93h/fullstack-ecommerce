import React, { useEffect, useState } from "react";
import api from "../api";

export default function ProductList() {
    const [products, setProducts] = useState([]);
    const [q, setQ] = useState("");
    const [page, setPage] = useState(0);

    useEffect(() => {
        fetch();
    }, [q, page]);

    const fetch = async () => {
        const res = await api.get("/products", { params: { q, page, size: 12 }});
        setProducts(res.data.content || res.data);
    };

    return (
        <div>
            <div>
                <input placeholder="Search products" value={q} onChange={e=>setQ(e.target.value)} />
            </div>
            <div className="grid">
                {products.map(p => (
                    <div key={p.id} className="card">
                        <img src={p.imageUrl} alt={p.name} style={{width: '100%'}} />
                        <h3>{p.name}</h3>
                        <p>{p.description?.substring(0,100)}</p>
                        <div>₹{p.price}</div>
                        <button onClick={()=>{/* add to cart */}}>Add to cart</button>
                    </div>
                ))}
            </div>
            <div>
                <button onClick={()=>setPage(p=>Math.max(0, p-1))}>Prev</button>
                <span> Page {page+1} </span>
                <button onClick={()=>setPage(p=>p+1)}>Next</button>
            </div>
        </div>
    )
}
