import { useEffect, useState } from "react";
import axios from "../api";
import {
  FaArrowLeft,
  FaBus,
  FaClock,
  FaMapMarked,
  FaMoneyBillWave,
} from "react-icons/fa";
import Navbar from "../components/layout/Navbar";
import { useNavigate } from "react-router-dom";

const TripsPage = () => {
  const [origin, setOrigin] = useState("");
  const [destination, setDestination] = useState("");
  const [date, setDate] = useState("");
  const [currency, setCurrency] = useState("EUR");
  const [trips, setTrips] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const initialize = async () => {
      const urlParams = new URLSearchParams(window.location.search);
      const origin = urlParams.get("origin");
      const destination = urlParams.get("destination");
      const date = urlParams.get("date");
      setOrigin(origin);
      setDestination(destination);
      setDate(date);

      await axios
        .get("/trips/list", {
          params: {
            origin: origin ? origin : null,
            destination: destination ? destination : null,
            date: date ? date : null,
            currency: currency,
          },
        })
        .then((res) => {
          console.log(res.data);
          setTrips(res.data);
        });
    };
    initialize();
  }, [origin, destination, currency]);

  return (
    <main className="flex flex-col bg-base-200 px-[5%] min-h-screen">
      <Navbar />
      <section className="flex flex-row items-center justify-between m-8">
        <span className="flex items-center">
          <FaArrowLeft
            className="mx-4 cursor-pointer text-accent"
            size={25}
            onClick={() => navigate(-1)}
          />
          <h1 className="text-4xl font-bold text-primary">
            Trips available from {origin} to {destination}{" "}
          </h1>
        </span>
        <select
          className="m-2 border-2 select select-bordered text-neutral"
          onChange={(e) => setCurrency(e.target.value)}
        >
          <option value="EUR">EUR</option>
          <option value="USD">USD</option>
        </select>
      </section>
      <section className="flex flex-col items-center">
        {trips.length > 0 ? (
          trips.map((trip) => (
            <div
              key={trip.id}
              className="w-1/2 p-4 m-2 shadow-xl rounded-xl bg-base-100"
            >
              <span className="flex flex-row items-center justify-between">
                <FaMapMarked className="m-2 text-accent" size={25} />
                <h1 className="text-3xl font-bold text-accent">
                  {trip.origin} to {trip.destination}
                </h1>
                <button className="btn btn-primary" onClick={() => navigate("/trips/" + trip.id)} >Choose this trip</button>
              </span>
              <span className="flex flex-row items-center">
                <FaBus className="m-2 text-accent" size={25} />
                <h1 className="text-2xl font-bold text-primary">
                  Bus {trip.busID}
                </h1>
              </span>
              <span className="flex flex-row items-center">
                <FaClock className="m-2 text-accent" size={25} />
                <h1 className="text-2xl font-bold text-primary">
                  {trip.departureDay} at {trip.departureHour}
                </h1>
              </span>
              <span className="flex flex-row items-center">
                <FaMoneyBillWave className="m-2 text-accent" size={25} />
                <h1 className="text-2xl font-bold text-primary">
                  {trip.price.toFixed(2)} {currency}
                </h1>
              </span>
            </div>
          ))
        ) : (
          <h1 className="text-2xl font-bold text-primary">No trips </h1>
        )}
      </section>
    </main>
  );
};

export default TripsPage;
