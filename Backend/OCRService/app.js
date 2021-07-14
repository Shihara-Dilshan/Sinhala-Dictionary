const express = require('express');
const cors = require('cors');
const ocrRoute = require('./api/ocrApi');

const app = express();
cors(app);
app.use(express.json())

app.use("/api/v2/ocr", ocrRoute);


app.listen(5001, () => {
    console.log("Server has started on port 5001");
});




