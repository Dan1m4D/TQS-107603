import { useEffect, useState } from "react";
import axios from "../api";

import Navbar from "../components/layout/Navbar";
import { FaMoneyBillWave, FaArrowLeft } from "react-icons/fa";
import { FaTicketSimple, FaPerson } from "react-icons/fa6";
import { MdEmail, MdEventSeat } from "react-icons/md";
import { useNavigate } from "react-router-dom";

const MyTickets = () => {
  const [tickets, setTickets] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get("/tickets/list").then((res) => {
      console.log(res.data);
      setTickets(res.data);
    });
  }, []);

  return (
    <main className="flex flex-col bg-base-200 px-[5%] min-h-screen">
      <Navbar />
      <section className="flex flex-row items-center justify-between m-8">
        <span className="flex items-center">
          <FaArrowLeft
            className="mx-4 cursor-pointer text-accent"
            size={25}
            onClick={() => navigate("/")}
          />
          <h1 className="text-4xl font-bold text-primary">My Tickets</h1>
        </span>
      </section>
      <section className="flex flex-col items-center justify-between m-8">
        {tickets.length > 0 ? (
          tickets.map((ticket) => (
            <div
              key={ticket.id}
              className="w-1/2 p-4 m-2 border-2 shadow-xl rounded-xl bg-base-100 border-primary"
            >
              <span className="flex flex-row items-center">
                <FaTicketSimple className="m-2 text-accent" size={25} />
                <h1 className="text-3xl font-bold text-accent">
                  {" "}
                  Ticket <span className="text-2xl">{ticket.id}</span>
                </h1>
              </span>
              <span className="flex flex-row items-center">
                <MdEventSeat className="m-2 text-accent" size={25} />
                <h1 className="text-2xl font-bold text-primary">
                  Seat {ticket.seatNumber}
                </h1>
              </span>
              <span className="flex flex-row items-center">
                <FaPerson className="m-2 text-accent" size={25} />
                <h1 className="text-2xl font-bold text-primary">
                  {ticket.name}
                </h1>
              </span>
              <span className="flex flex-row items-center">
                <MdEmail className="m-2 text-accent" size={25} />
                <h1 className="text-2xl font-bold text-primary">
                  {ticket.email}
                </h1>
              </span>
              <span className="flex flex-row items-center">
                <FaMoneyBillWave className="m-2 text-accent" size={25} />
                <h1 className="text-2xl font-bold text-primary">
                  {ticket.price}
                </h1>
              </span>
            </div>
          ))
        ) : (
          <h1 className="text-2xl font-bold text-primary">No tickets </h1>
        )}
      </section>
    </main>
  );
};

export default MyTickets;
