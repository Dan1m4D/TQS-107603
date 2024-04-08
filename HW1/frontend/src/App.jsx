import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import TripsPage from "./pages/TripsPage";
import TripDetails from "./pages/TripDetails";
import MyTickets from "./pages/MyTickets";
function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/trips" element={<TripsPage/>} />
        <Route path="/trips/:id" element={<TripDetails/>} />
        <Route path="/mytickets" element={<MyTickets/>} />
      </Routes>
    </BrowserRouter>

  )
}

export default App
