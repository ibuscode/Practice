import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [deleteMsg, setDeleteMessage] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [selectedUserId, setSelectedUserId] = useState(null);

  const api = "https://jsonplaceholder.typicode.com/users";

  const getAllUsers = async () => {
    try {
      const response = await axios.get(api);

      let allUsers = response.data;

      const addedUsers =
  JSON.parse(localStorage.getItem("addedUsers")) || [];

allUsers = [...addedUsers, ...allUsers];

      setUsers(allUsers);
      setLoading(false);
    } catch (err) {
      console.error("Error fetching users:", err);
      setLoading(false);
    }
  };

  useEffect(() => {
    getAllUsers();
  }, []);

  const confirmDelete = (id) => {
    setSelectedUserId(id);
    setShowModal(true);
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`${api}/${selectedUserId}`);

      const tempUsers = users.filter(
        (user) => user.id !== selectedUserId
      );

      setUsers(tempUsers);

      setDeleteMessage("User deleted successfully!");
      setTimeout(() => setDeleteMessage(""), 3000);

      setShowModal(false);
      setSelectedUserId(null);
    } catch (err) {
      console.error("Error deleting user:", err);

      setDeleteMessage("Error deleting user.");
      setTimeout(() => setDeleteMessage(""), 3000);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-5">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
        <p className="mt-2">Loading users...</p>
      </div>
    );
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h1>User Management</h1>

        <Link to="/add-user" className="btn btn-primary">
          <i className="bi bi-person-plus me-1"></i>
          Add New User
        </Link>
      </div>

      {successMsg && (
        <div
          className="alert alert-success alert-dismissible fade show"
          role="alert"
        >
          <i className="bi bi-check-circle me-2"></i>
          {successMsg}

          <button
            type="button"
            className="btn-close"
            onClick={() => setSuccessMsg("")}
          ></button>
        </div>
      )}

      {deleteMsg && (
        <div
          className="alert alert-success alert-dismissible fade show"
          role="alert"
        >
          <i className="bi bi-check-circle me-2"></i>
          {deleteMsg}

          <button
            type="button"
            className="btn-close"
            onClick={() => setDeleteMessage("")}
          ></button>
        </div>
      )}

      <div className="mb-2">
        <span className="badge bg-primary">
          <i className="bi bi-people me-1"></i>
          Total Users: {users.length}
        </span>
      </div>

      <div className="table-responsive">
        <table className="table table-striped table-hover">
          <thead className="table-primary">
            <tr>
              <th>#</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Company</th>
              <th className="text-center">Actions</th>
            </tr>
          </thead>

          <tbody>
            {users.length === 0 ? (
              <tr>
                <td colSpan="6" className="text-center py-3">
                  No users found
                </td>
              </tr>
            ) : (
              users.map((user, index) => (
                <tr key={user.id}>
                  <td>{index + 1}</td>

                  <td>
                    <i className="bi bi-person-circle me-2 text-primary"></i>
                    {user.name}
                  </td>

                  <td>
                    <i className="bi bi-envelope me-1"></i>
                    {user.email}
                  </td>

                  <td>
                    <i className="bi bi-telephone me-1"></i>
                    {user.phone}
                  </td>

                  <td>
                    <i className="bi bi-building me-1"></i>
                    {user.company?.name || "N/A"}
                  </td>

                  <td className="text-center">
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => confirmDelete(user.id)}
                    >
                      <i className="bi bi-trash me-1"></i>
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div
          className="modal show d-block"
          style={{ backgroundColor: "rgba(0,0,0,0.5)" }}
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  <i className="bi bi-exclamation-triangle text-warning me-2"></i>
                  Confirm Delete
                </h5>

                <button
                  type="button"
                  className="btn-close"
                  onClick={() => {
                    setShowModal(false);
                    setSelectedUserId(null);
                  }}
                ></button>
              </div>

              <div className="modal-body">
                <p>
                  Are you sure you want to delete this user?
                  <br />
                  <small className="text-muted">
                    This action cannot be undone.
                  </small>
                </p>
              </div>

              <div className="modal-footer">
                <button
                  className="btn btn-secondary"
                  onClick={() => {
                    setShowModal(false);
                    setSelectedUserId(null);
                  }}
                >
                  Cancel
                </button>

                <button
                  className="btn btn-danger"
                  onClick={handleDelete}
                >
                  Delete User
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserList;