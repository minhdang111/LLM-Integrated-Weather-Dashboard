function WeatherCard({ weather }) {
  const iconUrl = `https://openweathermap.org/img/wn/${weather.icon}@2x.png`;

  return (
    <div className="weather-card">
      <h2>{weather.city}</h2>
      <img src={iconUrl} alt={weather.description} />
      <p className="description">{weather.description}</p>
      <div className="weather-grid">
        <div><span>🌡️ Temp</span><strong>{weather.temperature}°F</strong></div>
        <div><span>🤔 Feels like</span><strong>{weather.feelsLike}°F</strong></div>
        <div><span>💧 Humidity</span><strong>{weather.humidity}%</strong></div>
        <div><span>💨 Wind</span><strong>{weather.windSpeed} mph</strong></div>
      </div>
    </div>
  );
}

export default WeatherCard;