import React, { useState, useEffect } from 'react';

const PREDEFINED_FOODS = [
  { name: 'Gourmet Pizza', price: 15.99, emoji: '🍕' },
  { name: 'Smash Burger', price: 9.99, emoji: '🍔' },
  { name: 'Dragon Sushi Roll', price: 22.50, emoji: '🍣' },
  { name: 'Spicy Shoyu Ramen', price: 13.25, emoji: '🍜' },
];

function App() {
  const [customerName, setCustomerName] = useState('');
  const [selectedFood, setSelectedFood] = useState(PREDEFINED_FOODS[0]);
  const [customAmount, setCustomAmount] = useState('');
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Poll orders
  useEffect(() => {
    fetchOrders();
    const interval = setInterval(fetchOrders, 2000);
    return () => clearInterval(interval);
  }, []);

  const fetchOrders = async () => {
    try {
      const response = await fetch('http://localhost:8081/api/orders');
      if (response.ok) {
        const data = await response.json();
        // Sort by ID descending so newest are on top
        const sorted = data.sort((a, b) => b.id - a.id);
        setOrders(sorted);
        setError('');
      } else {
        setError('Failed to connect to Order Service. Make sure backend is running.');
      }
    } catch (err) {
      setError('Connection error. Is the backend running on port 8081?');
    }
  };

  const handlePlaceOrder = async (e) => {
    e.preventDefault();
    if (!customerName.trim()) return;

    setIsSubmitting(true);
    const amount = customAmount ? parseFloat(customAmount) : selectedFood.price;
    const item = selectedFood ? selectedFood.name : 'Custom Meal';

    const payload = {
      customerName: customerName.trim(),
      item: item,
      amount: amount
    };

    try {
      const response = await fetch('http://localhost:8081/api/orders', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        setCustomerName('');
        setCustomAmount('');
        fetchOrders();
      } else {
        alert('Failed to place order.');
      }
    } catch (err) {
      alert('Error placing order: ' + err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const getStatusClass = (status) => {
    if (!status) return '';
    return status.toLowerCase();
  };

  const formatStatus = (status) => {
    if (!status) return '';
    return status.replace(/_/g, ' ');
  };

  return (
    <div>
      <header className="app-header">
        <div className="brand-section">
          <span className="brand-logo">🍕</span>
          <div>
            <h1 className="brand-title">FoodFlow Orchestrator</h1>
            <p className="brand-subtitle">Real-time Order Processing Dashboard</p>
          </div>
        </div>
        <div className="pulse-badge">
          <span className="pulse-dot"></span>
          Live Sync Active
        </div>
      </header>

      {error && (
        <div className="glass-card" style={{ borderColor: 'var(--danger)', marginBottom: '1.5rem', background: 'rgba(231, 76, 60, 0.1)' }}>
          <span style={{ color: 'var(--danger)', fontWeight: 'bold' }}>⚠️ Service Warning: </span>
          <span style={{ color: 'var(--text-secondary)' }}>{error}</span>
        </div>
      )}

      <div className="app-container">
        {/* Order Form */}
        <div className="glass-card">
          <h2 className="card-title">Place New Order</h2>
          <form onSubmit={handlePlaceOrder}>
            <div className="form-group">
              <label>Customer Name</label>
              <input
                type="text"
                placeholder="Enter customer name..."
                value={customerName}
                onChange={(e) => setCustomerName(e.target.value)}
                required
              />
              <span style={{ fontSize: '0.75rem', color: 'var(--text-secondary)' }}>
                Tip: Include the word <strong>"fail"</strong> or <strong>"cancel"</strong> in the name to simulate payment failure.
              </span>
            </div>

            <div className="form-group">
              <label>Select Menu Item</label>
              <div className="food-grid">
                {PREDEFINED_FOODS.map((food) => (
                  <div
                    key={food.name}
                    className={`food-option ${selectedFood && selectedFood.name === food.name ? 'selected' : ''}`}
                    onClick={() => {
                      setSelectedFood(food);
                      setCustomAmount('');
                    }}
                  >
                    <span className="food-emoji">{food.emoji}</span>
                    <span className="food-name">{food.name}</span>
                    <span className="food-price">${food.price.toFixed(2)}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="form-group">
              <label>Or Enter Custom Amount ($)</label>
              <input
                type="number"
                step="0.01"
                placeholder={`Use item price ($${selectedFood.price.toFixed(2)})`}
                value={customAmount}
                onChange={(e) => {
                  setCustomAmount(e.target.value);
                }}
              />
            </div>

            <button type="submit" className="btn-submit" disabled={isSubmitting || !customerName.trim()}>
              {isSubmitting ? 'Placing Order...' : '🚀 Submit Order'}
            </button>
          </form>
        </div>

        {/* Order Dashboard */}
        <div className="glass-card">
          <div className="order-board-header">
            <h2 className="card-title" style={{ margin: 0 }}>Active Orders Pipeline</h2>
            <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
              Showing {orders.length} orders
            </span>
          </div>

          {orders.length === 0 ? (
            <div className="no-orders">
              <span className="no-orders-icon">🛒</span>
              <p>No orders placed yet. Submit the form to start the Camunda flow!</p>
            </div>
          ) : (
            <div className="orders-table-wrapper">
              <table className="orders-table">
                <thead>
                  <tr>
                    <th>Order ID</th>
                    <th>Customer</th>
                    <th>Item</th>
                    <th>Amount</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {orders.map((order) => (
                    <tr key={order.id}>
                      <td style={{ fontWeight: 'bold', color: 'var(--primary)' }}>#{order.id}</td>
                      <td>{order.customerName}</td>
                      <td>{order.item}</td>
                      <td style={{ fontFamily: 'monospace' }}>${order.amount != null ? order.amount.toFixed(2) : '0.00'}</td>
                      <td>
                        <span className={`status-badge ${getStatusClass(order.status)}`}>
                          <span className="badge-dot"></span>
                          {formatStatus(order.status)}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default App;
