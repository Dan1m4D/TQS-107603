import React from "react";
import { logo_trans } from "../../assets";
import { Link } from "react-router-dom";
import axios from "../../api";

const Navbar = () => {
  return (
    <nav className="flex flex-row justify-between bg-black shadow-xl rounded-xl navbar">
      <Link to="/" className="m-4 text-xl text-white">
        <img className="w-48 m-2 navbar-logo" src={logo_trans} />
      </Link>

      <Link to="/mytickets" className="m-4 text-xl text-white">
        My Tickets
      </Link>
    </nav>
  );
};

export default Navbar;
