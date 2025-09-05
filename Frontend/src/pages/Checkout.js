import React from "react";
import api from "../api";

export default function Checkout({ cart }) {
    const handleCheckout = async () => {
        const items = cart.map(c=>({productId: c.id, quantity: c.qty}));
        const res = await api.post("/orders/checkout", { items });
        alert("Payment " + res.data.status + ", tx: " + res.data.transactionId);
        // clear cart
    };

    return <button onClick={handleCheckout}>Pay (dummy)</button>;
}
