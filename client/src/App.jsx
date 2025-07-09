import "./axiosConfig";
import { BrowserRouter } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Router from "./router";
import './scss/reset.scss';

function App() {
  return (
    <BrowserRouter>
      {/* ✅ 반드시 Router보다 바깥쪽 */}
      <AuthProvider>     
        <Router />
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;