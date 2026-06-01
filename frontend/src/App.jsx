import { useState } from "react";
import WeatherCard from "./components/WeatherCard";
import Activities from "./components/Activities";
import SearchBar from "./components/SearchBar";
import "./App.css";

function App() {
  const [weather, setWeather] = useState(null);
  const [activities, setActivities] = useState(null);
  const [loadingWeather, setLoadingWeather] = useState(false);
  const [loadingActivities, setLoadingActivities] = useState(false);
  const [weatherError, setWeatherError] = useState(null);
  const [activitiesError, setActivitiesError] = useState(null);

  const fetchActivities = async (searchCity) => {
    setLoadingActivities(true);
    setActivitiesError(null);
    try {
      const res = await fetch(`http://localhost:8080/api/weather/${searchCity}/activities`);
      if (!res.ok) throw new Error("Could not fetch activities");
      const data = await res.json();
      setActivities(data.suggestion);
    } catch (err) {
      setActivitiesError("AI suggestions unavailable right now. Try again in a moment.");
    } finally {
      setLoadingActivities(false);
    }
  };

  const handleSearch = async (searchCity) => {
    setWeather(null);
    setActivities(null);
    setWeatherError(null);
    setActivitiesError(null);

    setLoadingWeather(true);
    try {
      const res = await fetch(`http://localhost:8080/api/weather/${searchCity}`);
      if (!res.ok) throw new Error("City not found");
      const data = await res.json();
      setWeather(data);
    } catch (err) {
      setWeatherError(err.message);
    } finally {
      setLoadingWeather(false);
    }

    fetchActivities(searchCity);
  };

  return (
    <div className="app">
      <h1>Weather Dashboard</h1>
      <SearchBar onSearch={handleSearch} />
      {weatherError && <p className="error">{weatherError}</p>}
      {loadingWeather && <p className="loading">Fetching weather...</p>}
      {weather && <WeatherCard weather={weather} />}
      {activitiesError && <p className="error">{activitiesError}</p>}
      {loadingActivities && <p className="loading">Asking AI for activity suggestions...</p>}
      {activities && <Activities activities={activities} />}
    </div>
  );
}

export default App;