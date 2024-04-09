import axios from "../api";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import Navbar from "../components/layout/Navbar";
import {
  FaArrowLeft,
  FaMapMarked,
  FaBus,
  FaClock,
  FaMoneyBillWave,
  FaStar,
} from "react-icons/fa";
import { MdEventSeat } from "react-icons/md";

const TripDetails = () => {
  const { id } = useParams();
  const [trip, setTrip] = useState({});
  const [currency, setCurrency] = useState("EUR");
  const [selectedSeat, setSelectedSeat] = useState({});
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const getSeatPrice = () => {
    if (selectedSeat && selectedSeat.seatType == "premium") {
      return (trip.price * 1.25).toFixed(2);
    } else {
      return trip.price?.toFixed(2);
    }
  };

  useEffect(() => {
    const initialize = async () => {
      axios
        .get("/trips/get", {
          params: {
            id: id,
            currency: currency,
          },
        })
        .then((res) => {
          console.log(res.data);
          setTrip(res.data);
        });
    };
    initialize();
  }, [id, currency]);

  const onSubmit = (data) => {
    console.log(data);
    let ticket = {
      tripID: trip.id,
      seatNumber: data.seatNumber,
      price: getSeatPrice(),
      name: data.name,
      email: data.email,
    };

    axios
      .post("/tickets/buy", ticket, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        navigate("/mytickets");
      })
      .catch((err) => {
        console.log(err);
        alert("Something went wrong. Please try again.");
      });
  };

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
          <h1 className="text-4xl font-bold text-primary">Your trip details</h1>
        </span>
        <select
          className="m-2 border-2 select select-bordered text-neutral"
          onChange={(e) => setCurrency(e.target.value)}
        >
          <option value="EUR">EUR</option>
          <option value="USD">USD</option>
        </select>
      </section>
      <section className="flex flex-col items-center w-full p-4 m-2 shadow-xl rounded-xl bg-base-100">
        <span className="flex flex-row items-center ">
          <FaMapMarked className="m-2 text-accent" size={25} />
          <h1 className="text-4xl font-bold text-accent">
            {trip.origin} to {trip.destination}
          </h1>
        </span>
        <span className="flex flex-row items-center">
          <FaBus className="m-2 text-accent" size={25} />
          <h1 className="text-2xl font-bold text-primary">Bus {trip.busID}</h1>
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
            {getSeatPrice()} {currency}
          </h1>
        </span>
      </section>
      <form className="flex flex-row" onSubmit={handleSubmit(onSubmit)}>
        <section className="flex flex-col w-full p-4 m-2 shadow-xl rounded-xl bg-base-100">
          <h1 className="m-2 text-4xl font-bold text-primary">Seat Details</h1>
          <div className="flex flex-wrap items-center">
            {trip.seats?.map((seat) => (
              <label
                key={seat.id}
                className={`w-12 p-2 flex flex-col items-center m-2 text-white rounded-lg cursor-pointer bg-accent ${
                  seat == selectedSeat ? "ring-primary ring" : ""
                } `}
              >
                <input
                  disabled={seat.taken}
                  type="radio"
                  name="seatNumber" // Ensure all radio buttons have the same name
                  value={seat.number}
                  className="hidden"
                  checked={selectedSeat.number === seat.number}
                  onClick={() => setSelectedSeat(seat)}
                  {...register("seatNumber", {
                    required: "Please select a seat",
                  })} // Register the seat number in the form
                />
                <MdEventSeat
                  className={`${
                    seat.seatType === "premium" && !seat.taken
                      ? "text-amber-400"
                      : seat.taken && "text-secondary"
                  }`}
                  size={25}
                />

                <h1
                  className={`${
                    seat.seatType === "premium" && !seat.taken
                      ? "text-amber-400"
                      : seat.taken && "text-secondary"
                  } mx-1`}
                >
                  {seat.number}
                </h1>
              </label>
            ))}
          </div>
          <span className="flex flex-row items-center">
            <MdEventSeat className="m-2 text-accent" size={25} />
            <h1 className="text-2xl font-bold text-primary">
              Your seat is: {selectedSeat.number}
            </h1>
          </span>
          {errors.seatNumber && (
            <span className="text-lg text-error">
              {errors.seatNumber.message}
            </span>
          )}
        </section>
        <section className="flex flex-col w-full p-4 m-2 shadow-xl rounded-xl bg-base-100">
          <h1 className="m-2 text-4xl font-bold text-accent">Your details</h1>
          <div className="m-2">
            <span className="m-2">
              <h1 className="m-2 text-xl font-semibold text-accent">Name</h1>
              <input
                {...register("name", { required: "Please insert a name" })}
                type="text"
                placeholder="Name"
                className="w-full input-bordered input input-primary"
              />
              {errors.name && (
                <span className="text-lg text-error">
                  {errors.name.message}
                </span>
              )}
            </span>
            <span className="m-2">
              <h1 className="m-2 text-xl font-semibold text-accent">Email</h1>
              <input
                {...register("email", {
                  required: "Email is required",
                  pattern: {
                    value: /.+@.+\..+/,
                    message: "Invalid email format",
                  },
                })}
                type="text"
                placeholder="Email"
                className="w-full input-bordered input input-primary"
              />
              {errors.email && (
                <span className="text-lg text-error">
                  {errors.email.message}
                </span>
              )}
            </span>
          </div>
          <span className="flex flex-row items-center my-4">
            <button className="m-2 btn btn-primary">Buy Ticket</button>
          </span>
        </section>
      </form>
    </main>
  );
};

export default TripDetails;
