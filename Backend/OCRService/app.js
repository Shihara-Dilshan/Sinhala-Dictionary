const express = require('express');
const cors = require('cors');
const ocrRoute = require('./api/ocrApi');
const env = require("dotenv");

const app = express();
cors(app);
app.use(express.json())
env.config();

const PORT = process.env.PORT || 5001;

app.use("/api/v2/ocr", ocrRoute);


app.listen(PORT, () => {
    console.log("Server has started on port 5001");
});




