import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import UserList from './components/UserList';
import AddUser from './components/AddUser';
import CharacterList from './components/CharacterList';

function App() {
  return (
    <div>
      <Navbar />
      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<UserList />} />
          <Route path="/users" element={<UserList />} />
          <Route path="/add-user" element={<AddUser />} />
          <Route path="/character-list" element={<CharacterList />} />

        </Routes>
      </div>
    </div>
  );
}

export default App;