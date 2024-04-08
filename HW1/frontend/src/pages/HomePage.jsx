import { useEffect, useState } from "react";
import axios from "../api";
import { FaSearch } from "react-icons/fa";

import { bus, logo_trans } from "../assets";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
  const [origins, setOrigins] = useState([]);
  const [destinations, setDestinations] = useState([]);
  const [selectedOrigin, setSelectedOrigin] = useState("");
  const [selectedDestination, setSelectedDestination] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const initialize = async () => {
      await axios.get("/trips/get_destinations").then((res) => {
        setDestinations(res.data);
      });
      await axios.get("/trips/get_origins").then((res) => {
        setOrigins(res.data);
      });
    };
    initialize();
  }, []);

  return (
    <main
      className="min-h-screen hero"
      style={{
        backgroundImage: `url(${bus})`,
      }}
    >
      <div className="hero-overlay bg-opacity-60"></div>
      <article className="flex flex-col text-center hero-content text-neutral-content">
        <img src={logo_trans} alt="logo" className="w-[40vw]" />
        <section className="flex flex-col items-center p-4 shadow-xl bg-base-100 rounded-xl">
          <h1 className="mb-8 text-4xl font-bold text-primary">
            Start your trip here
          </h1>
          <div className="flex flex-row items-center">
            <span className="flex flex-col items-start mx-4 text-2xl ">
              <h1 className="mx-2 font-semibold text-secondary">From:</h1>
              <select
                className="w-[20vw] select select-bordered m-2 text-neutral border-2 border-primary"
                onChange={(e) => setSelectedOrigin(e.target.value)}
              >
                <option value="">Origin</option>
                {origins.map((origin, idx) => (
                  <option
                    key={idx}
                    value={origin}
                    className="m-2 space-y-4 text-lg text-neutral "
                  >
                    {origin}
                  </option>
                ))}
              </select>
            </span>
            <span className="flex flex-col items-start mx-4 text-2xl ">
              <h1 className="mx-2 font-semibold text-secondary">To:</h1>
              <select
                className="w-[20vw] select select-bordered border-2 border-primary m-2 text-neutral"
                onChange={(e) => setSelectedDestination(e.target.value)}
              >
                <option value="">Destination</option>
                {destinations.map((destination, idx) => (
                  <option
                    key={idx}
                    value={destination}
                    className="m-2 space-y-4 text-lg text-neutral "
                  >
                    {destination}
                  </option>
                ))}
              </select>
            </span>
          </div>

          <button
            className="m-4 btn btn-primary"
            onClick={() => {
              navigate(
                "/trips?origin=" +
                  selectedOrigin +
                  "&destination=" +
                  selectedDestination
              );
            }}
          >
            <FaSearch /> Search Trips !{" "}
          </button>
        </section>
      </article>
    </main>
  );
};

export default HomePage;
