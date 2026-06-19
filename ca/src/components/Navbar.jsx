import { NavLink } from 'react-router-dom';

function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        <NavLink className="navbar-brand" to="/">
          <i className="bi bi-people-fill me-2"></i>
          User Dashboard
        </NavLink>
        <div className="navbar-nav">
          <NavLink 
            className="nav-link" 
            to="/users"
            style={({ isActive }) => ({
              fontWeight: isActive ? 'bold' : 'normal',
              borderBottom: isActive ? '2px solid white' : 'none'
            })}
          >
            <i className="bi bi-list-ul me-1"></i>
            User List
          </NavLink>
          <NavLink 
            className="nav-link" 
            to="/add-user"
            style={({ isActive }) => ({
              fontWeight: isActive ? 'bold' : 'normal',
              borderBottom: isActive ? '2px solid white' : 'none'
            })}
          >
            <i className="bi bi-person-plus me-1"></i>
            Add User
          </NavLink>

         
        </div>
      </div>
    </nav>
  );
}

export default Navbar;