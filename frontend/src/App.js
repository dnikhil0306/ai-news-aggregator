import React, { useState, useEffect } from 'react'; // 1. Import hooks
import './App.css';

function App() {
  // 2. Set up state to hold the list of articles
  const [articles, setArticles] = useState([]);

  // 3. useEffect runs once when the component loads
  useEffect(() => {
    // 4. Fetch data from our backend API
    fetch('/api/news') // Because of the proxy, this goes to http://localhost:8080/api/news
      .then(response => response.json()) // Parse the JSON from the response
      .then(data => setArticles(data)); // Update our state with the fetched articles
  }, []); // The empty array means this effect runs only once

  return (
    <div className="App">
      <header className="App-header">
        <h1>AI News Aggregator</h1>
      </header>
      <main className="news-grid">
        {/* 5. Map over the articles and create a card for each one */}
        {articles.map(article => (
          <div key={article.id} className="article-card">
            {article.image && <img src={article.image} alt={article.title} />}
            <div className="article-content">
              <h2>{article.title}</h2>
              <p>{article.description}</p>
            </div>
          </div>
        ))}
      </main>
    </div>
  );
}

export default App;