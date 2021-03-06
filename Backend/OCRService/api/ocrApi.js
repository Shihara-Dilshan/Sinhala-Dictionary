const express = require('express');
const router = express.Router();
const ocrService = require('./../service/ocrService');

router.post('/', async(req, res) =>  {
    const data = req.body;
    const result = await ocrService(data);
    res.json({"result":result});
});

module.exports = router;
