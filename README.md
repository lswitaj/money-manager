# 📈 StockTracker – Android App for Monitoring US Stock Positions

StockTracker is an Android portfolio app enabling users to monitor their investment positions on the US stock market. Developed as part of an engineering thesis at the Warsaw University of Technology, it leverages cloud sync, modern Android architecture, and reliable market data APIs.

---

## 📱 Features

- User registration and login (Back4App / Parse)
- Add positions for US stock market assets
- Store position parameters: ticker symbol, purchase price, number of shares
- Fetch current stock prices (Finnhub API)
- Calculate and display profit/loss per position
- Cloud data sync – access your portfolio from any Android device
- Support for multiple positions in the same asset

---

## 🧰 Technologies

| Component                   | Technology                        |
|-----------------------------|-----------------------------------|
| Programming Language        | Kotlin                            |
| UI                          | Material Design             |
| Architecture                | MVVM                              |
| Backend as a Service (BaaS) | Back4App (Parse-based)            |
| Market Data API             | Finnhub API                       |
| Async                       | Kotlin Coroutines                 |
| Local Database              | Room (SQLite + LiveData)          |
| API Communication           | Retrofit + Moshi                  |

---

## 🗂️ Project Structure

- `app/` – app logic: views, models, ViewModels
- `network/` – Retrofit config, Finnhub API integration
- `database/` – Room local database logic
- `backend/` – Parse SDK and Back4App integration
- `ui/` – user interface layout (XML)

---

## 🔄 Data Sync & Security

- User data is stored in the cloud using Parse Objects.
- Access controlled via ACL (Access Control List) – positions are private to their owners.
- Application logic manages user sessions, password, and email validation.

---

## ▼ Requirements

- Android 9.0 (API 28) or newer
- Back4App user account
- Finnhub API key

---

## ⚙️ Running Project Locally

1. Clone the repository:
git clone https://github.com/your-username/StockTracker.git
2. Open the project in Android Studio.
3. Add your API keys to `local.properties` or config:
FINNHUB_API_KEY=your_key
PARSE_APP_ID=your_app_id
PARSE_CLIENT_KEY=your_client_key
PARSE_SERVER_URL=https://parseapi.back4app.com/
4. Run the app on an emulator or Android device.

---

## 🚀 Future Development

- Support for more exchanges (GPW, LSE, DAX)
- Other asset types (forex, cryptocurrencies)
- Multi-currency support, static positions (cash, bonds)
- Candle charts and historical portfolio value analytics
- Web and iOS client apps

---

## 🧪 Testing

- Tested on Emulator: Pixel 2 (Android 9.0)
- Physical Device: Motorola Moto G7 (Android 10)

Test cases included:
- Adding and syncing positions
- Offline mode reliability
- Handling registration/login errors

---

## 📄 License

This is an educational, non-commercial project created as part of an engineering thesis.
