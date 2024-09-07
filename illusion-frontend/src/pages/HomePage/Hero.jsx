import React from "react";
import Text from "../../components/Text";

function Hero() {
    return (
        <div className="w-full text-center py-16 bg-gradient-to-r from-purple-500 via-pink-500 to-red-500 text-white">
            <h1 className="text-[5.5rem] font-bold font-aeonik-pro drop-shadow-lg">
                Backend in<span className="ml-4 animate-blink text-yellow-300">Blink</span>
            </h1>
            <Text>Dream. Design. Develop. Deliver. Illusion Server handles the backend.</Text>
        </div>
    );
}

export default Hero;
