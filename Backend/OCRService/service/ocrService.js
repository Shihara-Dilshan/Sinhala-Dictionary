const ocr = require('./../utils/tesseract');

const ocrService = async (data) => {
    const url = data.url;
    const language = data.language;

    return new Promise(async(resolve, reject) => {
        try {
            const text = await ocr(url, language);
            resolve(text);
        } catch (err) {
            reject(err);
        }
    });
}

module.exports = ocrService;