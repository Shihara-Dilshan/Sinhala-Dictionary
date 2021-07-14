const Tesseract = require('tesseract.js');

const ocr = async (imageUrl, lang='eng') => {
    return new Promise( (resolve, reject) => {
        try{
            Tesseract.recognize(
                imageUrl,
                lang,
                { logger: m => console.log(m) }
              ).then(({ data: { text } }) => {
                resolve(text);
              });
        }catch(err){
            reject(err);
        }
    });
}

module.exports = ocr;