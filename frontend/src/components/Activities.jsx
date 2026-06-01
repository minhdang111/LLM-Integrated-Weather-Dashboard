function Activities({ activities }) {
  const lines = activities.split("\n").filter((line) => line.trim() !== "");

  return (
    <div className="activities">
      <h3>🎯 Suggested Activities</h3>
      <ul>
        {lines.map((line, i) => (
          <li key={i}>{line}</li>
        ))}
      </ul>
    </div>
  );
}

export default Activities;