import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function AddUser() {
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    company: {
      name: ""
    }
  });
  
  const [errors, setErrors] = useState({});
  const [successMsg, setSuccessMsg] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    if (name === 'companyName') {
      setFormData({
        ...formData,
        company: {
          ...formData.company,
          name: value
        }
      });
    } else {
      setFormData({
        ...formData,
        [name]: value
      });
    }
    
    setErrors({
      ...errors,
      [name]: ""
    });
  };

  const validateForm = () => {
    let tempErrors = {};
    
    if (!formData.name.trim()) tempErrors.name = "Name is required";
    const isValidEmail =
  formData.email.includes("@") &&
  formData.email.includes(".");

if (!isValidEmail) {
  tempErrors.email = "Email is invalid";
}
    if (!formData.phone.trim()) tempErrors.phone = "Phone is required";
    if (!formData.company.name.trim()) tempErrors.companyName = "Company name is required";
    
    setErrors(tempErrors);
    return Object.keys(tempErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) return;
    
    setSubmitting(true);
    setErrorMsg("");
    setSuccessMsg("");
    
    try {
      const response = await axios.post('https://jsonplaceholder.typicode.com/users', formData);
      console.log("API Response:", response.data);
      
      const newUser = {
        ...formData,
        id: Date.now()
      };
      
      const existingUsers =
  JSON.parse(localStorage.getItem("addedUsers")) || [];

existingUsers.push(newUser);

localStorage.setItem(
  "addedUsers",
  JSON.stringify(existingUsers)
);
      setSuccessMsg(`User "${formData.name}" created successfully!`);
      
      setFormData({
        name: "",
        email: "",
        phone: "",
        company: {
          name: ""
        }
      });
      
      setTimeout(() => {
        navigate("/users");
      }, 1500);
      
    } catch (err) {
      console.error("Error creating user:", err);
      setErrorMsg("Error creating user. Please try again.");
    } finally {
      setSubmitting(false);
    }
  };

  const resetForm = () => {
    setFormData({
      name: "",
      email: "",
      phone: "",
      company: {
        name: ""
      }
    });
    setErrors({});
    setSuccessMsg("");
    setErrorMsg("");
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h1>Add New User</h1>
        <button 
          className="btn btn-secondary" 
          onClick={() => navigate("/users")}
        >
          <i className="bi bi-arrow-left me-1"></i>
          Back to Users
        </button>
      </div>

      {successMsg && (
        <div className="alert alert-success alert-dismissible fade show" role="alert">
          <i className="bi bi-check-circle me-2"></i>
          {successMsg}
          <button 
            type="button" 
            className="btn-close" 
            onClick={() => setSuccessMsg("")}
          ></button>
        </div>
      )}

      
      {errorMsg && (
        <div className="alert alert-danger alert-dismissible fade show" role="alert">
          <i className="bi bi-exclamation-circle me-2"></i>
          {errorMsg}
          <button 
            type="button" 
            className="btn-close" 
            onClick={() => setErrorMsg("")}
          ></button>
        </div>
      )}

      <div className="card shadow-sm">
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <div className="row">
              <div className="col-md-6">
                {/* Name Field */}
                <div className="mb-3">
                  <label className="form-label fw-bold">
                    <i className="bi bi-person me-1"></i>
                    Name <span className="text-danger">*</span>
                  </label>
                  <input 
                    type="text" 
                    name="name" 
                    className={`form-control ${errors.name ? 'is-invalid' : ''}`}
                    placeholder="Enter full name"
                    value={formData.name}
                    onChange={handleChange}
                    disabled={submitting}
                  />
                  {errors.name && <div className="invalid-feedback">{errors.name}</div>}
                </div>

                {/* Email Field */}
                <div className="mb-3">
                  <label className="form-label fw-bold">
                    <i className="bi bi-envelope me-1"></i>
                    Email <span className="text-danger">*</span>
                  </label>
                  <input 
                    type="email" 
                    name="email" 
                    className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                    placeholder="Enter email address"
                    value={formData.email}
                    onChange={handleChange}
                    disabled={submitting}
                  />
                  {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                </div>
              </div>

              <div className="col-md-6">
                {/* Phone Field */}
                <div className="mb-3">
                  <label className="form-label fw-bold">
                    <i className="bi bi-telephone me-1"></i>
                    Phone <span className="text-danger">*</span>
                  </label>
                  <input 
                    type="text" 
                    name="phone" 
                    className={`form-control ${errors.phone ? 'is-invalid' : ''}`}
                    placeholder="Enter phone number"
                    value={formData.phone}
                    onChange={handleChange}
                    disabled={submitting}
                  />
                  {errors.phone && <div className="invalid-feedback">{errors.phone}</div>}
                </div>

                {/* Company Name Field */}
                <div className="mb-3">
                  <label className="form-label fw-bold">
                    <i className="bi bi-building me-1"></i>
                    Company Name <span className="text-danger">*</span>
                  </label>
                  <input 
                    type="text" 
                    name="companyName" 
                    className={`form-control ${errors.companyName ? 'is-invalid' : ''}`}
                    placeholder="Enter company name"
                    value={formData.company.name}
                    onChange={handleChange}
                    disabled={submitting}
                  />
                  {errors.companyName && <div className="invalid-feedback">{errors.companyName}</div>}
                </div>
              </div>
            </div>

            <div className="mt-3 border-top pt-3">
              <button 
                type="submit" 
                className="btn btn-primary me-2"
                disabled={submitting}
              >
                {submitting ? (
                  <>
                    <span className="spinner-border spinner-border-sm me-1" role="status"></span>
                    Creating...
                  </>
                ) : (
                  <>
                    <i className="bi bi-person-plus me-1"></i>
                    Create User
                  </>
                )}
              </button>
              <button 
                type="button" 
                className="btn btn-secondary me-2"
                onClick={resetForm}
                disabled={submitting}
              >
                <i className="bi bi-arrow-counterclockwise me-1"></i>
                Reset
              </button>
              <button 
                type="button" 
                className="btn btn-outline-danger"
                onClick={() => navigate("/users")}
                disabled={submitting}
              >
                <i className="bi bi-x-circle me-1"></i>
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>

     
    </div>
  );
}

export default AddUser;