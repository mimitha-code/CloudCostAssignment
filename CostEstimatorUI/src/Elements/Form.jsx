import React, { useState, useEffect } from "react";
import backgroundImage from "../assets/bgimage.svg";

function FormElements() {
  const [region, setRegion] = useState("");
  const [type, setType] = useState("");
  const [count, setCount] = useState(1);
  const [totalCost, setTotalCost] = useState(null);
  const [showBill, setShowBill] = useState(false);
  const [items, setItems] = useState([]);
  const [regionList, setRegionList] = useState([]);
  const [resourcetypeList, setResourcetypeList] = useState([]);
  const [breakdownList, setBreakdownList] = useState([]);
  const [editIndex, setEditIndex] = useState(null);

  const [previousForm, setPreviousForm] = useState(null);

  // Checkout Prices modal state
  const [showCheckout, setShowCheckout] = useState(false);
  const [checkoutResource, setCheckoutResource] = useState("");
  const [checkoutData, setCheckoutData] = useState([]);

  // Add resource
  const handleAdd = () => {
    if (!region || !type || !count) return;

    setItems(prev => [...prev, { region, type, count }]);
    setPreviousForm({ region, type, count });

    setRegion("");
    setType("");
    setCount(1);
    setEditIndex(null);
  };

  // Cancel last added form
  const handleCancel = () => {
    if (previousForm) {
      setRegion(previousForm.region);
      setType(previousForm.type);
      setCount(previousForm.count);
    }
    setItems(prev => prev.slice(0, -1)); // Remove last item
  };

  const handleEdit = (index) => {
    const itemToEdit = items[index];
    setRegion(itemToEdit.region);
    setType(itemToEdit.type);
    setCount(itemToEdit.count);
    setEditIndex(index);
  };

  const handleDelete = (index) => {
    setItems(items.filter((_, i) => i !== index));
  };

  useEffect(() => {
    const fetchRegions = async () => {
      try {
        const url = !type
          ? `http://localhost:8080/cost/searchregion`
          : `http://localhost:8080/cost/type/${type}`;
        const response = await fetch(url);
        if (!response.ok) throw new Error("Failed to fetch regions");
        const regions = await response.json();
        setRegionList(regions);
      } catch (error) {
        console.error("Error fetching regions:", error);
      }
    };
    fetchRegions();
  }, [type]);

  useEffect(() => {
    const fetchResourceTypes = async () => {
      try {
        const url = !region
          ? `http://localhost:8080/cost/searchAllTypes`
          : `http://localhost:8080/cost/region/${region}`;
        const response = await fetch(url);
        if (!response.ok) throw new Error("Failed to fetch resource types");
        const resourceTypes = await response.json();
        setResourcetypeList(resourceTypes);
      } catch (error) {
        console.error("Error fetching resource types:", error);
      }
    };
    fetchResourceTypes();
  }, [region]);

  const handleSelectRegion = (event) => setRegion(event.target.value);

  // Submit resources to backend
  const HandleClick = async (event) => {
    event.preventDefault();

    let finalItems = [...items];
    if (region && type && count && editIndex === null) {
      finalItems = [...items, { region, type, count }];
      setItems(finalItems);
    }

    try {
      const response = await fetch("http://localhost:8080/cost", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(finalItems)
      });

      if (!response.ok) throw new Error("Failed to fetch cost");

      const data = await response.json();
      setBreakdownList(data.breakDownList);
      setTotalCost(data.totalCost);

      setRegion("");
      setType("");
      setCount(1);
      setEditIndex(null);
      setPreviousForm(null);
    } catch (error) {
      console.error("Error fetching cost:", error);
    }
  };

  // Open checkout modal
  const handleCheckout = async () => {
    if (!checkoutResource) return;
    try {
      const response = await fetch(`http://localhost:8080/cost/checkoutprices/${checkoutResource}`);
      if (!response.ok) throw new Error("Failed to fetch checkout prices");
      const data = await response.json();
      setCheckoutData(data); // [{region, price}, ...]
      setShowCheckout(true);
    } catch (error) {
      console.error("Error fetching checkout prices:", error);
    }
  };

  return (
    <div className="background-container" style={{ backgroundImage: `url(${backgroundImage})` }}>
      <div className="form-container large-form">
        <h2>Cloud Cost Estimator</h2>
        <form onSubmit={HandleClick}>
          <label>Region</label>
          <select value={region} onChange={handleSelectRegion} required>
            <option value="">--Select--</option>
            {regionList.map((region, index) => (
              <option key={index} value={region}>{region}</option>
            ))}
          </select>

          <label>Resource Type</label>
          <select value={type} onChange={(e) => setType(e.target.value)} required>
            <option value="">--Select--</option>
            {resourcetypeList.map((type, index) => (
              <option key={index} value={type}>{type}</option>
            ))}
          </select>

          <label>Count</label>
          <input type="number" value={count} onChange={(e) => setCount(Number(e.target.value))} required />

          <div className="button-row">
            <button type="submit">Submit</button>
            <button type="button" onClick={handleAdd}>Add Resource</button>
            <button type="button" onClick={handleCancel}>Cancel</button>
          </div>
        </form>

        {items.length > 0 && (
          <div className="items-list">
            <h4>Added Resources:</h4>
            <ul>
              {items.map((item, idx) => (
                <li key={idx} className="item-box">
                  <span className="item-text">
                    {item.region} - {item.type} x {item.count}
                  </span>
                  <div className="action-buttons">
                    <button type="button" onClick={() => handleEdit(idx)} className="edit-btn">EDIT</button>
                    <button type="button" onClick={() => handleDelete(idx)} className="delete-btn">DELETE</button>
                  </div>
                </li>
              ))}
            </ul>
          </div>
        )}

        <div className="checkout-section">
          <h4>Checkout Prices</h4>
          <select value={checkoutResource} onChange={(e) => setCheckoutResource(e.target.value)}>
            <option value="">--Select Resource--</option>
            {resourcetypeList.map((res, idx) => (
              <option key={idx} value={res}>{res}</option>
            ))}
          </select>
          <button type="button" onClick={handleCheckout}>View Prices</button>
        </div>

        {totalCost !== null && (
          <div className="cost-display">
            Total Cost: <span>{totalCost}</span>
            <button className="show-bill-btn" onClick={() => setShowBill(true)}>Show Bill</button>
          </div>
        )}
      </div>

      {/* Bill Modal */}
      {showBill && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Detailed Bill</h3>
            <ul>
              {items.map((item, idx) => (
                <li key={idx}>
                  {item.region} - {item.type} x {item.count} =
                  {breakdownList[idx]
                    ? ` ${breakdownList[idx].price || "N/A"} (Price per unit: ${breakdownList[idx].price || "N/A"})`
                    : " N/A"}
                </li>
              ))}
            </ul>
            <p><strong>Total Cost:</strong> {totalCost}</p>
            <button onClick={() => setShowBill(false)}>Close</button>
          </div>
        </div>
      )}

      {/* Checkout Modal */}
      {showCheckout && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Prices for {checkoutResource}</h3>
            <ul>
              {checkoutData.map((item, idx) => (
                <li key={idx}>
                  Region: {item.region} - Price: {item.price || "N/A"}
                </li>
              ))}
            </ul>
            <button onClick={() => setShowCheckout(false)}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default FormElements;
